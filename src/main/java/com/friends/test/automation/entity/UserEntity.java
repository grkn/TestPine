package com.friends.test.automation.entity;

import com.friends.test.automation.enums.AttemptType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tanistan_user", uniqueConstraints = {@UniqueConstraint(columnNames = {"accountName", "emailAddress"})})
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
    private String accountName;

    // basic security
    private String secretAnswer;
    @Column(length = 4096)
    private String accountPhrase;


    private Integer loginAttempt = 0;

    @Enumerated(value = EnumType.STRING)
    private AttemptType attemptType;

    @ManyToMany(mappedBy = "userEntity", fetch = FetchType.EAGER)
    @Column(name = "user_entity_id")
    private Set<UserAuthorization> userAuthorization;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY)
    private Set<TestCase> testCases;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY)
    private Set<TestCaseInstanceRunner> testCaseInstanceRunners;

    @OneToOne
    private TestSuite testSuite;

    @ManyToMany
    @JoinTable(name = "test_project_user_rel",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<TestProject> projects;

    @ManyToOne(fetch = FetchType.EAGER)
    private Company company;

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

    public Integer getLoginAttempt() {
        return loginAttempt;
    }

    public void setLoginAttempt(Integer loginAttempt) {
        this.loginAttempt = loginAttempt;
    }

    public AttemptType getAttemptType() {
        return attemptType;
    }

    public void setAttemptType(AttemptType attemptType) {
        this.attemptType = attemptType;
    }

    public Set<UserAuthorization> getUserAuthorization() {
        return userAuthorization;
    }

    public void setUserAuthorization(Set<UserAuthorization> userAuthorization) {
        this.userAuthorization = userAuthorization;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Set<TestCase> getTestCases() {
        return testCases;
    }

    public void setTestCases(Set<TestCase> testCases) {
        this.testCases = testCases;
    }

    public Set<TestCaseInstanceRunner> getTestCaseInstanceRunners() {
        return testCaseInstanceRunners;
    }

    public void setTestCaseInstanceRunners(
            Set<TestCaseInstanceRunner> testCaseInstanceRunners) {
        this.testCaseInstanceRunners = testCaseInstanceRunners;
    }

    public TestSuite getTestSuite() {
        return testSuite;
    }

    public void setTestSuite(TestSuite testSuite) {
        this.testSuite = testSuite;
    }

    public List<TestProject> getProjects() {
        return projects;
    }

    public void setProjects(List<TestProject> projects) {
        this.projects = projects;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
