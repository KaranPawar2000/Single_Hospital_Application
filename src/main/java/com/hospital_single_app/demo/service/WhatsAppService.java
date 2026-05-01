package com.hospital_single_app.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class WhatsAppService {

    @Value("${whatsapp.token}")
    private String token;

    @Value("${whatsapp.phone-number-id}")
    private String phoneNumberId;

    private final RestTemplate restTemplate = new RestTemplate();

    // 🧠 Simple memory
    private final Map<String, String> userDate = new HashMap<>();
    private final Map<String, String> userSlot = new HashMap<>();

    public void handleMessage(String from, String message) {

        if (message == null) return;

        System.out.println("FROM: " + from + " MESSAGE: " + message);

        message = message.trim().toUpperCase();

        switch (message) {

            case "HI":
                sendWelcomeButtons(from);
                break;

            case "OLD_PATIENT":
                System.out.println("➡️ Sending date list");
                sendDateList(from);
                break;

            case "DATE_1":
            case "DATE_2":
            case "DATE_3":
                userDate.put(from, message);
                sendSlotList(from);
                break;

            case "SLOT_1":
            case "SLOT_2":
            case "SLOT_3":

                userSlot.put(from, message);

                String date = userDate.get(from);
                String slot = userSlot.get(from);

                sendText(from,
                        "✅ Booking Confirmed\nDate: " + date + "\nSlot: " + slot);
                break;

            default:
                sendText(from, "Type 'hi' to start");
        }
    }

    // ✅ FIXED TEXT MESSAGE
//    private void sendText(String to, String message) {
//
//        // escape quotes
//        message = message.replace("\"", "\\\"");
//
//        String body = String.format(
//                "{ \"messaging_product\": \"whatsapp\", " +
//                        "\"to\": \"%s\", " +
//                        "\"type\": \"text\", " +
//                        "\"text\": { \"body\": \"%s\" } }",
//                to, message
//        );
//
//        send(body);
//    }


    private void sendText(String to, String message) {

        // 🔥 CRITICAL FIX
        message = message
                .replace("\"", "\\\"")   // escape quotes
                .replace("\n", "\\n");   // escape newline

        String body = String.format(
                "{ \"messaging_product\": \"whatsapp\", " +
                        "\"to\": \"%s\", " +
                        "\"type\": \"text\", " +
                        "\"text\": { \"body\": \"%s\" } }",
                to, message
        );

        send(body);
    }

    // 🔹 BUTTON
    private void sendWelcomeButtons(String to) {

        String body = """
        {
          "messaging_product": "whatsapp",
          "to": "%s",
          "type": "interactive",
          "interactive": {
            "type": "button",
            "body": {
              "text": "Welcome to Demo Hospital 🏥"
            },
            "action": {
              "buttons": [
                {
                  "type": "reply",
                  "reply": {
                    "id": "NEW_PATIENT",
                    "title": "New Patient"
                  }
                },
                {
                  "type": "reply",
                  "reply": {
                    "id": "OLD_PATIENT",
                    "title": "Old Patient"
                  }
                }
              ]
            }
          }
        }
        """.formatted(to);

        send(body);
    }

    // 🔹 DATE LIST
    private void sendDateList(String to) {

        System.out.println("🔥 sendDateList CALLED");

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
                  "title": "Dates",
                  "rows": [
                    { "id": "DATE_1", "title": "01 May" },
                    { "id": "DATE_2", "title": "02 May" },
                    { "id": "DATE_3", "title": "03 May" }
                  ]
                }
              ]
            }
          }
        }
        """.formatted(to);

        send(body);
    }

    // 🔹 SLOT LIST
    private void sendSlotList(String to) {

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
                  "title": "Slots",
                  "rows": [
                    { "id": "SLOT_1", "title": "10:00 AM" },
                    { "id": "SLOT_2", "title": "12:00 PM" },
                    { "id": "SLOT_3", "title": "02:00 PM" }
                  ]
                }
              ]
            }
          }
        }
        """.formatted(to);

        send(body);
    }

    // 🔹 COMMON API CALL
    private void send(String body) {

        System.out.println("REQUEST BODY: " + body);

        String url = "https://graph.facebook.com/v18.0/" + phoneNumberId + "/messages";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(url, request, String.class);

        System.out.println("RESPONSE STATUS: " + response.getStatusCode());
        System.out.println("RESPONSE BODY: " + response.getBody());
    }
}

