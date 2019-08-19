package com.friends.tanistan.controller.converter;

import com.friends.tanistan.controller.resource.UserAuthorizationResource;
import com.friends.tanistan.entity.UserAuthorization;
import org.springframework.core.convert.converter.Converter;

public class UserAuthorizationToUserAuthorizationDtoConverter implements
        Converter<UserAuthorization, UserAuthorizationResource> {

    @Override
    public UserAuthorizationResource convert(UserAuthorization source) {
        UserAuthorizationResource userAuthorizationResource = new UserAuthorizationResource();
        userAuthorizationResource.setAuthorization(source.getAuthority());
        userAuthorizationResource.setId(source.getId());
        return userAuthorizationResource;
    }
}
