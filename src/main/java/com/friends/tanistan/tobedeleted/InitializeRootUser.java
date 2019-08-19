package com.friends.tanistan.tobedeleted;

import com.friends.tanistan.entity.UserAuthorization;
import com.friends.tanistan.entity.UserEntity;
import com.friends.tanistan.repository.OAuthClientDetailsRepository;
import com.friends.tanistan.repository.UserAuthorizationRepository;
import com.friends.tanistan.repository.UserRepository;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * TO BE DELETED
 *
 * @author grkn
 */
@Component
public class InitializeRootUser {

    // INITIALIZE USER AND OAuth2 implementation JWT Token
    // TO BE DELETED
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthorizationRepository userAuthorizationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OAuthClientDetailsRepository oAuthClientDetailsRepository;
    
    private final static String CLIENT_ID = "grkn";
    private final static String PASSWORD = "grkn";

    // TO BE DELETED
    @PostConstruct
    @Transactional
    public void setUp() {

        String encodedPassword = passwordEncoder.encode(PASSWORD);
        System.out.println("******************************");
        System.out.println("Encoded Password : " + encodedPassword);
        System.out.println("******************************");
        UserEntity userEntity = new UserEntity();
        userEntity.setAccountName(CLIENT_ID);
        userEntity.setAccountPhrase(encodedPassword);
        userEntity.setEmailAddress("gurkanilleez@gmail.com");
        userEntity = userRepository.save(userEntity);
        
        // AUTHS
        UserAuthorization userAuth = new UserAuthorization();
        userAuth.setAuthority("ROLE_ADMIN");
        userAuth.setUserEntity(userEntity);
        userAuthorizationRepository.save(userAuth);

        UserAuthorization userAuth2 = new UserAuthorization();
        userAuth2.setAuthority("ROLE_USER");
        userAuth2.setUserEntity(userEntity);
        userAuthorizationRepository.save(userAuth2);

        
        userEntity.setUserAuthorization(Sets.newHashSet(userAuth, userAuth2));
        userRepository.save(userEntity);
        
        try {
            // For root 1 hour to accessToken, 1 year to refreshTokenValidity
            oAuthClientDetailsRepository
                    .insertAccessToken(CLIENT_ID, encodedPassword, "ROLE_ADMIN,ROLE_USER", 60 * 60,
                            60 * 60 * 24 * 365);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
