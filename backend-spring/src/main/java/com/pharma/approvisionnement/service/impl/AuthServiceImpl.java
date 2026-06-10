/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.security.core.userdetails.User
 *  org.springframework.security.core.userdetails.UserDetails
 *  org.springframework.security.crypto.password.PasswordEncoder
 *  org.springframework.stereotype.Service
 */
package com.pharma.approvisionnement.service.impl;

import com.pharma.approvisionnement.dto.LoginRequest;
import com.pharma.approvisionnement.dto.LoginResponse;
import com.pharma.approvisionnement.entity.User;
import com.pharma.approvisionnement.repository.UserRepository;
import com.pharma.approvisionnement.security.JwtUtil;
import com.pharma.approvisionnement.service.AuthService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl
implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private static final List<String> GROSSISTES = List.of("MedicaLab", "MeDistrib", "PharmRestock");

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginResponse signupPharmacie(LoginRequest request) {
        if (this.userRepository.existsByEmail(request.getEmail())) {
            return LoginResponse.builder().status("error").message("Cet email est déjà utilisé").build();
        }
        com.pharma.approvisionnement.entity.User newUser = com.pharma.approvisionnement.entity.User.builder().name(request.getName() != null ? request.getName() : "Utilisateur").email(request.getEmail()).password(this.passwordEncoder.encode((CharSequence)request.getPassword())).role(User.Role.PHARMACIE).build();
        com.pharma.approvisionnement.entity.User saved = (com.pharma.approvisionnement.entity.User)this.userRepository.save(newUser);
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(saved.getEmail()).password(saved.getPassword()).roles(new String[]{"PHARMACIE"}).build();
        String token = this.jwtUtil.generateTokenWithClaims(userDetails, "pharmacie", saved.getName(), null);
        log.info("Inscription pharmacie : {}", (Object)saved.getEmail());
        return LoginResponse.builder().status("success").message("Compte créé").token(token).userName(saved.getName()).userId(saved.getId()).role("pharmacie").build();
    }

    @Override
    public LoginResponse loginPharmacie(LoginRequest request) {
        com.pharma.approvisionnement.entity.User user = this.userRepository.findByEmail(request.getEmail()).orElse(null);
        if (user == null || !this.passwordEncoder.matches((CharSequence)request.getPassword(), user.getPassword())) {
            return LoginResponse.builder().status("error").message("Email ou mot de passe incorrect").build();
        }
        if (user.getRole() != User.Role.PHARMACIE) {
            return LoginResponse.builder().status("error").message("Ce compte n'est pas un compte pharmacie").build();
        }
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getEmail()).password(user.getPassword()).roles(new String[]{user.getRole().name()}).build();
        String token = this.jwtUtil.generateTokenWithClaims(userDetails, user.getRole().name().toLowerCase(), user.getName(), null);
        log.info("Connexion pharmacie réussie : {}", (Object)user.getEmail());
        return LoginResponse.builder().status("success").message("Connexion réussie").token(token).userName(user.getName()).userId(user.getId()).role("pharmacie").build();
    }

    @Override
    public LoginResponse loginGrossiste(LoginRequest request) {
        Optional<com.pharma.approvisionnement.entity.User> optUser = this.userRepository.findByEmail(request.getEmail());
        if (optUser.isEmpty() || !this.passwordEncoder.matches((CharSequence)request.getPassword(), optUser.get().getPassword())) {
            return LoginResponse.builder().status("error").message("Email ou mot de passe incorrect").build();
        }
        com.pharma.approvisionnement.entity.User user = optUser.get();
        if (user.getRole() != User.Role.GROSSISTE) {
            return LoginResponse.builder().status("error").message("Ce compte n'est pas un compte grossiste").build();
        }
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getEmail()).password(user.getPassword()).roles(new String[]{"GROSSISTE"}).build();
        String token = this.jwtUtil.generateTokenWithClaims(userDetails, "grossiste", user.getName(), user.getNomGrossiste());
        log.info("Connexion grossiste réussie : {} ({})", (Object)user.getEmail(), (Object)user.getNomGrossiste());
        return LoginResponse.builder().status("success").message("Connexion réussie").token(token).userName(user.getName()).userId(user.getId()).role("grossiste").nomGrossiste(user.getNomGrossiste()).build();
    }
}

