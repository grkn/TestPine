package com.friends.test.automation.controller;

import com.friends.test.automation.controller.dto.FindElementDto;
import com.friends.test.automation.controller.dto.NavigateDto;
import com.friends.test.automation.controller.dto.SendKeysDto;
import com.friends.test.automation.controller.dto.SessionDto;
import com.friends.test.automation.controller.dto.TimeoutDto;
import com.friends.test.automation.controller.resource.DefaultResource;
import com.friends.test.automation.controller.resource.DeleteSessionResource;
import com.friends.test.automation.controller.resource.SessionResource;
import com.friends.test.automation.service.DriverService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tanistan/driver/session", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SessionController {

    private final DriverService driverService;

    public SessionController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    public ResponseEntity<SessionResource> getSession(@RequestBody SessionDto sessionDto,
            @RequestParam(required = false) String driverUrl) {
        return driverService.getSession(sessionDto, driverUrl);
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<DeleteSessionResource> deleteSession(@PathVariable String sessionId,
            @RequestParam(required = false) String driverUrl) {
        return driverService.deleteSession(sessionId, driverUrl);
    }

    @PostMapping("/{sessionId}/timeouts")
    public ResponseEntity<DefaultResource> setTimeout(@PathVariable String sessionId,
            @RequestBody TimeoutDto timeoutDto, @RequestParam(required = false) String driverUrl) {
        return driverService.setTimeout(sessionId, timeoutDto, driverUrl);
    }

    @PostMapping("/{sessionId}/url")
    public ResponseEntity<DefaultResource> navigate(@PathVariable String sessionId,
            @RequestBody NavigateDto navigateDto, @RequestParam(required = false) String driverUrl) {
        return driverService.navigate(sessionId, navigateDto, driverUrl);
    }

    @GetMapping("/{sessionId}/url")
    public ResponseEntity<DefaultResource> currentUrl(@PathVariable String sessionId,
            @RequestParam(required = false) String driverUrl) {
        return driverService.getCurrentUrl(sessionId, driverUrl);
    }

    @GetMapping("/{sessionId}/title")
    public ResponseEntity<DefaultResource> getPageTitle(@PathVariable String sessionId,
            @RequestParam(required = false) String driverUrl) {
        return driverService.getPageTitle(sessionId, driverUrl);
    }

    @PostMapping("/{sessionId}/window/current/maximize")
    public ResponseEntity<DefaultResource> maximize(@PathVariable String sessionId,
            @RequestParam(required = false) String driverUrl) {
        return driverService.maximize(sessionId, driverUrl);
    }

    @PostMapping("/{sessionId}/element")
    public ResponseEntity<DefaultResource> findElement(@PathVariable String sessionId,
            @RequestBody FindElementDto findElementDto, @RequestParam(required = false) String driverUrl) {
        return driverService.findElement(sessionId, findElementDto, driverUrl);
    }

    @GetMapping("/{sessionId}/element/{elementId}/selected")
    public ResponseEntity<DefaultResource> selected(@PathVariable("sessionId") String sessionId,
            @PathVariable("elementId") String elementId, @RequestParam(required = false) String driverUrl) {
        return driverService.isSelectedElement(sessionId, elementId, driverUrl);
    }

    @GetMapping("/{sessionId}/element/{elementId}/attribute/{name}")
    public ResponseEntity<DefaultResource> getAttributeByName(@PathVariable("sessionId") String sessionId,
            @PathVariable("elementId") String elementId, @PathVariable("name") String name,
            @RequestParam(required = false) String driverUrl) {
        return driverService.getAttributeByName(sessionId, elementId, name, driverUrl);
    }

    @GetMapping("/{sessionId}/element/{elementId}/text")
    public ResponseEntity<DefaultResource> getElementText(@PathVariable("sessionId") String sessionId,
            @PathVariable("elementId") String elementId, @RequestParam(required = false) String driverUrl) {
        return driverService.getElementText(sessionId, elementId, driverUrl);
    }

    @PostMapping("/{sessionId}/element/{elementId}/click")
    public ResponseEntity<DefaultResource> clickElement(@PathVariable("sessionId") String sessionId,
            @PathVariable("elementId") String elementId, @RequestParam(required = false) String driverUrl) {
        return driverService.clickElement(sessionId, elementId, driverUrl);
    }

    @PostMapping("/{sessionId}/element/{elementId}/value")
    public ResponseEntity<DefaultResource> sendKeys(@PathVariable("sessionId") String sessionId,
            @PathVariable("elementId") String elementId, @RequestBody SendKeysDto sendKeysDto,
            @RequestParam(required = false) String driverUrl) {
        return driverService.sendKeys(sessionId, elementId, sendKeysDto, driverUrl);
    }
}
