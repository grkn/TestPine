package com.friends.test.automation.controller;

import com.friends.test.automation.controller.dto.DriverDto;
import com.friends.test.automation.controller.resource.DriverResource;
import com.friends.test.automation.entity.Driver;
import com.friends.test.automation.service.UserDriverService;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/tanistan/driver", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DriverController {

    private final ConversionService conversionService;
    private final UserDriverService userDriverService;

    public DriverController(ConversionService conversionService,
            UserDriverService userDriverService) {
        this.conversionService = conversionService;
        this.userDriverService = userDriverService;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<DriverResource> createDriver(@RequestBody DriverDto driverDto, @PathVariable String userId) {
        Driver driver = conversionService.convert(driverDto, Driver.class);
        return ResponseEntity
                .ok(conversionService.convert(userDriverService.createOrUpdate(driver, userId), DriverResource.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverResource> createDriver(@PathVariable String id) {
        return ResponseEntity.ok(conversionService.convert(userDriverService.findById(id), DriverResource.class));
    }

    @PutMapping("/{id}/user/{userId}")
    public ResponseEntity<DriverResource> updateDriver(@RequestBody DriverDto driverDto, @PathVariable String id,
            @PathVariable String userId) {
        driverDto.setId(id);
        Driver driver = conversionService.convert(driverDto, Driver.class);
        return ResponseEntity
                .ok(conversionService.convert(userDriverService.createOrUpdate(driver, userId), DriverResource.class));
    }

    @GetMapping("/user/{userId}/all")
    public ResponseEntity<List<DriverResource>> getAll(@PathVariable String userId) {
        List<Driver> drivers = userDriverService.findAll(userId);
        List<DriverResource> driverResources = drivers.stream()
                .map(item -> conversionService.convert(item, DriverResource.class)).collect(Collectors.toList());
        return ResponseEntity.ok(driverResources);
    }
}
