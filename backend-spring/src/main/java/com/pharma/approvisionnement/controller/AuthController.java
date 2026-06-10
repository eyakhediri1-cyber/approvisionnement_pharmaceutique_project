/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.v3.oas.annotations.Operation
 *  io.swagger.v3.oas.annotations.tags.Tag
 *  jakarta.validation.Valid
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.pharma.approvisionnement.controller;

import com.pharma.approvisionnement.dto.LoginRequest;
import com.pharma.approvisionnement.dto.LoginResponse;
import com.pharma.approvisionnement.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/auth"})
@Tag(name="Authentification", description="Login et inscription pharmacie/grossiste")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value={"/login"})
    @Operation(summary="Login ou signup pharmacien")
    public ResponseEntity<LoginResponse> loginPharmacie(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = "signup".equalsIgnoreCase(request.getMode()) ? this.authService.signupPharmacie(request) : this.authService.loginPharmacie(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value={"/login-grossiste"})
    @Operation(summary="Login grossiste")
    public ResponseEntity<LoginResponse> loginGrossiste(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(this.authService.loginGrossiste(request));
    }
}

