package com.friends.tanistan.repository;

import com.friends.tanistan.entity.UserAuthorization;

import java.util.List;

public interface OAuthClientDetailsRepository {

    void insertAccessToken(String clientId,String clientSecret,String roles);

    void updateAuthorities(List<String> authorities, String clientId);
}
