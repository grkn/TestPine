package com.friends.test.automation.repository;

import com.friends.test.automation.entity.Driver;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DriverRepository extends BaseTanistanJpaRepository<Driver> {

    List<Driver> findAllByUserEntityId(String userId);

}
