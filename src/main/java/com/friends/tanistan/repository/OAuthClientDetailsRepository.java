package com.friends.tanistan.repository;

import java.util.List;

public interface OAuthClientDetailsRepository {

    void insertAccessToken(String clientId, String clientSecret, String roles, int accessTokenValiditySeconds,
            long refreshTokenValiditySeconds);

    void updateAuthorities(List<String> authorities, String clientId);
}
