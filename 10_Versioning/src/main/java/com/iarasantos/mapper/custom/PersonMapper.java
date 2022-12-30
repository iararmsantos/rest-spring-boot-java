package com.iarasantos.mapper.custom;

import com.iarasantos.data.vo.v2.PersonVOV2;
import com.iarasantos.model.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {
    public PersonVOV2 convertEntityToVO(Person person){
        PersonVOV2 vo = new PersonVOV2();
        vo.setId(person.getId());
        vo.setFirstName(person.getFirstName());
        vo.setLastName(person.getLastName());
        vo.setEmail(person.getEmail());
        vo.setBirthDate(new Date());
        return vo;
    }

    public Person ConvertVOToEntity(PersonVOV2 person){
        Person entity = new Person();
        entity.setId(person.getId());
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setEmail(person.getEmail());
        //vo.setBirthDate(new Date());
        return entity;
    }
}
