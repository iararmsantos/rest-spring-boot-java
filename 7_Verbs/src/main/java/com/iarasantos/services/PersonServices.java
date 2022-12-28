package com.iarasantos.services;

import com.iarasantos.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {
    private Logger logger = Logger.getLogger(PersonServices.class.getName());
    private final AtomicLong counter = new AtomicLong();

    public Person create(Person person) {

        logger.info("Creating one person!");
        return person;
    }

    public Person update(Person person) {

        logger.info("Updating one person!");
        return person;
    }

    public void delete(String id) {
        logger.info("Deleting one person!");
    }

    public Person findById(String id) {
        logger.info("Searching one person!");
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Iara");
        person.setLastName("Santos");
        person.setEmail("iarasantos@gmail.com");
        return person;
    }

    public List<Person> findAll() {
        logger.info("Searching all people!");
        List<Person> persons = new ArrayList<Person>();
        for (int i = 0; i < 8; i++) {
            Person person = mockPerson(i);
            persons.add(person);
        }
        return persons;
    }

    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Person name " + i);
        person.setLastName("Last name " + i);
        person.setEmail("some email address " + i);

        return person;
    }

}