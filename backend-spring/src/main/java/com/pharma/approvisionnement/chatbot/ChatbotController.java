/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.v3.oas.annotations.Operation
 *  io.swagger.v3.oas.annotations.security.SecurityRequirement
 *  io.swagger.v3.oas.annotations.tags.Tag
 *  jakarta.validation.Valid
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.pharma.approvisionnement.chatbot;

import com.pharma.approvisionnement.chatbot.ChatbotService;
import com.pharma.approvisionnement.dto.ChatbotRequest;
import com.pharma.approvisionnement.dto.ChatbotResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/chatbot"})
@SecurityRequirement(name="bearerAuth")
@Tag(name="Chatbot", description="Assistant m\u00e9tier pharmaceutique (logique locale)")
public class ChatbotController {
    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping(value={"/message"})
    @Operation(summary="Envoyer un message au chatbot pharmaceutique")
    public ResponseEntity<ChatbotResponse> envoyerMessage(@Valid @RequestBody ChatbotRequest request) {
        ChatbotResponse response = this.chatbotService.traiterMessage(request);
        return ResponseEntity.ok(response);
    }
}

