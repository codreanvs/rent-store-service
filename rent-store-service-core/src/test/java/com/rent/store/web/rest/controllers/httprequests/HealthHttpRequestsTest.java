package com.rent.store.web.rest.controllers.httprequests;

import com.rent.store.util.TestConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HealthHttpRequestsTest {

    @LocalServerPort
    private int localServerPort;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void shouldReturnStatusUpWhenHttpRequestIsMadeOnActuatorCheckHealthEndpoint() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        final HttpEntity<Object> httpEntity = new HttpEntity<>(null, httpHeaders);
        final ResponseEntity<Health> responseEntity = testRestTemplate.exchange(
                String.format(TestConstants.HOST_AND_API_FORMAT, localServerPort) + "/actuator/health",
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<Health>() {}
        );
        final Health serviceHealth = responseEntity.getBody();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(serviceHealth).isNotNull();
        Assertions.assertThat(serviceHealth.getStatus()).isEqualTo(Status.UP);
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @EqualsAndHashCode
    static class Health {
        private Status status;
    }

}
