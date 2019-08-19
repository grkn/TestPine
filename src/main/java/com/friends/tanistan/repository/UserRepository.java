package com.friends.tanistan.repository;

import com.friends.tanistan.entity.UserEntity;

public interface UserRepository extends BaseTanistanJpaRepository<UserEntity> {

	UserEntity findByAccountNameOrEmailAddress(String userName,String email);

	boolean existsByAccountNameOrEmailAddress(String userName,String email);

}
