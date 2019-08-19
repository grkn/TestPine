package com.friends.tanistan.service;

import com.friends.tanistan.controller.resource.ErrorResource;
import com.friends.tanistan.entity.UserAuthorization;
import com.friends.tanistan.entity.UserEntity;
import com.friends.tanistan.exception.NotFoundException;
import com.friends.tanistan.repository.UserAuthorizationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserAuthorizationService extends BaseService {

    private final UserAuthorizationRepository userAuthorizationRepository;
    private final UserService<UserEntity> userService;

    private Logger logger = LoggerFactory.getLogger(UserAuthorizationService.class);

    public UserAuthorizationService(UserAuthorizationRepository userAuthorizationRepository,
            UserService<UserEntity> userService) {
        this.userAuthorizationRepository = userAuthorizationRepository;
        this.userService = userService;
    }

    public List<UserAuthorization> createAuthorizations(List<UserAuthorization> authorizations) {
        List<UserAuthorization> addedAuthorizations = new ArrayList<>();
        for (UserAuthorization userAuthorization : authorizations) {
            try {
                UserAuthorization savedAuthorization = userAuthorizationRepository.save(userAuthorization);
                addedAuthorizations.add(savedAuthorization);
            } catch (DataIntegrityViolationException ex) {
                logger.warn(
                        String.format("Authorization Unique Constraint exception. Authority : %s", userAuthorization),
                        ex);
            }
        }
        return addedAuthorizations;
    }

    @Transactional
    public UserEntity givePermissionToUser(String userId, List<UserAuthorization> userAuthorizations) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserAuthorization(new HashSet<>());

        userAuthorizations.forEach(
                userAuthorization -> {
                    Optional<UserAuthorization> userAuthorizationOptional = userAuthorizationRepository
                            .findByAuthority(userAuthorization.getAuthority());
                    if (!userAuthorizationOptional.isPresent()) {
                        throw new NotFoundException(ErrorResource.ErrorContent.builder().message(
                                String.format("Authorization can not be found by given name : %s",
                                        userAuthorization.getAuthority())).build(""));


                    } else {
                        userEntity.getUserAuthorization().add(userAuthorizationOptional.get());
                    }
                });

        return userService.updateUser(userId, userEntity);
    }

    public Page<UserAuthorization> findAll(Pageable pageable) {
        return userAuthorizationRepository.findAll(pageable);
    }
}
