package com.friends.test.automation.repository;

import com.friends.test.automation.entity.TestSuiteInstanceRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TestSuiteInstanceRunnerRepository extends BaseTanistanJpaRepository<TestSuiteInstanceRunner> {

    Page<TestSuiteInstanceRunner> findAllByTestSuiteTestProjectId(String projectId, Pageable pageable);

}
