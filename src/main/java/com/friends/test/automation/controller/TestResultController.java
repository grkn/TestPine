package com.friends.test.automation.controller;

import com.friends.test.automation.controller.resource.InstanceRunnerResource;
import com.friends.test.automation.controller.resource.TestSuiteInstanceRunnerResource;
import com.friends.test.automation.entity.TestCaseInstanceRunner;
import com.friends.test.automation.entity.TestSuiteInstanceRunner;
import com.friends.test.automation.service.TestCaseService;
import com.friends.test.automation.service.TestSuiteService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/tanistan/result/project/{projectId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TestResultController {

    private final TestCaseService testCaseService;
    private final ConversionService conversionService;
    private final TestSuiteService testSuiteService;

    public TestResultController(TestCaseService testCaseService,
            ConversionService conversionService, TestSuiteService testSuiteService) {
        this.testCaseService = testCaseService;
        this.conversionService = conversionService;
        this.testSuiteService = testSuiteService;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<InstanceRunnerResource>> findAllTestCaseRunnersUnderProject(
            @PathVariable String projectId,
            @RequestParam(required = false) String suiteName,
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<TestCaseInstanceRunner> testModelPage = this.testCaseService
                .findAllInstanceRunnersForProject(suiteName, projectId, pageable);
        List<InstanceRunnerResource> instanceRunnerResources = testModelPage.get()
                .map(testCaseInstanceRunner -> conversionService
                        .convert(testCaseInstanceRunner, InstanceRunnerResource.class)).collect(
                        Collectors.toList());
        return ResponseEntity.ok(new PageImpl(instanceRunnerResources, pageable, testModelPage.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstanceRunnerResource> findById(@PathVariable String id) {
        TestCaseInstanceRunner testCaseInstanceRunner = this.testCaseService.findById(id);
        return ResponseEntity.ok(conversionService.convert(testCaseInstanceRunner, InstanceRunnerResource.class));
    }

    @GetMapping("/suites")
    public ResponseEntity<Page<TestSuiteInstanceRunnerResource>> findAllTestSuitesRunnersUnderProject(
            @PathVariable String projectId,
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<TestSuiteInstanceRunner> testModelPage = this.testSuiteService
                .findTestSuiteInstanceRunners(projectId, pageable);
        List<TestSuiteInstanceRunnerResource> instanceRunnerResources = testModelPage.get()
                .map(testSuiteInstanceRunner -> conversionService
                        .convert(testSuiteInstanceRunner, TestSuiteInstanceRunnerResource.class)).collect(
                        Collectors.toList());
        return ResponseEntity.ok(new PageImpl(instanceRunnerResources, pageable, testModelPage.getTotalElements()));
    }
}
