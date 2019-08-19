package com.friends.tanistan.controller.converter;

import com.friends.tanistan.controller.dto.UserDto;
import com.friends.tanistan.entity.UserAuthorization;
import com.friends.tanistan.entity.UserEntity;
import com.friends.tanistan.enums.AttemptType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;

public class UserDtoToUserEntityConverter implements Converter<UserDto, UserEntity> {

    @Override
    public UserEntity convert(UserDto source) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(source.getName());
        userEntity.setBirthDay(source.getBirthDay());
        userEntity.setEmailAddress(source.getEmailAddress());
        userEntity.setLastName(source.getLastName());
        userEntity.setMiddleName(source.getMiddleName());
        userEntity.setPhoneNumber(source.getPhoneNumber());
        userEntity.setSecretQuestion(source.getSecretQuestion());
        userEntity.setAccountName(source.getAccountName());
        if (!CollectionUtils.isEmpty(source.getUserAuthorzation())) {
            userEntity.setUserAuthorization(source.getUserAuthorzation().stream().map(uAuth -> {
                UserAuthorization userAuthorization = new UserAuthorization();
                userAuthorization.setAuthority(uAuth.getAuhtorization());
                userAuthorization.setUserEntity(userEntity);
                return userAuthorization;
            }).collect(Collectors.toSet()));
        }
        userEntity.setSecretAnswer(source.getSecretAnswer());

        if (source.getAttemptType() != null) {
            userEntity.setAttemptType(AttemptType.valueOf(source.getAttemptType()));
        }
        if (source.getLoginAttempt() != null) {
            userEntity.setLoginAttempt(source.getLoginAttempt());
        }

        userEntity.setAccountPhrase(source.getAccountPhrase());
        return userEntity;
    }
}
