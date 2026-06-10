/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.authority.SimpleGrantedAuthority
 *  org.springframework.security.core.userdetails.User
 *  org.springframework.security.core.userdetails.UserDetails
 *  org.springframework.security.core.userdetails.UserDetailsService
 *  org.springframework.security.core.userdetails.UsernameNotFoundException
 *  org.springframework.stereotype.Service
 */
package com.pharma.approvisionnement.security;

import com.pharma.approvisionnement.repository.UserRepository;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl
implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.pharma.approvisionnement.entity.User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouv\u00e9 avec l'email : " + email));
        return User.builder().username(user.getEmail()).password(user.getPassword()).authorities(List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))).build();
    }
}

