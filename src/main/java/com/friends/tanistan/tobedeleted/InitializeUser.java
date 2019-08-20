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
public class InitializeUser {

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
        createPreDefinedUser(CLIENT_ID, PASSWORD, true);
    }

    public void createPreDefinedUser(String clientId, String password, boolean isEncoded) {
        String encodedPassword = passwordEncoder.encode(password);

        if (isEncoded) {
            System.out.println("******************************");
            System.out.println("User : " + clientId + " -> Encoded Password : " + encodedPassword);
            System.out.println("******************************");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setAccountName(clientId);
        userEntity.setAccountPhrase(encodedPassword);
        userEntity.setEmailAddress(clientId);
        userEntity = userRepository.save(userEntity);
        userEntity.setUserAuthorization(Sets.newHashSet());

        if (!userAuthorizationRepository.existsByAuthority("ROLE_ADMIN")) {
            UserAuthorization userAuth = new UserAuthorization();
            userAuth.setAuthority("ROLE_ADMIN");
            userAuth.setUserEntity(userEntity);
            userEntity.getUserAuthorization().add(userAuthorizationRepository.save(userAuth));
        } else {
            userEntity.getUserAuthorization().add(userAuthorizationRepository.findByAuthority("ROLE_ADMIN").get());
        }

        if (!userAuthorizationRepository.existsByAuthority("ROLE_USER")) {
            UserAuthorization userAuth2 = new UserAuthorization();
            userAuth2.setAuthority("ROLE_USER");
            userAuth2.setUserEntity(userEntity);
            userEntity.getUserAuthorization().add(userAuthorizationRepository.save(userAuth2));
        } else {
            userEntity.getUserAuthorization().add(userAuthorizationRepository.findByAuthority("ROLE_USER").get());
        }

        userRepository.save(userEntity);

        try {
            // For root 1 hour to accessToken, 1 year to refreshTokenValidity
            oAuthClientDetailsRepository
                    .insertAccessToken(clientId, isEncoded ? encodedPassword : password, "ROLE_ADMIN,ROLE_USER",
                            60 * 60,
                            60 * 60 * 24 * 365);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
