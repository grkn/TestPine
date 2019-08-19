package com.friends.tanistan.repository.impl;

import com.friends.tanistan.repository.OAuthClientDetailsRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class OAuthClientDetailsRepositoryImpl implements OAuthClientDetailsRepository {

	private final PasswordEncoder passwordEncoder;
	private final DataSource dataSource;

	public OAuthClientDetailsRepositoryImpl(PasswordEncoder passwordEncoder, DataSource dataSource) {
		this.passwordEncoder = passwordEncoder;
		this.dataSource = dataSource;
	}

	@Override
	public void insertAccessToken(String clientId, String clientSecret, String roles, int accessTokenValiditySecond,
			long refreshTokenValiditySecond) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String insertScript = "INSERT INTO OAUTH_CLIENT_DETAILS  "
				+ "(client_id, client_secret, scope, authorized_grant_types,  "
				+ "authorities, access_token_validity, refresh_token_validity) VALUES " + "(?,?, "
				+ " 'read,write', 'password,refresh_token,client_credentials,authorization_code',?,?,?)";
		jdbcTemplate.update(insertScript, clientId, passwordEncoder.encode(clientSecret), roles,
				accessTokenValiditySecond, refreshTokenValiditySecond);
	}

	@Override
	public void updateAuthorities(List<String> authorities, String clientId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String updateScript = "UPDATE OAUTH_CLIENT_DETAILS SET authorities = ? WHERE client_id = ?";
		jdbcTemplate.update(updateScript, authorities.toString().substring(1, authorities.toString().length() - 1),
				clientId);
	}
}
