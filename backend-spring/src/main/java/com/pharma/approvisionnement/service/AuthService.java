/*
 * Decompiled with CFR 0.152.
 */
package com.pharma.approvisionnement.service;

import com.pharma.approvisionnement.dto.LoginRequest;
import com.pharma.approvisionnement.dto.LoginResponse;

public interface AuthService {
    public LoginResponse signupPharmacie(LoginRequest var1);

    public LoginResponse loginPharmacie(LoginRequest var1);

    public LoginResponse loginGrossiste(LoginRequest var1);
}

