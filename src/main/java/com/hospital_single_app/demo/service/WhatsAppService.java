package com.hospital_single_app.demo.service;

import com.hospital_single_app.demo.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WhatsAppService {

    private final ChatFlowService chatFlowService;

    @Value("${whatsapp.token}")
    private String token;

    @Value("${whatsapp.phone-number-id}")
    private String phoneNumberId;

    private final RestTemplate restTemplate = new RestTemplate();

    // 🧠 MEMORY
    private final Map<String, String> userState = new HashMap<>();
    private final Map<String, PatientDTO> userData = new HashMap<>();

    private final Map<String, LocalDate> userSelectedDate = new HashMap<>();

    public WhatsAppService(ChatFlowService chatFlowService) {
        this.chatFlowService = chatFlowService;
    }

    public void handleMessage(String from, String message) {

        if (message == null) return;

        System.out.println("FROM: " + from + " MESSAGE: " + message);

        message = message.trim();

        // 🔥 STEP 1: HANDLE REGISTRATION FLOW
        String state = userState.get(from);
        if (state != null) {
            handleRegistrationFlow(from, message, state);
            return;
        }

        // 🔥 STEP 2: HANDLE DYNAMIC ACTIONS
        if (message.startsWith("DATE_")) {
            LocalDate date = LocalDate.parse(message.replace("DATE_", ""));
            userSelectedDate.put(from, date);
            sendAvailableSlots(from, date);
            return;
        }

        if (message.startsWith("SLOT_")) {
            handleSlotSelection(from, message);
            return;
        }

        message = message.toUpperCase();

        switch (message) {

            case "HI":
                PatientDTO patient = chatFlowService.getPatient(from);

                if (patient != null) {
                    sendWelcomeButtons(from, patient.getFullName());
                } else {
                    sendDefaultButtons(from);
                }
                break;

            case "REGISTER":
                userState.put(from, "ASK_NAME");
                sendText(from, "Enter your full name:");
                break;

            case "APPOINTMENT":
                sendDynamicDateList(from);
                break;

            default:
                sendText(from, "Type 'hi' to start");
        }
    }

    // ================= REGISTRATION =================

    private void handleRegistrationFlow(String from, String message, String state) {

        PatientDTO dto = userData.getOrDefault(from, new PatientDTO());

        switch (state) {

            case "ASK_NAME":
                dto.setFullName(message);
                userData.put(from, dto);

                userState.put(from, "ASK_GENDER");
                sendGenderButtons(from);
                break;

            case "ASK_GENDER":
                dto.setGender(message);
                userData.put(from, dto);

                userState.put(from, "ASK_ADDRESS");
                sendText(from, "Enter your address:");
                break;

            case "ASK_ADDRESS":
                dto.setAddress(message);

                String phone = from.substring(2);
                dto.setPhone(phone);

                chatFlowService.savePatient(dto);

                userState.remove(from);
                userData.remove(from);

                sendText(from, "✅ Registration completed!");
                break;
        }
    }

    // ================= DATE LIST =================

    private void sendDynamicDateList(String to) {

        List<HolidayDTO> holidays = chatFlowService.getAllHolidays();

        Set<LocalDate> holidayDates = holidays.stream()
                .map(HolidayDTO::getHolidayDate)
                .collect(Collectors.toSet());

        List<LocalDate> nextDates = new ArrayList<>();

        LocalDate today = LocalDate.now();
        int count = 0;
        int i = 0;

        while (count < 10) {
            LocalDate date = today.plusDays(i);

            if (!holidayDates.contains(date)) {
                nextDates.add(date);
                count++;
            }
            i++;
        }

        StringBuilder rows = new StringBuilder();

        for (LocalDate d : nextDates) {
            rows.append(String.format(
                    "{ \"id\": \"DATE_%s\", \"title\": \"%s\" },",
                    d, d
            ));
        }

        String body = """
        {
          "messaging_product": "whatsapp",
          "to": "%s",
          "type": "interactive",
          "interactive": {
            "type": "list",
            "body": {
              "text": "Select Date 📅"
            },
            "action": {
              "button": "Choose Date",
              "sections": [
                {
                  "title": "Available Dates",
                  "rows": [%s]
                }
              ]
            }
          }
        }
        """.formatted(to, rows.substring(0, rows.length() - 1));

        send(body);
    }

    // ================= SLOT LIST =================

    private void sendAvailableSlots(String to, LocalDate date) {

        List<SlotDTO> slots = chatFlowService.getAllSlots();

        StringBuilder rows = new StringBuilder();

        for (SlotDTO slot : slots) {

            long booked = chatFlowService.getBookedCount(slot.getPkSlotId(), date);

            if (booked < slot.getCapacity()) {

                rows.append(String.format(
                        "{ \"id\": \"SLOT_%d\", \"title\": \"%s\" },",
                        slot.getPkSlotId(),
                        slot.getSlotName()
                ));
            }
        }

        if (rows.length() == 0) {
            sendText(to, "❌ No slots available");
            return;
        }

        String body = """
        {
          "messaging_product": "whatsapp",
          "to": "%s",
          "type": "interactive",
          "interactive": {
            "type": "list",
            "body": {
              "text": "Select Slot ⏰"
            },
            "action": {
              "button": "Choose Slot",
              "sections": [
                {
                  "title": "Available Slots",
                  "rows": [%s]
                }
              ]
            }
          }
        }
        """.formatted(to, rows.substring(0, rows.length() - 1));

        send(body);
    }

    // ================= BOOKING =================

    private void handleSlotSelection(String from, String message) {

        Long slotId = Long.parseLong(message.replace("SLOT_", ""));
        LocalDate date = userSelectedDate.get(from);

        PatientDTO patient = chatFlowService.getPatient(from);

        if (patient == null) {
            sendText(from, "Please register first");
            return;
        }

        BookingDTO dto = new BookingDTO();
        dto.setPatientId(patient.getPatientId());
        dto.setBookingDate(date);
        dto.setSlotId(slotId);

        BookingDTO booking = chatFlowService.createBooking(dto);

        sendText(from,
                "✅ Booking Confirmed\nDate: " + booking.getBookingDate() +
                        "\nSlot: " + booking.getSlotName() +
                        "\nBooking No: " + booking.getBookingNo());
    }

    // ================= BUTTONS =================

    private void sendWelcomeButtons(String to, String name) {

        String body = """
        {
          "messaging_product": "whatsapp",
          "to": "%s",
          "type": "interactive",
          "interactive": {
            "type": "button",
            "body": {
              "text": "Hi %s, Welcome to Clinic 🏥"
            },
            "action": {
              "buttons": [
                {
                  "type": "reply",
                  "reply": {
                    "id": "APPOINTMENT",
                    "title": "Appointment"
                  }
                },
                 {
                   "type": "reply",
                   "reply": {
                   "id": "ADDRESS",
                   "title": "Address"
                   }
                }
              ]
            }
          }
        }
        """.formatted(to, name);

        send(body);
    }

    private void sendDefaultButtons(String to) {

        String body = """
        {
          "messaging_product": "whatsapp",
          "to": "%s",
          "type": "interactive",
          "interactive": {
            "type": "button",
            "body": {
              "text": "Welcome to Clinic 🏥"
            },
            "action": {
              "buttons": [
                {
                  "type": "reply",
                  "reply": {
                    "id": "REGISTER",
                    "title": "Register"
                  }
                },
                {
                   "type": "reply",
                   reply": {
                   "id": "ADDRESS",
                   "title": "Address"
                   }
                }
              ]
            }
          }
        }
        """.formatted(to);

        send(body);
    }

    private void sendGenderButtons(String to) {

        String body = """
        {
          "messaging_product": "whatsapp",
          "to": "%s",
          "type": "interactive",
          "interactive": {
            "type": "button",
            "body": {
              "text": "Select Gender"
            },
            "action": {
              "buttons": [
                { "type": "reply", "reply": { "id": "MALE", "title": "Male" }},
                { "type": "reply", "reply": { "id": "FEMALE", "title": "Female" }}
              ]
            }
          }
        }
        """.formatted(to);

        send(body);
    }

    // ================= COMMON =================

    private void send(String body) {

        String url = "https://graph.facebook.com/v18.0/" + phoneNumberId + "/messages";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        restTemplate.postForEntity(url, request, String.class);
    }

    private void sendText(String to, String message) {

        message = message.replace("\"", "\\\"").replace("\n", "\\n");

        String body = String.format(
                "{ \"messaging_product\": \"whatsapp\", \"to\": \"%s\", \"type\": \"text\", \"text\": { \"body\": \"%s\" } }",
                to, message
        );

        send(body);
    }
}