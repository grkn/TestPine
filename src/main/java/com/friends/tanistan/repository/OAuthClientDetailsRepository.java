package com.friends.tanistan.repository;

public interface OAuthClientDetailsRepository {

    void insertAccessToken(String clientId,String clientSecret,String roles);
}
