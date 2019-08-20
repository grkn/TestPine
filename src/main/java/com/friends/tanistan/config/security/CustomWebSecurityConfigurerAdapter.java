package com.friends.tanistan.config.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {


    private CustomAuthenticationProvider customAuthenticationProvider;
    private CustomUserDetailService customUserDetailService;
    private CustomBasicAuthenticationFilter customBasicAuthenticationFilter;

    public CustomWebSecurityConfigurerAdapter(CustomAuthenticationProvider customAuthenticationProvider,
            CustomUserDetailService customUserDetailService,
            CustomBasicAuthenticationFilter customBasicAuthenticationFilter) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.customUserDetailService = customUserDetailService;
        this.customBasicAuthenticationFilter = customBasicAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/tanistan/**").fullyAuthenticated().and()
                .authenticationProvider(customAuthenticationProvider).userDetailsService(customUserDetailService)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().formLogin().loginProcessingUrl("/login")
                .usernameParameter("username").passwordParameter("password").and()
                .addFilterBefore(customBasicAuthenticationFilter,
                        BasicAuthenticationFilter.class);
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return customUserDetailService;
    }

}
