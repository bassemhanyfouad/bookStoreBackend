package com.check24.codingchallenge.bookstore;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.validation.constraints.NotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataJpa
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {AbstractIT.Initializer.class})
@ActiveProfiles("integration,flyway")
@Transactional
public abstract class AbstractIT {
    @LocalServerPort
    private int port;

    protected TestRestTemplate restTemplate = new TestRestTemplate();

    @Autowired
    private FlywayAutoConfiguration.FlywayConfiguration flywayConfig;

    protected String createURLWithPort(String uri) {
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

}
