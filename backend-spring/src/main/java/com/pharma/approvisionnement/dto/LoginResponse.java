/*
 * Decompiled with CFR 0.152.
 */
package com.pharma.approvisionnement.dto;

public class LoginResponse {
    private String status;
    private String message;
    private String token;
    private String userName;
    private Long userId;
    private String role;
    private String nomGrossiste;

    public String getToken() {
        return this.token;
    }

    public LoginResponse(String status, String message, String token, String userName, Long userId, String role, String nomGrossiste) {
        this.status = status;
        this.message = message;
        this.token = token;
        this.userName = userName;
        this.userId = userId;
        this.role = role;
        this.nomGrossiste = nomGrossiste;
    }

    public LoginResponse() {
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LoginResponse)) {
            return false;
        }
        LoginResponse other = (LoginResponse)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Long this$userId = this.getUserId();
        Long other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !((Object)this$userId).equals(other$userId)) {
            return false;
        }
        String this$status = this.getStatus();
        String other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) {
            return false;
        }
        String this$message = this.getMessage();
        String other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) {
            return false;
        }
        String this$token = this.getToken();
        String other$token = other.getToken();
        if (this$token == null ? other$token != null : !this$token.equals(other$token)) {
            return false;
        }
        String this$userName = this.getUserName();
        String other$userName = other.getUserName();
        if (this$userName == null ? other$userName != null : !this$userName.equals(other$userName)) {
            return false;
        }
        String this$role = this.getRole();
        String other$role = other.getRole();
        if (this$role == null ? other$role != null : !this$role.equals(other$role)) {
            return false;
        }
        String this$nomGrossiste = this.getNomGrossiste();
        String other$nomGrossiste = other.getNomGrossiste();
        return !(this$nomGrossiste == null ? other$nomGrossiste != null : !this$nomGrossiste.equals(other$nomGrossiste));
    }

    public String toString() {
        return "LoginResponse(status=" + this.getStatus() + ", message=" + this.getMessage() + ", token=" + this.getToken() + ", userName=" + this.getUserName() + ", userId=" + this.getUserId() + ", role=" + this.getRole() + ", nomGrossiste=" + this.getNomGrossiste() + ")";
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $userId = this.getUserId();
        result = result * 59 + ($userId == null ? 43 : ((Object)$userId).hashCode());
        String $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        String $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        String $token = this.getToken();
        result = result * 59 + ($token == null ? 43 : $token.hashCode());
        String $userName = this.getUserName();
        result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
        String $role = this.getRole();
        result = result * 59 + ($role == null ? 43 : $role.hashCode());
        String $nomGrossiste = this.getNomGrossiste();
        result = result * 59 + ($nomGrossiste == null ? 43 : $nomGrossiste.hashCode());
        return result;
    }

    public static LoginResponseBuilder builder() {
        return new LoginResponseBuilder();
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNomGrossiste() {
        return this.nomGrossiste;
    }

    public void setNomGrossiste(String nomGrossiste) {
        this.nomGrossiste = nomGrossiste;
    }

    protected boolean canEqual(Object other) {
        return other instanceof LoginResponse;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public static class LoginResponseBuilder {
        private String status;
        private String message;
        private String token;
        private String userName;
        private Long userId;
        private String role;
        private String nomGrossiste;

        LoginResponseBuilder() {
        }

        public String toString() {
            return "LoginResponse.LoginResponseBuilder(status=" + this.status + ", message=" + this.message + ", token=" + this.token + ", userName=" + this.userName + ", userId=" + this.userId + ", role=" + this.role + ", nomGrossiste=" + this.nomGrossiste + ")";
        }

        public LoginResponseBuilder status(String status) {
            this.status = status;
            return this;
        }

        public LoginResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public LoginResponse build() {
            return new LoginResponse(this.status, this.message, this.token, this.userName, this.userId, this.role, this.nomGrossiste);
        }

        public LoginResponseBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public LoginResponseBuilder token(String token) {
            this.token = token;
            return this;
        }

        public LoginResponseBuilder role(String role) {
            this.role = role;
            return this;
        }

        public LoginResponseBuilder nomGrossiste(String nomGrossiste) {
            this.nomGrossiste = nomGrossiste;
            return this;
        }

        public LoginResponseBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }
    }
}

