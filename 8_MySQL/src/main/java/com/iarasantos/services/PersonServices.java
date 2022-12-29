package com.iarasantos.services;

import com.iarasantos.exceptions.handler.ResourceNotFoundException;
import com.iarasantos.model.Person;
import com.iarasantos.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    public Person create(Person person) {

        logger.info("Creating one person!");
        return repository.save(person);
    }

    public Person update(Person person) {

        logger.info("Updating one person!");
        Person entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setEmail(person.getEmail());

        return repository.save(person);
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");
        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
        repository.delete(entity);
    }

    public Person findById(Long id) {
        logger.info("Searching one person!");

        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
    }

    public List<Person> findAll() {
        logger.info("Searching all people!");

        return repository.findAll();
    }
}