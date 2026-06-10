/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.jsonwebtoken.Claims
 *  io.jsonwebtoken.Jwts
 *  io.jsonwebtoken.SignatureAlgorithm
 *  io.jsonwebtoken.security.Keys
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.security.core.userdetails.UserDetails
 *  org.springframework.stereotype.Component
 */
package com.pharma.approvisionnement.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    @Value(value="${jwt.secret}")
    private String secretKey;
    @Value(value="${jwt.expiration}")
    private long expiration;

    private Key getSignKey() {
        return Keys.hmacShaKeyFor((byte[])this.secretKey.getBytes());
    }

    public String generateToken(UserDetails userDetails) {
        return this.generateToken(userDetails, new HashMap<String, Object>());
    }

    public String generateToken(UserDetails userDetails, Map<String, Object> extraClaims) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername()).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + this.expiration)).signWith(this.getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private boolean isTokenExpired(String token) {
        return this.extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return this.extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return (Claims)Jwts.parserBuilder().setSigningKey(this.getSignKey()).build().parseClaimsJws(token).getBody();
    }

    public String generateTokenWithClaims(UserDetails userDetails, String role, String nom, String nomGrossiste) {
        HashMap<String, Object> claims = new HashMap<String, Object>();
        claims.put("role", role);
        claims.put("nom", nom);
        if (nomGrossiste != null) {
            claims.put("nomGrossiste", nomGrossiste);
        }
        return this.generateToken(userDetails, claims);
    }

    public String extractUsername(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = this.extractUsername(token);
        return username.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
    }

    public String extractRole(String token) {
        return this.extractClaim(token, claims -> (String)claims.get("role", String.class));
    }

    public String extractNomGrossiste(String token) {
        return this.extractClaim(token, claims -> (String)claims.get("nomGrossiste", String.class));
    }
}

