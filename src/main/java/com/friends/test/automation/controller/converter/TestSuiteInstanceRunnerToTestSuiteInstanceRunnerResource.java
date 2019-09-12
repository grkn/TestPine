package com.friends.test.automation.controller.converter;

import com.friends.test.automation.controller.resource.TestSuiteInstanceRunnerResource;
import com.friends.test.automation.entity.TestSuiteInstanceRunner;
import org.springframework.core.convert.converter.Converter;

public class TestSuiteInstanceRunnerToTestSuiteInstanceRunnerResource implements
        Converter<TestSuiteInstanceRunner, TestSuiteInstanceRunnerResource> {

    private final TestSuiteToTestSuiteResource testSuiteToTestSuiteResource;

    public TestSuiteInstanceRunnerToTestSuiteInstanceRunnerResource(
            TestSuiteToTestSuiteResource testSuiteToTestSuiteResource) {
        this.testSuiteToTestSuiteResource = testSuiteToTestSuiteResource;
    }

    @Override
    public TestSuiteInstanceRunnerResource convert(TestSuiteInstanceRunner source) {
        TestSuiteInstanceRunnerResource testSuiteInstanceRunnerResource = new TestSuiteInstanceRunnerResource();
        testSuiteInstanceRunnerResource.setId(source.getId());
        testSuiteInstanceRunnerResource.setEndDate(source.getEndDate());
        testSuiteInstanceRunnerResource.setStartDate(source.getStartDate());
        testSuiteInstanceRunnerResource.setTestSuite(testSuiteToTestSuiteResource.convert(source.getTestSuite()));
        return testSuiteInstanceRunnerResource;
    }
}
