package com.friends.tanistan.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.friends.tanistan.controller.resource.ErrorResource;
import com.friends.tanistan.entity.UserEntity;
import com.friends.tanistan.exception.NotFoundException;
import com.friends.tanistan.repository.UserRepository;

@Service
public class UserService<T extends UserEntity> extends BaseService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public UserEntity createUserEntity(UserEntity userEntity) {
		userEntity.setAccountPhrase(passwordEncoder.encode(userEntity.getAccountPhrase()));
		return userRepository.save(userEntity);
	}

	public Page<UserEntity> getUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	public UserEntity getUserById(String id) {
		return userRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorResource.ErrorContent.builder()
				.message(String.format("User can not be found by given id : %s", id)).build("")));
	}

	public UserEntity updateUser(String id, UserEntity user) {
		UserEntity userEntity = getUserById(id);
		overrideVariables(user, userEntity);
		return userRepository.save(userEntity);
	}

	public void deleteUser(String id) {
		userRepository.deleteById(id);
	}

	public UserEntity getUserByUsernameOrEmail(String userName, String email) {
		return userRepository.findByAccountNameOrEmailAddress(userName, email);
	}

	private void overrideVariables(UserEntity user, UserEntity userEntity) {
		userEntity.setName(user.getName());
		userEntity.setBirthDay(user.getBirthDay());
		userEntity.setEmailAddress(user.getEmailAddress());
		userEntity.setLastName(user.getLastName());
		userEntity.setMiddleName(user.getMiddleName());
		userEntity.setPhoneNumber(user.getPhoneNumber());
		userEntity.setSecretQuestion(user.getSecretQuestion());
		userEntity.setUserAuthorization(user.getUserAuthorization());

		userEntity.setSecretAnswer(user.getSecretAnswer());
		if (user.getAttemptType() != null) {
			userEntity.setAttemptType(user.getAttemptType());
		}
		if (user.getLoginAttempt() != null) {
			userEntity.setLoginAttempt(user.getLoginAttempt());
		}
		if (userEntity.getAccountPhrase() != null) {
			userEntity.setAccountPhrase(passwordEncoder.encode(userEntity.getAccountPhrase()));
		}
	}

}
