package com.friends.test.automation.controller.resource;

import java.util.Date;

public class TestSuiteInstanceRunnerResource {


    private String id;
    private Date startDate;
    private Date endDate;
    private TestSuiteResource testSuite;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public TestSuiteResource getTestSuite() {
        return testSuite;
    }

    public void setTestSuite(TestSuiteResource testSuite) {
        this.testSuite = testSuite;
    }
}
