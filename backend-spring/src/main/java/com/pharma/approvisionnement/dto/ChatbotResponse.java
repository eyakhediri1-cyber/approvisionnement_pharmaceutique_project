/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.pharma.approvisionnement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class ChatbotResponse {
    private String response;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    public ChatbotResponse(String response, LocalDateTime timestamp) {
        this.response = response;
        this.timestamp = timestamp;
    }

    public ChatbotResponse() {
        this.timestamp = ChatbotResponse.$default$timestamp();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ChatbotResponse)) {
            return false;
        }
        ChatbotResponse other = (ChatbotResponse)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$response = this.getResponse();
        String other$response = other.getResponse();
        if (this$response == null ? other$response != null : !this$response.equals(other$response)) {
            return false;
        }
        LocalDateTime this$timestamp = this.getTimestamp();
        LocalDateTime other$timestamp = other.getTimestamp();
        return !(this$timestamp == null ? other$timestamp != null : !((Object)this$timestamp).equals(other$timestamp));
    }

    public String toString() {
        return "ChatbotResponse(response=" + this.getResponse() + ", timestamp=" + String.valueOf(this.getTimestamp()) + ")";
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $response = this.getResponse();
        result = result * 59 + ($response == null ? 43 : $response.hashCode());
        LocalDateTime $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : ((Object)$timestamp).hashCode());
        return result;
    }

    public static ChatbotResponseBuilder builder() {
        return new ChatbotResponseBuilder();
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    protected boolean canEqual(Object other) {
        return other instanceof ChatbotResponse;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    private static LocalDateTime $default$timestamp() {
        return LocalDateTime.now();
    }

    public static class ChatbotResponseBuilder {
        private String response;
        private boolean timestamp$set;
        private LocalDateTime timestamp$value;

        ChatbotResponseBuilder() {
        }

        public String toString() {
            return "ChatbotResponse.ChatbotResponseBuilder(response=" + this.response + ", timestamp$value=" + String.valueOf(this.timestamp$value) + ")";
        }

        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        public ChatbotResponseBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp$value = timestamp;
            this.timestamp$set = true;
            return this;
        }

        public ChatbotResponse build() {
            LocalDateTime timestamp$value = this.timestamp$value;
            if (!this.timestamp$set) {
                timestamp$value = ChatbotResponse.$default$timestamp();
            }
            return new ChatbotResponse(this.response, timestamp$value);
        }

        public ChatbotResponseBuilder response(String response) {
            this.response = response;
            return this;
        }
    }
}

