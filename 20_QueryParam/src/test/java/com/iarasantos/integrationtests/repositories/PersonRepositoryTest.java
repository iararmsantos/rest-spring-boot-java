package com.iarasantos.integrationtests.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.iarasantos.integrationtests.testcontainers.AbstractIntegrationTest;
import com.iarasantos.model.Person;
import com.iarasantos.repositories.PersonRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonRepositoryTest extends AbstractIntegrationTest {

    //have to use entity Person
    private static Person person;
    //inject person repository
    @Autowired
    PersonRepository repository;

    //tests setup
    @BeforeAll
    public static void setup() {
        person = new Person();
    }

    @Test
    @Order(1)
    public void testFindByName() throws JsonMappingException, JsonProcessingException {
        //go to database and verify if data is equal
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "firstName"));
        person = repository.findPersonsByName("ayr", pageable).toList().get(0);

        assertNotNull(person.getId());
        assertNotNull(person.getFirstName());
        assertNotNull(person.getLastName());
        assertNotNull(person.getEmail());
        assertTrue(person.getEnabled());

        assertEquals(770, person.getId());

        assertEquals("Sayre", person.getFirstName());
        assertEquals("Donan", person.getLastName());
        assertEquals("sdonanld@cyberchimps.com", person.getEmail());
    }

    @Test
    @Order(2)
    public void disablePerson() throws JsonMappingException, JsonProcessingException {
        //get the person of the last test
        repository.disablePerson(person.getId());
        Pageable pageable = PageRequest.of(0, 6, Sort.by(Sort.Direction.ASC, "firstName"));
        //go to database and verify if data is equal
        person = repository.findPersonsByName("ayr", pageable).toList().get(0);

        assertNotNull(person.getId());
        assertNotNull(person.getFirstName());
        assertNotNull(person.getLastName());
        assertNotNull(person.getEmail());
        assertFalse(person.getEnabled());

        assertEquals(770, person.getId());

        assertEquals("Sayre", person.getFirstName());
        assertEquals("Donan", person.getLastName());
        assertEquals("sdonanld@cyberchimps.com", person.getEmail());
    }
}
