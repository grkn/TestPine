package com.friends.tanistan.tobedeleted;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.friends.tanistan.entity.UserAuthorization;
import com.friends.tanistan.entity.UserEntity;
import com.friends.tanistan.repository.UserAuthorizationRepository;
import com.friends.tanistan.repository.UserRepository;
import com.google.common.collect.Sets;

/**
 * TO BE DELETED
 * 
 * @author grkn
 *
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

		// AUTHS
		UserAuthorization userAuth = new UserAuthorization();
		userAuth.setAuthority("ROLE_ADMIN");
		UserAuthorization firstAuth = userAuthorizationRepository.save(userAuth);
		userAuth = new UserAuthorization();
		userAuth.setAuthority("ROLE_USER");
		UserAuthorization secondAuth = userAuthorizationRepository.save(userAuth);

		UserEntity userEntity = new UserEntity();
		userEntity.setAccountName("grkn");
		userEntity.setAccountPhrase(passwordEncoder.encode("grkn"));
		userEntity.setEmailAddress("gurkanilleez@gmail.com");
		userEntity.setUserAuthorization(Sets.newHashSet(firstAuth, secondAuth));
		userRepository.save(userEntity);
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			jdbcTemplate.update("INSERT INTO OAUTH_CLIENT_DETAILS  "
					+ "(client_id, client_secret, scope, authorized_grant_types,  "
					+ "authorities, access_token_validity, refresh_token_validity) VALUES "
					+ "('gurkanilleez@gmail.com','" + passwordEncoder.encode("grkn")
					+ "', 'read,write', 'password,refresh_token,client_credentials,authorization_code', 'ROLE_ADMIN,ROLE_USER', 900, 2592000)");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
