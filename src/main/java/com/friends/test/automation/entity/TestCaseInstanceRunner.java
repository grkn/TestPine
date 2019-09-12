package com.friends.test.automation.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class TestCaseInstanceRunner extends TanistanBaseEntity<String> {

    @OneToMany(mappedBy = "testCaseInstanceRunner", fetch = FetchType.EAGER)
    private List<TestStep> testSteps;

    @ManyToOne
    private TestCase testCase;

    private boolean running;

    @ManyToOne
    private UserEntity userEntity;

    private String testSuiteInstanceRunnerId;

    private String testSuiteInstanceRunnerName;

    public TestCaseInstanceRunner() {

    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public List<TestStep> getTestSteps() {
        return testSteps;
    }

    public void setTestSteps(List<TestStep> testSteps) {
        this.testSteps = testSteps;
    }

    public TestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
    }

    public String getTestSuiteInstanceRunnerId() {
        return testSuiteInstanceRunnerId;
    }

    public void setTestSuiteInstanceRunnerId(String testSuiteInstanceRunnerId) {
        this.testSuiteInstanceRunnerId = testSuiteInstanceRunnerId;
    }

    public String getTestSuiteInstanceRunnerName() {
        return testSuiteInstanceRunnerName;
    }

    public void setTestSuiteInstanceRunnerName(String testSuiteInstanceRunnerName) {
        this.testSuiteInstanceRunnerName = testSuiteInstanceRunnerName;
    }
}
