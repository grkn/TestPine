package com.friends.test.automation.controller.converter;

import com.friends.test.automation.controller.resource.TestCaseResource;
import com.friends.test.automation.controller.resource.TestSuiteResource;
import com.friends.test.automation.entity.TestCase;
import com.friends.test.automation.entity.TestSuite;
import org.springframework.core.convert.converter.Converter;

import java.util.stream.Collectors;

public class TestSuiteToTestSuiteResource implements Converter<TestSuite, TestSuiteResource> {

    private final Converter<TestCase, TestCaseResource> converter;

    public TestSuiteToTestSuiteResource(
            Converter<TestCase, TestCaseResource> converter) {
        this.converter = converter;
    }


    @Override
    public TestSuiteResource convert(TestSuite source) {
        TestSuiteResource testSuiteResource = new TestSuiteResource();
        testSuiteResource.setName(source.getName());
        testSuiteResource.setId(source.getId());

        testSuiteResource
                .setTestCases(source.getTestCases().stream().map(testCase -> converter.convert(testCase)).collect(
                        Collectors.toList()));

        return testSuiteResource;
    }
}
