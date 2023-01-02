package com.iarasantos.services;

import com.iarasantos.controllers.PersonController;
import com.iarasantos.data.vo.v1.PersonVO;
import com.iarasantos.exceptions.handler.RequiredObjectIsNullException;
import com.iarasantos.exceptions.handler.ResourceNotFoundException;
import com.iarasantos.mapper.DozerMapper;
import com.iarasantos.model.Person;
import com.iarasantos.repositories.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    /**
     * Convert object to entity then save it and convert back to object
     * @param personVO object to be converted, saved, then converted back
     * */
    public PersonVO create(PersonVO personVO) {
        if(personVO == null) throw new RequiredObjectIsNullException();
        logger.info("Creating one person!");
        Person entity = DozerMapper.parseObject(personVO, Person.class);
        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVO update(PersonVO personVO) {
        if(personVO == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one person!");
        Person entity = repository.findById(personVO.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));

        entity.setFirstName(personVO.getFirstName());
        entity.setLastName(personVO.getLastName());
        entity.setEmail(personVO.getEmail());

        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
        repository.delete(entity);
    }

    /**
     * Find object then convert to entity to return it
     * @param id  of the object to be found
     * */
    public PersonVO findById(Long id) {
        logger.info("Searching one person!");

        Person  entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
        var vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    /**
     * Find all objects then convert to entity's list to return it
     * */
    public List<PersonVO> findAll() {
        logger.info("Searching all people!");

        var persons = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
        persons.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
        return persons;
    }
}