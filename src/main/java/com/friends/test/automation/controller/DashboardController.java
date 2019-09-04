package com.friends.test.automation.controller;

import com.friends.test.automation.service.TestCaseService;
import com.friends.test.automation.service.TestSuiteService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tanistan/dashboard", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DashboardController {

    private final TestCaseService testCaseService;
    private final TestSuiteService testSuiteService;

    public DashboardController(TestCaseService testCaseService,
            TestSuiteService testSuiteService) {
        this.testCaseService = testCaseService;
        this.testSuiteService = testSuiteService;
    }

    @GetMapping("/project/{projectId}/testcasecount")
    public ResponseEntity<Integer> getTotalTestCase(@PathVariable String projectId) {
        return ResponseEntity.ok(testCaseService.getTotalTestCases(projectId));
    }


    @GetMapping("/project/{projectId}/testsuitecount")
    public ResponseEntity<Integer> getTotalTestSuites(@PathVariable String projectId) {
        return ResponseEntity.ok(testSuiteService.getTotalTestSuites(projectId));
    }

}
