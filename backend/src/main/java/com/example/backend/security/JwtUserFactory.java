package com.example.backend.security;

import java.util.ArrayList;
import java.util.List;

import com.example.backend.model.Authority;
import com.example.backend.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


/**
 * user factory for creating token
 */
public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                String.valueOf(user.getId()),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getPasswd(),
                mapToGrantedAuthorities(user.getAuthority()),
                user.isEnabled(),
                user.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Authority authority) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(authority.getName().name()));
        return authorities;
    }
}