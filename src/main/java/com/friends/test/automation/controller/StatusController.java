package com.friends.test.automation.controller;

import com.friends.test.automation.controller.resource.StatusResource;
import com.friends.test.automation.service.DriverService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tanistan/driver/status", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class StatusController {

    private final DriverService driverService;

    public StatusController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public ResponseEntity<StatusResource> getStatus() {
        return driverService.getStatus();
    }
}
