package com.friends.tanistan.tobedeleted;

import com.friends.tanistan.entity.UserAuthorization;
import com.friends.tanistan.entity.UserEntity;
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
    DataSource dataSource;

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

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

            jdbcTemplate.update("INSERT INTO OAUTH_CLIENT_DETAILS  "
                    + "(client_id, client_secret, scope, authorized_grant_types,  "
                    + "authorities, access_token_validity, refresh_token_validity) VALUES "
                    + "('grkn','" + encodedPassword
                    + "', 'read,write', 'password,refresh_token,client_credentials,authorization_code', 'ROLE_ADMIN,ROLE_USER', 900, 2592000)");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
