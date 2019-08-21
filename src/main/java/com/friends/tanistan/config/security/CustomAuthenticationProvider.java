package com.friends.tanistan.config.security;

import com.friends.tanistan.entity.UserEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final Authenticate authenticate;

    public CustomAuthenticationProvider(Authenticate authenticate) {
        this.authenticate = authenticate;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        return authenticate.authenticate(authentication);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}