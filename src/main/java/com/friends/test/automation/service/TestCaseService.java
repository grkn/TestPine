package com.friends.test.automation.service;

import com.friends.test.automation.controller.resource.ErrorResource;
import com.friends.test.automation.entity.TestCase;
import com.friends.test.automation.entity.TestCaseInstanceRunner;
import com.friends.test.automation.entity.TestProject;
import com.friends.test.automation.entity.UserEntity;
import com.friends.test.automation.exception.AlreadyExistsException;
import com.friends.test.automation.exception.NotFoundException;
import com.friends.test.automation.repository.DriverRepository;
import com.friends.test.automation.repository.TestCaseInstanceRunnerRepository;
import com.friends.test.automation.repository.TestCaseRepository;
import com.friends.test.automation.repository.TestProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TestCaseService {

    private final TestCaseRepository testCaseRepository;
    private final UserService<UserEntity> userService;
    private final TestCaseInstanceRunnerRepository testCaseInstanceRunnerRepository;
    private final TestProjectRepository testProjectRepository;
    private final DriverRepository driverRepository;

    public TestCaseService(TestCaseRepository testCaseRepository,
            UserService<UserEntity> userService,
            TestCaseInstanceRunnerRepository testCaseInstanceRunnerRepository,
            TestProjectRepository testProjectRepository,
            DriverRepository driverRepository) {
        this.testCaseRepository = testCaseRepository;
        this.userService = userService;
        this.testCaseInstanceRunnerRepository = testCaseInstanceRunnerRepository;
        this.testProjectRepository = testProjectRepository;
        this.driverRepository = driverRepository;
    }

    public TestCase save(String projectId, String userId, TestCase testCase) {
        testCase.setUserEntity(userService.getUserById(userId));
        testCase.setTestProject(getProjectById(projectId));

        if (testCase.getDriver() != null) {
            testCase.setDriver(driverRepository.findById(testCase.getDriver().getId()).get());
            testCase.getDriver().setTestCase(testCase);
        }
        if (testCase.getId() == null) {
            checkTestCaseExistWithSameName(testCase.getName());
        }

        return this.testCaseRepository.save(testCase);
    }

    private TestProject getProjectById(String projectId) {
        return testProjectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorResource.ErrorContent.builder().message("Please select a project").build("")));
    }

    private void checkTestCaseExistWithSameName(String name) {
        testCaseRepository.existsByName(name).ifPresent((exists) -> {
            if (exists) {
                throw new AlreadyExistsException(
                        ErrorResource.ErrorContent.builder()
                                .message(String.format("Test Case exist with name : %s", name)).build(""));
            }
        });
    }

    public Page<TestCase> findAllByProjectId(String projectId, Pageable pageable) {
        return this.testCaseRepository.findAllByTestProjectId(projectId, pageable);
    }

    public Page<TestCaseInstanceRunner> findAllInstanceRunnersByTestCaseId(String projectId, String testCaseId,
            Pageable pageable) {
        return testCaseInstanceRunnerRepository
                .findAllByTestCaseIdAndTestCaseTestProjectId(testCaseId, projectId, pageable);
    }

    public Page<TestCaseInstanceRunner> findAllInstanceRunnersForProject(String suiteName, String projectId,
            Pageable pageable) {
        if (StringUtils.isEmpty(suiteName)) {
            return testCaseInstanceRunnerRepository.findAllByTestCaseTestProjectId(projectId, pageable);
        } else {
            return testCaseInstanceRunnerRepository
                    .findAllByTestCaseTestProjectIdAndTestCaseTestSuiteNameIgnoreCaseContains(projectId, suiteName, pageable);
        }
    }

    public TestCaseInstanceRunner findById(String id) {
        return testCaseInstanceRunnerRepository.findById(id).orElseThrow(() -> new NotFoundException(
                ErrorResource.ErrorContent.builder().message("Test result can not be found").build("")));
    }

    public TestCase findById(String projectId, String id) {
        return testCaseRepository.findByIdAndTestProjectId(id, projectId).orElseThrow(() -> new NotFoundException(
                ErrorResource.ErrorContent.builder().message("Test case can not be found right now.").build("")));
    }

    public Integer getTotalTestCases(String projectId) {
        return testCaseRepository.countByTestProjectId(projectId).intValue();
    }
}
