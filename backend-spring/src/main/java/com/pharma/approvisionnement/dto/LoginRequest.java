/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.validation.constraints.Email
 *  jakarta.validation.constraints.NotBlank
 */
package com.pharma.approvisionnement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message="L'email est requis")
    @Email
    private @NotBlank(message="L'email est requis") @Email String email;
    @NotBlank(message="Le mot de passe est requis")
    private @NotBlank(message="Le mot de passe est requis") String password;
    private String mode = "login";
    private String name;

    public String getPassword() {
        return this.password;
    }

    public String getName() {
        return this.name;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LoginRequest)) {
            return false;
        }
        LoginRequest other = (LoginRequest)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$email = this.getEmail();
        String other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) {
            return false;
        }
        String this$password = this.getPassword();
        String other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) {
            return false;
        }
        String this$mode = this.getMode();
        String other$mode = other.getMode();
        if (this$mode == null ? other$mode != null : !this$mode.equals(other$mode)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        return !(this$name == null ? other$name != null : !this$name.equals(other$name));
    }

    public String toString() {
        return "LoginRequest(email=" + this.getEmail() + ", password=" + this.getPassword() + ", mode=" + this.getMode() + ", name=" + this.getName() + ")";
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $email = this.getEmail();
        result = result * 59 + ($email == null ? 43 : $email.hashCode());
        String $password = this.getPassword();
        result = result * 59 + ($password == null ? 43 : $password.hashCode());
        String $mode = this.getMode();
        result = result * 59 + ($mode == null ? 43 : $mode.hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMode() {
        return this.mode;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    protected boolean canEqual(Object other) {
        return other instanceof LoginRequest;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}

