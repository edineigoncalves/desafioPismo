package com.simulation.transaction_simulation_test_service.e2e;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simulation.transaction_simulation_test_service.factories.AccountsFactory;
import com.simulation.transaction_simulation_test_service.factories.RegisterTransactionRequestFactory;
import com.simulation.transaction_simulation_test_service.resources.repositories.AccountsRepository;
import com.simulation.transaction_simulation_test_service.resources.repositories.TransactionsRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class TransactionsControllerIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    @LocalServerPort
    private Integer port;

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;

        accountsRepository.deleteAll();
        transactionsRepository.deleteAll();
    }

    @Test
    void connectionEstablished() {
        assertThat(postgreSQLContainer.isCreated()).isTrue();
        assertThat(postgreSQLContainer.isRunning()).isTrue();
    }

    @Test
    void shouldRegisterTransaction() throws JsonProcessingException {
        var accountMock = AccountsFactory.sampleWithCpf("32991132086");
        var registerTransactionRequestMock = RegisterTransactionRequestFactory.sampleWithOperationId("1");

        accountsRepository.save(accountMock);

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(registerTransactionRequestMock))
                .when()
                .post("/transactions")
                .then()
                .statusCode(200);

        var result = transactionsRepository.findAll();

        assertThat(result).hasSize(1);
    }
}
