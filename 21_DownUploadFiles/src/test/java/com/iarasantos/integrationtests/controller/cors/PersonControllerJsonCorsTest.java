package com.iarasantos.integrationtests.controller.cors;

import com.iarasantos.configs.TestConfigs;
import com.iarasantos.integrationtests.testcontainers.AbstractIntegrationTest;
import com.iarasantos.integrationtests.vo.AccountCredentialsVO;
import com.iarasantos.integrationtests.vo.PersonVO;
import com.iarasantos.integrationtests.vo.TokenVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerJsonCorsTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static PersonVO person;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        person = new PersonVO();
    }

    @Test
    @Order(0)
    public void authorization() throws IOException {
        AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");

        var accessToken = given()
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(user)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenVO.class)
                .getAccessToken();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    public void testCreate() throws IOException {
        mockPerson();

        var content =
                given().spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
                        .body(person)
                        .when()
                        .post()
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .asString();

        PersonVO createdPerson = objectMapper.readValue(content, PersonVO.class);
        person = createdPerson;

        assertNotNull(createdPerson);
        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getEmail());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Richard", createdPerson.getFirstName());
        assertEquals("Stallman", createdPerson.getLastName());
        assertEquals("r.s.open@Ig.com", createdPerson.getEmail());
    }

    @Test
    @Order(2)
    public void testCreateWithWrongOrigin() throws IOException {
        mockPerson();

        var content =
                given().spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SEMERU)
                        .body(person)
                        .when()
                        .post()
                        .then()
                        .statusCode(403)
                        .extract()
                        .body()
                        .asString();

        assertNotNull(content);
        assertEquals("Invalid CORS request", content);
    }

    @Test
    @Order(3)
    public void testFindById() throws IOException {
        mockPerson();

        var content =
                given().spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
                        .pathParams("id", person.getId())
                        .when()
                        .get("{id}")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .asString();

        PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
        person = persistedPerson;

        assertNotNull(persistedPerson);
        assertNotNull(persistedPerson.getId());
        assertNotNull(persistedPerson.getFirstName());
        assertNotNull(persistedPerson.getLastName());
        assertNotNull(persistedPerson.getEmail());
        assertTrue(persistedPerson.getId() > 0);

        assertEquals("Richard", persistedPerson.getFirstName());
        assertEquals("Stallman", persistedPerson.getLastName());
        assertEquals("r.s.open@Ig.com", persistedPerson.getEmail());
    }

    @Test
    @Order(4)
    public void testFindByIdWithWrongOrigin() throws IOException {
        mockPerson();

        var content =
                given().spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SEMERU)
                        .pathParams("id", person.getId())
                        .when()
                        .get("{id}")
                        .then()
                        .statusCode(403)
                        .extract()
                        .body()
                        .asString();

        assertNotNull(content);
        assertEquals("Invalid CORS request", content);
    }

    @Test
    @Order(5)
    public void testDelete() throws IOException {

        given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", person.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }

    private void mockPerson() {
        person.setFirstName("Richard");
        person.setLastName("Stallman");
        person.setEmail("r.s.open@Ig.com");
        person.setEnabled(true);
    }

}
