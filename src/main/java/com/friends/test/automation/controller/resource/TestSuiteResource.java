package com.friends.test.automation.controller.resource;

import java.util.Date;
import java.util.List;

public class TestSuiteResource {

    private String id;
    private String name;
    private List<TestCaseResource> testCases;
    private Date createdDate;
    private String userName;

    public TestSuiteResource() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TestCaseResource> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<TestCaseResource> testCases) {
        this.testCases = testCases;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
