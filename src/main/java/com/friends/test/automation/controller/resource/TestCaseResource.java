package com.friends.test.automation.controller.resource;

import com.fasterxml.jackson.databind.JsonNode;
import com.friends.test.automation.entity.Driver;

import java.util.Date;
import java.util.List;

public class TestCaseResource {

    private String id;
    private String name;
    private Date createdDate;
    private JsonNode testCommands;
    private String userId;
    private List<String> testSuiteNames;
    private List<String> testSuiteIds;
    private DriverResource driver;
    private List<InstanceRunnerResource> instanceRunnerResources;
    private String createdBy;

    public TestCaseResource() {
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public JsonNode getTestCommands() {
        return testCommands;
    }

    public void setTestCommands(JsonNode testCommands) {
        this.testCommands = testCommands;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getTestSuiteNames() {
        return testSuiteNames;
    }

    public void setTestSuiteNames(List<String> testSuiteNames) {
        this.testSuiteNames = testSuiteNames;
    }

    public DriverResource getDriver() {
        return driver;
    }

    public void setDriver(DriverResource driver) {
        this.driver = driver;
    }

    public List<InstanceRunnerResource> getInstanceRunnerResources() {
        return instanceRunnerResources;
    }

    public void setInstanceRunnerResources(
            List<InstanceRunnerResource> instanceRunnerResources) {
        this.instanceRunnerResources = instanceRunnerResources;
    }

    public List<String> getTestSuiteIds() {
        return testSuiteIds;
    }

    public void setTestSuiteIds(List<String> testSuiteIds) {
        this.testSuiteIds = testSuiteIds;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
