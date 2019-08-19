package com.friends.tanistan.tobedeleted;

import com.friends.tanistan.entity.UserAuthorization;
import com.friends.tanistan.entity.UserEntity;
import com.friends.tanistan.repository.OAuthClientDetailsRepository;
import com.friends.tanistan.repository.UserAuthorizationRepository;
import com.friends.tanistan.repository.UserRepository;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

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


    // TO BE DELETED
    @PostConstruct
    public void setUp() {

        String encodedPassword = passwordEncoder.encode("grkn");

        UserEntity userEntity = new UserEntity();
        userEntity.setAccountName("grkn");
        userEntity.setAccountPhrase(encodedPassword);
        userEntity.setEmailAddress("gurkanilleez@gmail.com");

        // AUTHS
        UserAuthorization userAuth = new UserAuthorization();
        userAuth.setAuthority("ROLE_ADMIN");
        userAuth.setUserEntity(userEntity);


        UserAuthorization userAuth2 = new UserAuthorization();
        userAuth2.setAuthority("ROLE_USER");
        userAuth2.setUserEntity(userEntity);

        userEntity.setUserAuthorization(Sets.newHashSet(userAuth, userAuth2));

        userRepository.save(userEntity);

        userAuthorizationRepository.save(userAuth);
        userAuthorizationRepository.save(userAuth2);

        try{
            oAuthClientDetailsRepository.insertAccessToken("grkn",encodedPassword,"ROLE_ADMIN,ROLE_USER");
        }catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
