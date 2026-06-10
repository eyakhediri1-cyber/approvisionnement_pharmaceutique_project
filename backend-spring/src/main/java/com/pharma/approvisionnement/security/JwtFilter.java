/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.servlet.FilterChain
 *  jakarta.servlet.ServletException
 *  jakarta.servlet.ServletRequest
 *  jakarta.servlet.ServletResponse
 *  jakarta.servlet.http.HttpServletRequest
 *  jakarta.servlet.http.HttpServletResponse
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.lang.NonNull
 *  org.springframework.security.authentication.UsernamePasswordAuthenticationToken
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.security.core.userdetails.UserDetails
 *  org.springframework.security.web.authentication.WebAuthenticationDetailsSource
 *  org.springframework.stereotype.Component
 *  org.springframework.web.filter.OncePerRequestFilter
 */
package com.pharma.approvisionnement.security;

import com.pharma.approvisionnement.security.JwtUtil;
import com.pharma.approvisionnement.security.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter
extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter((ServletRequest)request, (ServletResponse)response);
            return;
        }
        String jwt = authHeader.substring(7);
        try {
            UserDetails userDetails;
            String userEmail = this.jwtUtil.extractUsername(jwt);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null && this.jwtUtil.isTokenValid(jwt, userDetails = this.userDetailsService.loadUserByUsername(userEmail))) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken((Object)userDetails, null, userDetails.getAuthorities());
                authToken.setDetails((Object)new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication((Authentication)authToken);
            }
        }
        catch (Exception e) {
            log.warn("JWT invalide ou expir\u00e9 : {}", (Object)e.getMessage());
        }
        filterChain.doFilter((ServletRequest)request, (ServletResponse)response);
    }
}

