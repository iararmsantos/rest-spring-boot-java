package com.iarasantos.services;

import com.iarasantos.controllers.PersonController;
import com.iarasantos.data.vo.v1.PersonVO;
import com.iarasantos.exceptions.RequiredObjectIsNullException;
import com.iarasantos.exceptions.ResourceNotFoundException;
import com.iarasantos.mapper.DozerMapper;
import com.iarasantos.model.Person;
import com.iarasantos.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServices {
    @Autowired
    PersonRepository repository;

    @Autowired
    PagedResourcesAssembler<PersonVO> assembler;
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    /**
     * Convert object to entity then save it and convert back to object
     *
     * @param personVO object to be converted, saved, then converted back
     */
    public PersonVO create(PersonVO personVO) {
        if (personVO == null) throw new RequiredObjectIsNullException();
        logger.info("Creating one person!");
        Person entity = DozerMapper.parseObject(personVO, Person.class);
        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVO update(PersonVO personVO) {
        if (personVO == null) throw new RequiredObjectIsNullException();

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
     *
     * @param id of the object to be found
     */
    public PersonVO findById(Long id) {
        logger.info("Searching one person!");

        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
        var vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    /**
     * Find all objects then convert to entity's list to return it
     */
    public PagedModel<EntityModel<PersonVO>> findAll(Pageable pageable) {
        logger.info("Searching all people!");

        var personPage = repository.findAll(pageable);
        var personVOsPage = personPage.map(p -> DozerMapper.parseObject(p, PersonVO.class));
        personVOsPage.map(p -> p.add(
                linkTo(methodOn(PersonController.class)
                        .findById(p.getKey())).withSelfRel()));

        Link link = linkTo(methodOn(PersonController.class)
                .findAll(pageable.getPageNumber(),
                        pageable.getPageSize(), "asc")).withSelfRel();
        return assembler.toModel(personVOsPage, link);
    }

    /**
     * Find objects by name then convert to entity's list to return it
     */
    public PagedModel<EntityModel<PersonVO>> findPersonsByName(String firstName, Pageable pageable) {
        logger.info("Searching person by name!");

        var personPage = repository.findPersonsByName(firstName, pageable);
        var personVOsPage = personPage.map(p -> DozerMapper.parseObject(p, PersonVO.class));
        personVOsPage.map(p -> p.add(
                linkTo(methodOn(PersonController.class)
                        .findById(p.getKey())).withSelfRel()));

        Link link = linkTo(methodOn(PersonController.class)
                .findAll(pageable.getPageNumber(),
                        pageable.getPageSize(), "asc")).withSelfRel();
        return assembler.toModel(personVOsPage, link);
    }

    /**
     * Find object then disable it
     *
     * @param id of the object to be found
     */
    @Transactional
    public PersonVO disablePerson(Long id) {
        logger.info("Disabling one person!");
        repository.disablePerson(id);

        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
        var vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }
}