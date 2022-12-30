package com.iarasantos.services;

import com.iarasantos.data.vo.v1.PersonVO;
import com.iarasantos.data.vo.v2.PersonVOV2;
import com.iarasantos.exceptions.handler.ResourceNotFoundException;
import com.iarasantos.mapper.DozerMapper;
import com.iarasantos.mapper.custom.PersonMapper;
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

    @Autowired
    PersonMapper mapper;

    /**
     * Convert object to entity then save it and convert back to object
     * @param personVO object to be converted, saved, then converted back
     * */
    public PersonVO create(PersonVO personVO) {

        logger.info("Creating one person!");
        Person entity = DozerMapper.parseObject(personVO, Person.class);
        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        return vo;
    }

    public PersonVOV2 createV2(PersonVOV2 person) {

        logger.info("Creating one person!");
        Person entity = mapper.ConvertVOToEntity(person);
        PersonVOV2 vo = mapper.convertEntityToVO(repository.save(entity));
        return vo;
    }

    public PersonVO update(PersonVO personVO) {

        logger.info("Updating one person!");
        Person entity = repository.findById(personVO.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));

        entity.setFirstName(personVO.getFirstName());
        entity.setLastName(personVO.getLastName());
        entity.setEmail(personVO.getEmail());

        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");
        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
        repository.delete(entity);
    }

    /**
     * Find object then convert to entity to return it
     * @param id  of the object to be found
     * */
    public PersonVO findById(Long id) {
        logger.info("Searching one person!");

        Person  entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
        return DozerMapper.parseObject(entity, PersonVO.class);
    }

    /**
     * Find all objects then convert to entity's list to return it
     * */
    public List<PersonVO> findAll() {
        logger.info("Searching all people!");

        return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
    }
}