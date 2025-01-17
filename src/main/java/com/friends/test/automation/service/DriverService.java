package com.friends.test.automation.service;

import com.friends.test.automation.controller.dto.FindElementDto;
import com.friends.test.automation.controller.dto.NavigateDto;
import com.friends.test.automation.controller.dto.SendKeysDto;
import com.friends.test.automation.controller.dto.SessionDto;
import com.friends.test.automation.controller.dto.TimeoutDto;
import com.friends.test.automation.controller.resource.DefaultResource;
import com.friends.test.automation.controller.resource.DeleteSessionResource;
import com.friends.test.automation.controller.resource.ErrorResource;
import com.friends.test.automation.controller.resource.SessionResource;
import com.friends.test.automation.controller.resource.StatusResource;
import com.friends.test.automation.exception.DriverException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Service
public class DriverService extends BaseService {

    private final RestTemplate restTemplate;

    @Value("${testpine.driver.address}")
    private String driverAddress;

    private static String DRIVER_URL;

    public DriverService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void setUp() {
        DRIVER_URL = driverAddress;
    }

    public ResponseEntity<SessionResource> getSession(SessionDto sessionDto, String driverUrl) {
        String url = StringUtils.isEmpty(driverUrl) ? DRIVER_URL : driverUrl;
        HttpEntity<SessionDto> httpEntity = new HttpEntity<>(sessionDto);
        ResponseEntity<SessionResource> result = restTemplate.exchange(url + "/session", HttpMethod.POST,
                httpEntity, SessionResource.class);
        throwDriverExceptionWhenResultIsNull(result);
        return ResponseEntity.ok(result.getBody());
    }

    public ResponseEntity<DeleteSessionResource> deleteSession(String sessionId, String driverUrl) {
        String url = StringUtils.isEmpty(driverUrl) ? DRIVER_URL : driverUrl;
        HttpEntity httpEntity = new HttpEntity<>(null);
        ResponseEntity<DeleteSessionResource> result = restTemplate
                .exchange(url + "/session/{sessionId}", HttpMethod.DELETE,
                        httpEntity, DeleteSessionResource.class, sessionId);
        throwDriverExceptionWhenResultIsNull(result);
        return ResponseEntity.ok(result.getBody());
    }

    public ResponseEntity<StatusResource> getStatus(String driverUrl) {
        String url = StringUtils.isEmpty(driverUrl) ? DRIVER_URL : driverUrl;
        HttpEntity httpEntity = new HttpEntity<>(null);
        ResponseEntity<StatusResource> result = restTemplate.exchange(url + "/status", HttpMethod.GET,
                httpEntity, StatusResource.class);
        throwDriverExceptionWhenResultIsNull(result);
        return ResponseEntity.ok(result.getBody());
    }

    //POST /session/<session id>/timeouts
    public ResponseEntity<DefaultResource> setTimeout(String sessionId, TimeoutDto timeoutDto, String driverUrl) {
        String url = StringUtils.isEmpty(driverUrl) ? DRIVER_URL : driverUrl;
        HttpEntity httpEntity = new HttpEntity<>(timeoutDto);
        ResponseEntity<DefaultResource> result = restTemplate
                .exchange(url + "/session/{sessionId}/timeouts", HttpMethod.POST,
                        httpEntity, DefaultResource.class, sessionId);
        throwDriverExceptionWhenResultIsNull(result);
        return ResponseEntity.ok(result.getBody());
    }

    private void throwDriverExceptionWhenResultIsNull(ResponseEntity result) {
        if (result.getBody() == null) {
            throw new DriverException(ErrorResource.ErrorContent.builder()
                    .message("getSession() method -> Driver did not return any response").build(""));
        }
    }

    public ResponseEntity<DefaultResource> navigate(String sessionId, NavigateDto navigateDto, String driverUrl) {
        String url = StringUtils.isEmpty(driverUrl) ? DRIVER_URL : driverUrl;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity<>(navigateDto, httpHeaders);
        ResponseEntity<DefaultResource> result = restTemplate
                .exchange(url + "/session/{sessionId}/url", HttpMethod.POST,
                        httpEntity, DefaultResource.class, sessionId);
        throwDriverExceptionWhenResultIsNull(result);
        return ResponseEntity.ok(result.getBody());
    }

    public ResponseEntity<DefaultResource> getCurrentUrl(String sessionId, String driverUrl) {
        String url = StringUtils.isEmpty(driverUrl) ? DRIVER_URL : driverUrl;
        HttpEntity httpEntity = prepareContentType();
        ResponseEntity<DefaultResource> result = restTemplate
                .exchange(url + "/session/{sessionId}/url", HttpMethod.GET,
                        httpEntity, DefaultResource.class, sessionId);
        throwDriverExceptionWhenResultIsNull(result);
        return ResponseEntity.ok(result.getBody());
    }

    public ResponseEntity<DefaultResource> getPageTitle(String sessionId, String driverUrl) {
        String url = StringUtils.isEmpty(driverUrl) ? DRIVER_URL : driverUrl;
        HttpEntity httpEntity = prepareContentType();
        ResponseEntity<DefaultResource> result = restTemplate
                .exchange(url + "/session/{sessionId}/title", HttpMethod.GET,
                        httpEntity, DefaultResource.class, sessionId);
        throwDriverExceptionWhenResultIsNull(result);
        return ResponseEntity.ok(result.getBody());
    }

    private HttpEntity prepareContentType() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(httpHeaders);
    }

    public ResponseEntity<DefaultResource> maximize(String sessionId, String driverUrl) {
        String url = StringUtils.isEmpty(driverUrl) ? DRIVER_URL : driverUrl;
        HttpEntity httpEntity = prepareContentType();
        ResponseEntity<DefaultResource> result = restTemplate
                .exchange(url + "/session/{sessionId}/window/current/maximize", HttpMethod.POST,
                        httpEntity, DefaultResource.class, sessionId);
        throwDriverExceptionWhenResultIsNull(result);
        return ResponseEntity.ok(result.getBody());
    }

    public ResponseEntity<DefaultResource> findElement(String sessionId, FindElementDto findElementDto,
            String driverUrl) {
        String url = StringUtils.isEmpty(driverUrl) ? DRIVER_URL : driverUrl;
        HttpEntity<FindElementDto> httpEntity = new HttpEntity<>(findElementDto);
        ResponseEntity<DefaultResource> result = restTemplate
                .exchange(url + "/session/{sessionId}/element", HttpMethod.POST,
                        httpEntity, DefaultResource.class, sessionId);
        throwDriverExceptionWhenResultIsNull(result);
        return ResponseEntity.ok(result.getBody());
    }

    public ResponseEntity<DefaultResource> isSelectedElement(String sessionId, String elementId, String driverUrl) {
        String url = StringUtils.isEmpty(driverUrl) ? DRIVER_URL : driverUrl;
        HttpEntity httpEntity = prepareContentType();
        ResponseEntity<DefaultResource> result = restTemplate
                .exchange(url + "/session/{sessionId}/element/{{elementId}}/selected", HttpMethod.GET,
                        httpEntity, DefaultResource.class, sessionId, elementId);
        throwDriverExceptionWhenResultIsNull(result);
        return ResponseEntity.ok(result.getBody());

    }

    public ResponseEntity<DefaultResource> getAttributeByName(String sessionId, String elementId, String name,
            String driverUrl) {
        String url = StringUtils.isEmpty(driverUrl) ? DRIVER_URL : driverUrl;
        HttpEntity httpEntity = prepareContentType();
        ResponseEntity<DefaultResource> result = restTemplate
                .exchange(url + "/session/{session_id}/element/{element_id}/attribute/{name}", HttpMethod.GET,
                        httpEntity, DefaultResource.class, sessionId, elementId, name);
        throwDriverExceptionWhenResultIsNull(result);
        return ResponseEntity.ok(result.getBody());
    }

    public ResponseEntity<DefaultResource> getElementText(String sessionId, String elementId, String driverUrl) {
        String url = StringUtils.isEmpty(driverUrl) ? DRIVER_URL : driverUrl;
        HttpEntity httpEntity = prepareContentType();
        ResponseEntity<DefaultResource> result = restTemplate
                .exchange(url + "/session/{session_id}/element/{element_id}/text", HttpMethod.GET,
                        httpEntity, DefaultResource.class, sessionId, elementId);
        throwDriverExceptionWhenResultIsNull(result);
        return ResponseEntity.ok(result.getBody());
    }

    public ResponseEntity<DefaultResource> clickElement(String sessionId, String elementId, String driverUrl) {
        String url = StringUtils.isEmpty(driverUrl) ? DRIVER_URL : driverUrl;
        HttpEntity httpEntity = prepareContentType();
        ResponseEntity<DefaultResource> result = restTemplate
                .exchange(url + "/session/{session_id}/element/{element_id}/click", HttpMethod.POST,
                        httpEntity, DefaultResource.class, sessionId, elementId);
        throwDriverExceptionWhenResultIsNull(result);
        return ResponseEntity.ok(result.getBody());
    }

    public ResponseEntity<DefaultResource> sendKeys(String sessionId, String elementId, SendKeysDto sendKeysDto,
            String driverUrl) {
        String url = StringUtils.isEmpty(driverUrl) ? DRIVER_URL : driverUrl;
        HttpEntity httpEntity = new HttpEntity(sendKeysDto);
        ResponseEntity<DefaultResource> result = restTemplate
                .exchange(url + "/session/{session_id}/element/{element_id}/value", HttpMethod.POST,
                        httpEntity, DefaultResource.class, sessionId, elementId);
        throwDriverExceptionWhenResultIsNull(result);
        return ResponseEntity.ok(result.getBody());
    }
}
