package com.friends.tanistan.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.friends.tanistan.controller.dto.UserDto;
import com.friends.tanistan.controller.resource.UserResource;
import com.friends.tanistan.entity.UserEntity;
import com.friends.tanistan.service.UserService;

@RestController
@RequestMapping(value = "/tanistan/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {

	private final UserService<UserEntity> userService;
	private final ConversionService conversionService;

	public UserController(UserService<UserEntity> userService, ConversionService conversionService) {
		this.userService = userService;
		this.conversionService = conversionService;
	}

	@PostMapping
	public ResponseEntity<UserResource> createUser(@RequestBody @Valid UserDto userDto) {
		UserEntity userEntity = conversionService.convert(userDto, UserEntity.class);
		UserResource userResource = conversionService.convert(userService.createUserEntity(userEntity),
				UserResource.class);
		return ResponseEntity.of(Optional.of(userResource));
	}

	@GetMapping(value = "/all")
	public ResponseEntity<Page<UserResource>> getUsers(@PageableDefault Pageable pageable) {
		Page<UserEntity> result = userService.getUsers(pageable);
		return ResponseEntity.of(Optional.of(result.map(item -> conversionService.convert(item, UserResource.class))));
	}
}
