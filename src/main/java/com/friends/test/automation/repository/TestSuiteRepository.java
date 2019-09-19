package com.friends.test.automation.repository;

import com.friends.test.automation.entity.TestSuite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TestSuiteRepository extends BaseTanistanJpaRepository<TestSuite> {

    Optional<TestSuite> findByIdAndTestProjectId(String id, String projectId);

    Page<TestSuite> findAllByTestProjectId(String projectId, Pageable pageable);

    Integer countByTestProjectId(String projectId);

}
