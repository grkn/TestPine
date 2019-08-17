package com.friends.tanistan.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class UserAuthorization extends TanistanBaseEntity<String> {

	private String authority;

	@ManyToOne
	private UserEntity userEntity;

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

}
