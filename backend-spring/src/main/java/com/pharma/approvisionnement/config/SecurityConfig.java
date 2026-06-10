/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.servlet.Filter
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.security.authentication.AuthenticationManager
 *  org.springframework.security.authentication.AuthenticationProvider
 *  org.springframework.security.authentication.dao.DaoAuthenticationProvider
 *  org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
 *  org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
 *  org.springframework.security.config.annotation.web.builders.HttpSecurity
 *  org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
 *  org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
 *  org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer$AuthorizedUrl
 *  org.springframework.security.config.http.SessionCreationPolicy
 *  org.springframework.security.core.userdetails.UserDetailsService
 *  org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
 *  org.springframework.security.crypto.password.PasswordEncoder
 *  org.springframework.security.web.SecurityFilterChain
 *  org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
 */
package com.pharma.approvisionnement.config;

import com.pharma.approvisionnement.security.JwtFilter;
import com.pharma.approvisionnement.security.UserDetailsServiceImpl;
import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final UserDetailsServiceImpl userDetailsService;
    private static final String[] PUBLIC_URLS = new String[]{"/api/auth/**", "/swagger-ui/**", "/swagger-ui.html", "/api-docs/**", "/v3/api-docs/**", "/api/grossiste/**"};

    public SecurityConfig(JwtFilter jwtFilter, UserDetailsServiceImpl userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).cors(cors -> {}).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(auth -> ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)auth.requestMatchers(PUBLIC_URLS)).permitAll().anyRequest()).authenticated()).authenticationProvider(this.authenticationProvider()).addFilterBefore((Filter)this.jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return (SecurityFilterChain)http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService((UserDetailsService)this.userDetailsService);
        provider.setPasswordEncoder(this.passwordEncoder());
        return provider;
    }
}

