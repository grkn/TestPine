package com.friends.tanistan.config.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class CustomBasicAuthenticationFilter extends BasicAuthenticationFilter {

    public CustomBasicAuthenticationFilter(
            AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }
}
