package com.friends.tanistan.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.friends.tanistan.entity.UserEntity;
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

}
