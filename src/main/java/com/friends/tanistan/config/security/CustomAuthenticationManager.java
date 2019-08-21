package com.friends.tanistan.config.security;

import com.friends.tanistan.entity.UserEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationManager extends ProviderManager {

    private final Authenticate authenticate;

    public CustomAuthenticationManager(CustomAuthenticationProvider customAuthenticationProvider,
            Authenticate authenticate) {
        super(Arrays.asList(customAuthenticationProvider));

        this.authenticate = authenticate;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return authenticate.authenticate(authentication);
    }
}