package com.friends.tanistan.controller.converter;

import org.springframework.core.convert.converter.Converter;

import com.friends.tanistan.controller.dto.UserDto;
import com.friends.tanistan.entity.UserEntity;
import com.friends.tanistan.enums.AttemptType;

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

		userEntity.setSecretAnswer(source.getSecretAnswer());
		userEntity.setAttemptType(AttemptType.SUCCESS);
		userEntity.setLoginAttempt(0);

		userEntity.setAccountPhrase(source.getAccountPhrase());
		return userEntity;
	}
}
