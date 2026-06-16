package com.hospital_single_app.demo.controller;

import com.hospital_single_app.demo.service.WhatsAppService;
import com.hospital_single_app.demo.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @Value("${whatsapp.verify-token}")
    private String verifyToken;

    private final WhatsAppService whatsAppService;

    // 🔥 IMPORTANT: store processed message IDs
    private final Set<String> processedMessages = new HashSet<>();

    public WebhookController(WhatsAppService whatsAppService) {
        this.whatsAppService = whatsAppService;
    }

    // ✅ Verification
    @GetMapping
    public ResponseEntity<String> verifyWebhook(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.verify_token") String token,
            @RequestParam("hub.challenge") String challenge) {

        if ("subscribe".equals(mode) && verifyToken.equals(token)) {
            return ResponseEntity.ok(challenge);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    // ✅ Receive Message
    @PostMapping
    public ResponseEntity<String> receiveMessage(@RequestBody Map<String, Object> payload) {

        System.out.println("WEBHOOK DATA: " + payload);

        try {
            String messageId = JsonUtil.extractMessageId(payload);

            // ❌ ignore if not actual message
            if (messageId == null) {
                return ResponseEntity.ok("IGNORED");
            }

            // 🔥 DUPLICATE FIX
            if (processedMessages.contains(messageId)) {
                System.out.println("DUPLICATE MESSAGE IGNORED: " + messageId);
                return ResponseEntity.ok("DUPLICATE");
            }

            processedMessages.add(messageId);

            String from = JsonUtil.extractPhone(payload);
            String message = JsonUtil.extractMessage(payload);

            if (from != null && message != null) {
                whatsAppService.handleMessage(from, message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok("EVENT_RECEIVED");
    }
}