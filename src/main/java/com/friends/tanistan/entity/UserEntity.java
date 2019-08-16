package com.friends.tanistan.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.friends.tanistan.enums.AttemptType;

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
public class UserEntity extends TanistanBaseEntity<String> {

	// user informations
	private String name;
	private String middleName;
	private String lastName;
	@Temporal(value = TemporalType.DATE)
	private Date birthDay;
	private String emailAddress;
	private String phoneNumber;
	private String secretQuestion;

	// basic security
	private String secretAnswer;
	@Lob
	private String accountPhrase;
	private String accountName;

	private int loginAttempt = 0;

	@Enumerated(value = EnumType.STRING)
	private AttemptType attemptType;

	public UserEntity() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSecretQuestion() {
		return secretQuestion;
	}

	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}

	public String getSecretAnswer() {
		return secretAnswer;
	}

	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}

	public String getAccountPhrase() {
		return accountPhrase;
	}

	public void setAccountPhrase(String accountPhrase) {
		this.accountPhrase = accountPhrase;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public int getLoginAttempt() {
		return loginAttempt;
	}

	public void setLoginAttempt(int loginAttempt) {
		this.loginAttempt = loginAttempt;
	}

	public AttemptType getAttemptType() {
		return attemptType;
	}

	public void setAttemptType(AttemptType attemptType) {
		this.attemptType = attemptType;
	}

}
