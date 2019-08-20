package com.friends.tanistan.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final CustomBasicAuthenticationFilter customBasicAuthenticationFilter;

    public ResourceServerConfig(CustomAuthenticationProvider customAuthenticationProvider,
            CustomBasicAuthenticationFilter customBasicAuthenticationFilter) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.customBasicAuthenticationFilter = customBasicAuthenticationFilter;
    }

    @Autowired
    private ResourceServerTokenServices tokenServices;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenServices(tokenServices);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authenticationProvider(customAuthenticationProvider)
                .requestMatchers().and().authorizeRequests().antMatchers("/actuator/**", "/api-docs/**").permitAll()
                .and().requestMatchers().and().authorizeRequests().antMatchers("/tanistan/**").authenticated()
                .and().requestMatchers().and().authorizeRequests().antMatchers("/oauth/*").permitAll().and()
                .addFilterBefore(customBasicAuthenticationFilter, BasicAuthenticationFilter.class);
    }

}
