/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.stereotype.Service
 */
package com.pharma.approvisionnement.chatbot;

import com.pharma.approvisionnement.chatbot.ChatbotEngine;
import com.pharma.approvisionnement.dto.ChatbotRequest;
import com.pharma.approvisionnement.dto.ChatbotResponse;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ChatbotService {
    private static final Logger log = LoggerFactory.getLogger(ChatbotService.class);
    private final ChatbotEngine chatbotEngine;

    public ChatbotService(ChatbotEngine chatbotEngine) {
        this.chatbotEngine = chatbotEngine;
    }

    public ChatbotResponse traiterMessage(ChatbotRequest request) {
        log.debug("Chatbot re\u00e7oit message (session={}) : {}", (Object)request.getSessionId(), (Object)request.getMessage());
        String reponse = this.chatbotEngine.analyser(request.getMessage());
        log.debug("Chatbot r\u00e9pond : {}", (Object)reponse.substring(0, Math.min(80, reponse.length())));
        return ChatbotResponse.builder().response(reponse).timestamp(LocalDateTime.now()).build();
    }
}

