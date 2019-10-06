package com.check24.codingchallenge.bookstore.controller;

import com.check24.codingchallenge.bookstore.payload.LoginRequest;
import com.check24.codingchallenge.bookstore.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.validation.constraints.NotNull;

/**
 * Created by Bassem
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataJpa
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {UserControllerIT.Initializer.class})
@ActiveProfiles("integration,flyway")
@Transactional
public class UserControllerIT {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Autowired
    UserRepository userRepository;

    @Autowired
    private FlywayAutoConfiguration.FlywayConfiguration flywayConfig;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    private static PostgreSQLContainer postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:9.6-alpine")
                    .withDatabaseName("books_service_test")
                    .withUsername("test")
                    .withPassword("test")
                    .withExposedPorts(5432)
                    .withNetwork(Network.SHARED);


    // fix for several integration tests classes using an abstract test class:
    // see https://github.com/testcontainers/testcontainers-java/issues/417
    static {
        postgreSQLContainer.start();
    }

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(@NotNull ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.database.name=" + postgreSQLContainer.getDatabaseName(),
                    "spring.datasource.name=" + postgreSQLContainer.getDatabaseName(),
                    "spring.datasource.driverClassName=" + postgreSQLContainer.getDriverClassName(),
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    public void testSuccessfulLoginRequest() {
        LoginRequest loginRequest = new LoginRequest("ab@gmail.com", "1234");
        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest, new HttpHeaders());
        ResponseEntity responseEntity = restTemplate.exchange(createURLWithPort("/books-svc/task/login"),
                HttpMethod.POST, entity, String.class);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);


    }

    @Test
    public void testWrongCredentialsLoginRequest() {
        LoginRequest loginRequest = new LoginRequest("ab@gmail.com", "123");
        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest, new HttpHeaders());
        ResponseEntity responseEntity = restTemplate.exchange(createURLWithPort("/books-svc/task/login"),
                HttpMethod.POST, entity, String.class);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testWrongEmailLoginRequest() {
        LoginRequest loginRequest = new LoginRequest("ab@gmal.com", "1234");
        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest, new HttpHeaders());
        ResponseEntity responseEntity = restTemplate.exchange(createURLWithPort("/books-svc/task/login"),
                HttpMethod.POST, entity, String.class);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testInvalidEmailLoginRequest() {
        LoginRequest loginRequest = new LoginRequest("abgmail.com", "1234");
        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest, new HttpHeaders());
        ResponseEntity responseEntity = restTemplate.exchange(createURLWithPort("/books-svc/task/login"),
                HttpMethod.POST, entity, String.class);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}
