package com.friends.test.automation.controller;

import com.friends.test.automation.controller.dto.TestSuiteDto;
import com.friends.test.automation.controller.resource.TestCaseResource;
import com.friends.test.automation.controller.resource.TestSuiteResource;
import com.friends.test.automation.entity.TestSuite;
import com.friends.test.automation.service.TestSuiteService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/tanistan/testsuite", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TestSuiteController {

    private final ConversionService conversionService;
    private final TestSuiteService testSuiteService;

    public TestSuiteController(ConversionService conversionService,
            TestSuiteService testSuiteService) {
        this.conversionService = conversionService;
        this.testSuiteService = testSuiteService;
    }

    @PostMapping("/project/{projectId}")
    public ResponseEntity<TestSuiteResource> createTestSuite(@PathVariable String projectId,
            @RequestBody TestSuiteDto testSuiteDto) {
        TestSuite testSuite = conversionService.convert(testSuiteDto, TestSuite.class);
        TestSuiteResource resource = conversionService
                .convert(testSuiteService.createTestSuite(testSuite, projectId), TestSuiteResource.class);
        return ResponseEntity.ok(resource);
    }

    @PostMapping("/project/{projectId}/{id}")
    public ResponseEntity<TestSuiteResource> updateTestSuite(@PathVariable String projectId, @PathVariable String id,
            @RequestBody TestSuiteDto testSuiteDto) {
        TestSuite testSuite = conversionService.convert(testSuiteDto, TestSuite.class);
        testSuite.setId(id);
        TestSuiteResource resource = conversionService
                .convert(testSuiteService.createTestSuite(testSuite, projectId), TestSuiteResource.class);
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/project/{projectId}/children/{parentId}")
    public ResponseEntity<TestSuiteResource> updateTestSuite(@PathVariable String projectId,
            @PathVariable String parentId) {
        TestSuiteResource resource = conversionService
                .convert(testSuiteService.getChildren(parentId, projectId), TestSuiteResource.class);
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestSuiteResource> getTestSuiteById(@PathVariable String id) {
        TestSuiteResource resource = conversionService
                .convert(testSuiteService.getTestSuiteById(id), TestSuiteResource.class);
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/project/{projectId}/all")
    public ResponseEntity<Page<TestSuiteResource>> root(@PathVariable String projectId,
            @PageableDefault(sort = "createdBy")
                    Pageable pageable) {

        Page<TestSuite> testSuitePageable = testSuiteService.findAllBySuiteByProjectId(projectId, pageable);

        List<TestSuiteResource> testSuiteResources = testSuitePageable.get()
                .map(testSuite -> conversionService.convert(testSuite, TestSuiteResource.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new PageImpl<>(testSuiteResources, pageable, testSuitePageable.getTotalElements()));
    }

    @PostMapping("/project/{projectId}/{suiteId}/addtest")
    public ResponseEntity<TestSuiteResource> addTestCaseToTestSuite(@PathVariable String projectId,
            @PathVariable String suiteId,
            @RequestBody List<String> testIds) {
        TestSuiteResource resource = conversionService
                .convert(testSuiteService.addTestCaseToTestSuite(suiteId, testIds, projectId), TestSuiteResource.class);
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/project/{projectId}/{suiteId}/testcases")
    public ResponseEntity<List<TestCaseResource>> getTestCaseBySuiteId(@PathVariable String projectId,
            @PathVariable String suiteId) {
        List<TestCaseResource> testCases = testSuiteService.getTestCasesBySuiteIdAndUserId(suiteId, projectId)
                .stream()
                .map(testCase -> conversionService.convert(testCase, TestCaseResource.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(testCases);
    }

    @DeleteMapping("/project/{projectId}/{suiteId}/testcase/{testCaseId}")
    public ResponseEntity<Void> deleteTestCaseFromSuite(@PathVariable String projectId, @PathVariable String suiteId,
            @PathVariable String testCaseId) {
        testSuiteService.deleteTestCase(suiteId, testCaseId, projectId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/project/{projectId}/suite/{suiteId}/testcase/run/driver/{driverId}")
    public ResponseEntity<Void> runTestCases(@PathVariable String projectId, @PathVariable String suiteId
            , @PathVariable String driverId) {
        testSuiteService.runTestCase(suiteId, projectId, driverId);
        return ResponseEntity.noContent().build();
    }
}
