/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.validation.constraints.NotBlank
 */
package com.pharma.approvisionnement.dto;

import jakarta.validation.constraints.NotBlank;

public class ChatbotRequest {
    @NotBlank(message="Le message est obligatoire")
    private @NotBlank(message="Le message est obligatoire") String message;
    private String sessionId;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ChatbotRequest)) {
            return false;
        }
        ChatbotRequest other = (ChatbotRequest)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$message = this.getMessage();
        String other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) {
            return false;
        }
        String this$sessionId = this.getSessionId();
        String other$sessionId = other.getSessionId();
        return !(this$sessionId == null ? other$sessionId != null : !this$sessionId.equals(other$sessionId));
    }

    public String toString() {
        return "ChatbotRequest(message=" + this.getMessage() + ", sessionId=" + this.getSessionId() + ")";
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        String $sessionId = this.getSessionId();
        result = result * 59 + ($sessionId == null ? 43 : $sessionId.hashCode());
        return result;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    protected boolean canEqual(Object other) {
        return other instanceof ChatbotRequest;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}

