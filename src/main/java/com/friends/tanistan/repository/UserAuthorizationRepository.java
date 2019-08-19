package com.friends.tanistan.repository;

import com.friends.tanistan.entity.UserAuthorization;

import java.util.Optional;

public interface UserAuthorizationRepository extends BaseTanistanJpaRepository<UserAuthorization> {

    Optional<UserAuthorization> findByAuthority(String authority);
}
