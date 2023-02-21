package com.iarasantos.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class DozerMapper {
    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    /**
     * Method to convert an Object into an entity and return the new entity
     * @param <O> object's origin type to be converted
     * @param <D> object's destiny type to convert
     * */
    public static <O, D> D parseObject(O origin, Class<D> destiny){
        return mapper.map(origin, destiny);

    }

    /**
     * Method to convert a list of Object into a list of entity and return the new list of entity
     * @param <O> object's origin list to be converted
     * @param <D> object's destiny to convert
     * */
    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
        List<D> destinationObjects = new ArrayList<D>();
        for (Object o : origin) {
            destinationObjects.add(mapper.map(o, destination));
        }
        return destinationObjects;
    }
}
