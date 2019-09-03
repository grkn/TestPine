package com.friends.test.automation.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.friends.test.automation.controller.resource.UserResource;
import com.friends.test.automation.controller.resource.UserResourceWithAccessToken;
import com.friends.test.automation.entity.UserEntity;
import com.friends.test.automation.service.UserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;
    private final UserService<UserEntity> userService;
    private final ConversionService conversionService;
    private final TokenStore tokenStore;
    private final DefaultTokenServices defaultTokenServices;

    public CustomAuthSuccessHandler(ObjectMapper objectMapper, UserService<UserEntity> userService,
            ConversionService conversionService, TokenStore tokenStore,
            DefaultTokenServices defaultTokenServices) {
        this.objectMapper = objectMapper;
        this.userService = userService;
        this.conversionService = conversionService;
        this.tokenStore = tokenStore;
        this.defaultTokenServices = defaultTokenServices;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {

        UserEntity userEntity = userService.getUserByUsernameOrEmail(authentication.getPrincipal().toString(),
                authentication.getPrincipal().toString());

        UserResource userResource = conversionService.convert(userEntity, UserResource.class);
        if(userEntity.getCompany() != null) {
            userResource.setCompanyId(userEntity.getCompany().getId());
        }
        UserResourceWithAccessToken userResourceWithAccessToken = new UserResourceWithAccessToken(userResource);

        OAuth2Request oAuth2Request = prepareOAuth2Request(authentication);

        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,
                SecurityContextHolder.getContext().getAuthentication());

        OAuth2AccessToken accessToken = tokenStore.getAccessToken(oAuth2Authentication);

        if (accessToken != null && !accessToken.isExpired()) {
            userResourceWithAccessToken.setAccessToken(tokenStore.getAccessToken(oAuth2Authentication).getValue());
        } else {
            userResourceWithAccessToken
                    .setAccessToken(defaultTokenServices.createAccessToken(oAuth2Authentication).getValue());

        }

        response.getWriter().write(objectMapper.writeValueAsString(userResourceWithAccessToken) + "\n");
        response.setHeader("Content-Type", "application/json");

        if (!response.getHeaders("Access-Control-Allow-Origin").contains("*")) {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "*");
            response.addHeader("Access-Control-Allow-Headers", "*");
            response.addHeader("Access-Control-Expose-Headers",
                    "Access-Control-Allow-Origin, Access-Control-Allow-Credentials");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addIntHeader("Access-Control-Max-Age", 100);
        }
        response.flushBuffer();
    }

    private OAuth2Request prepareOAuth2Request(Authentication authentication) {
        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("client_id", authentication.getPrincipal().toString());
        requestParameters.put("client_secret", authentication.getCredentials().toString());

        return new OAuth2Request(requestParameters, null, null, true, null, null,
                null, null, null);
    }

}
