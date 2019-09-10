package com.friends.test.automation.service;

import com.friends.test.automation.controller.resource.ErrorResource;
import com.friends.test.automation.entity.Driver;
import com.friends.test.automation.entity.UserEntity;
import com.friends.test.automation.exception.NotFoundException;
import com.friends.test.automation.repository.DriverRepository;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserDriverService {

    private final DriverRepository driverRepository;
    private final UserService<UserEntity> userService;

    public UserDriverService(DriverRepository driverRepository,
            UserService<UserEntity> userService) {
        this.driverRepository = driverRepository;
        this.userService = userService;
    }

    public Driver findById(String id) {
        return driverRepository.findById(id).orElseThrow(() -> new NotFoundException(
                ErrorResource.ErrorContent.builder().message("Driver can not be found").build("")));
    }

    public List<Driver> findAll(String userId) {
        return driverRepository.findAllByUserEntityId(userId);
    }

    @Transactional
    public Driver createOrUpdate(Driver driver, String userId) {
        UserEntity userEntity = userService.getUserById(userId);
        if (StringUtils.isEmpty(driver.getId() != null)) {
            Driver persistedDriver = findById(driver.getId());
            if (driver.getAddress() != null) {
                persistedDriver.setAddress(driver.getAddress());
                persistedDriver.setPort(driver.getPort());
            }
            persistedDriver.setUserEntity(userEntity);
            setDriverToUserEntity(userEntity, persistedDriver);

            return driverRepository.save(persistedDriver);
        }

        driver.setUserEntity(userEntity);
        setDriverToUserEntity(userEntity, driver);
        return driverRepository.save(driver);
    }

    private void setDriverToUserEntity(UserEntity userEntity, Driver persistedDriver) {
        if (userEntity.getDriver() == null) {
            userEntity.setDriver(Sets.newHashSet(persistedDriver));
        } else {
            userEntity.getDriver().add(persistedDriver);
        }
    }
}
