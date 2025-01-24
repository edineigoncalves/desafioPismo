package com.simulation.transaction_simulation_test_service.e2e;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.simulation.transaction_simulation_test_service.factories.AccountsFactory;
import com.simulation.transaction_simulation_test_service.factories.CreateAccountDtoFactory;
import com.simulation.transaction_simulation_test_service.resources.repositories.AccountsRepository;
import io.restassured.RestAssured;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class AccountsControllerIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    @LocalServerPort
    private Integer port;

    @Autowired
    private AccountsRepository accountsRepository;

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
    }

    @Test
    void connectionEstablished() {
        assertThat(postgreSQLContainer.isCreated()).isTrue();
        assertThat(postgreSQLContainer.isRunning()).isTrue();
    }

    @Test
    void shouldCreateAccountWithValidCpf() throws JsonProcessingException {
        String validCpf = "32991132086";

        var createAccountDtoMock = CreateAccountDtoFactory.sampleWithCpf(validCpf);

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(createAccountDtoMock))
                .when()
                .post("/accounts")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        var accountExists = accountsRepository.findByDocumentNumber(validCpf).isPresent();

        assertThat(accountExists).isTrue();
    }

    @Test
    void shouldGetAccountInfo() {
        var accountMock = AccountsFactory.sampleWithCpf("32991132086");

        accountsRepository.save(accountMock);

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get("/accounts/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("document_number", equalTo(accountMock.getDocumentNumber()));
    }
}
