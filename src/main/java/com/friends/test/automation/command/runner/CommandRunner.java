package com.friends.test.automation.command.runner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.friends.test.automation.controller.dto.FindElementDto;
import com.friends.test.automation.controller.dto.NavigateDto;
import com.friends.test.automation.controller.dto.SendKeysDto;
import com.friends.test.automation.controller.dto.SessionDto;
import com.friends.test.automation.controller.resource.DefaultResource;
import com.friends.test.automation.controller.resource.RunnableResource;
import com.friends.test.automation.controller.resource.SessionResource;
import com.friends.test.automation.entity.Driver;
import com.friends.test.automation.entity.TestCase;
import com.friends.test.automation.entity.TestCaseInstanceRunner;
import com.friends.test.automation.entity.TestStep;
import com.friends.test.automation.entity.TestSuiteInstanceRunner;
import com.friends.test.automation.repository.TestCaseInstanceRunnerRepository;
import com.friends.test.automation.repository.TestStepRepository;
import com.friends.test.automation.repository.TestSuiteInstanceRunnerRepository;
import com.friends.test.automation.service.DriverService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CommandRunner implements Runnable {

    private Logger logger = LoggerFactory.getLogger(CommandRunner.class);
    private final ObjectMapper objectMapper;
    private final DriverService driverService;
    private final TestCase testCase;
    private final TestCaseInstanceRunnerRepository testCaseInstanceRunnerRepository;
    private final TestSuiteInstanceRunnerRepository testSuiteInstanceRunnerRepository;
    private final TestSuiteInstanceRunner testSuiteInstanceRunner;
    private final TestStepRepository testStepRepository;
    private final Driver driver;

    public CommandRunner(ObjectMapper objectMapper, DriverService driverService,
            TestCase testCase, TestCaseInstanceRunnerRepository testCaseInstanceRunnerRepository,
            TestStepRepository testStepRepository, Driver driver,
            TestSuiteInstanceRunnerRepository testSuiteInstanceRunnerRepository,
            TestSuiteInstanceRunner testSuiteInstanceRunner) {
        this.objectMapper = objectMapper;
        this.driverService = driverService;
        this.testCase = testCase;
        this.testCaseInstanceRunnerRepository = testCaseInstanceRunnerRepository;
        this.testStepRepository = testStepRepository;
        this.driver = driver;
        this.testSuiteInstanceRunnerRepository = testSuiteInstanceRunnerRepository;
        this.testSuiteInstanceRunner = testSuiteInstanceRunner;
    }

    private String createDriverUrl() {
        return driver != null ? driver.getAddress() : null;
    }

    @Override
    public void run() {
        TestCaseInstanceRunner testCaseInstanceRunner = new TestCaseInstanceRunner();
        testCaseInstanceRunner.setTestCase(testCase);
        testCaseInstanceRunner.setRunning(true);
        testCaseInstanceRunner.setUserEntity(testCase.getUserEntity());
        testCaseInstanceRunner.setTestSuiteInstanceRunnerId(testSuiteInstanceRunner.getId());
        testCaseInstanceRunner.setTestSuiteInstanceRunnerName(testSuiteInstanceRunner.getTestSuite().getName());
        testCaseInstanceRunner = this.testCaseInstanceRunnerRepository.saveAndFlush(testCaseInstanceRunner);

        SessionDto sessionDto = sessionDtoPrepare();

        ResponseEntity<SessionResource> sessionResource = driverService
                .getSession(sessionDto, createDriverUrl());
        if (sessionResource.getBody() != null) {
            String sessionId = sessionResource.getBody().getSessionId();

            try {
                List<RunnableResource> commands = objectMapper
                        .readValue(testCase.getTestCommands(), new TypeReference<List<RunnableResource>>() {
                        });
                DefaultResource defaultResource;
                for (RunnableResource resource : commands) {
                    TestStep testStep = new TestStep();
                    testStep.setRunStatus(true);
                    testStep.setTestCaseInstanceRunner(testCaseInstanceRunner);
                    testStep = testStepRepository.saveAndFlush(testStep);
                    testCaseInstanceRunner = InstanceRunnerInsert(testCaseInstanceRunner, testStep);

                    long s = System.currentTimeMillis();
                    switch (resource.getType()) {
                        case "goToUrl":
                            defaultResource = navigate(sessionId, resource);
                            break;
                        case "click":
                            defaultResource = click(sessionId, resource);
                            break;
                        case "sendKey":
                            defaultResource = sendKeys(sessionId, resource);
                            break;
                        default:
                            return;
                    }
                    if (defaultResource == null || defaultResource.getStatus() != 0) {
                        testStep.setStatus(defaultResource.getStatus());
                        testStep.setResult(objectMapper.writeValueAsString(defaultResource.getValue()));
                        testStep.setExecutionTime(new Date(System.currentTimeMillis() - s));
                        testStep.setRunStatus(false);
                        testStep = testStepRepository.saveAndFlush(testStep);
                        testCaseInstanceRunner = InstanceRunnerInsert(testCaseInstanceRunner, testStep);
                        break;
                    }

                    testStep.setStatus(0);
                    testStep.setResult("{\"value\" : \"SUCCESS\"}");
                    testStep.setExecutionTime(new Date(System.currentTimeMillis() - s));
                    testStep.setRunStatus(false);
                    testStep = testStepRepository.saveAndFlush(testStep);
                    testCaseInstanceRunner = InstanceRunnerInsert(testCaseInstanceRunner, testStep);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (RuntimeException ex) {
                logger.error("Unexpected Error @CommandRunner", ex);
            } finally {
                driverService.deleteSession(sessionId, createDriverUrl());
                testCaseInstanceRunner.setRunning(false);
                testCaseInstanceRunnerRepository.saveAndFlush(testCaseInstanceRunner);

                testSuiteInstanceRunner.setEndDate(new Date());
                testSuiteInstanceRunnerRepository.save(testSuiteInstanceRunner);

            }
        }

    }

    private SessionDto sessionDtoPrepare() {
        SessionDto sessionDto = new SessionDto();
        SessionDto.DesiredCapabilities desiredCapabilities = new SessionDto.DesiredCapabilities();
        desiredCapabilities.setBrowserName("chrome");

        sessionDto.setDesiredCapabilities(desiredCapabilities);
        return sessionDto;
    }

    private TestCaseInstanceRunner InstanceRunnerInsert(TestCaseInstanceRunner testCaseInstanceRunner,
            TestStep testStep) {
        if (testCaseInstanceRunner.getTestSteps() != null) {
            testCaseInstanceRunner.getTestSteps().add(testStep);
        } else {
            testCaseInstanceRunner.setTestSteps(Lists.newArrayList(testStep));
        }
        testCaseInstanceRunner = testCaseInstanceRunnerRepository.saveAndFlush(testCaseInstanceRunner);
        return testCaseInstanceRunner;
    }

    private DefaultResource navigate(String sessionId, RunnableResource resource) {
        DefaultResource defaultResource;
        NavigateDto navigateDto = new NavigateDto();
        navigateDto.setUrl(resource.getNavigateUrl());
        defaultResource = driverService.navigate(sessionId, navigateDto, createDriverUrl()).getBody();
        return defaultResource;
    }

    private DefaultResource click(String sessionId, RunnableResource resource) {
        DefaultResource defaultResource = findElement(sessionId, resource);
        if (defaultResource != null && defaultResource.getStatus() == 0) {
            defaultResource = driverService
                    .clickElement(sessionId, defaultResource.getValue().get("ELEMENT").textValue(),
                            createDriverUrl()).
                            getBody();
        }
        return defaultResource;
    }

    private DefaultResource findElement(String sessionId, RunnableResource resource) {
        DefaultResource defaultResource;
        FindElementDto findElementDto = new FindElementDto();
        findElementDto.setUsing(resource.getSelectionType());
        findElementDto.setValue(resource.getSelectionValue());
        defaultResource = driverService.findElement(sessionId, findElementDto, createDriverUrl()).getBody();
        return defaultResource;
    }

    private DefaultResource sendKeys(String sessionId, RunnableResource resource) {
        DefaultResource defaultResource = findElement(sessionId, resource);
        if (defaultResource != null && defaultResource.getStatus() == 0) {
            SendKeysDto sendKeysDto = new SendKeysDto();
            sendKeysDto.setValue(Lists.newArrayList(resource.getMessage()));
            defaultResource = driverService
                    .sendKeys(sessionId, defaultResource.getValue().get("ELEMENT").textValue(),
                            sendKeysDto, createDriverUrl())
                    .getBody();
        }
        return defaultResource;
    }
}
