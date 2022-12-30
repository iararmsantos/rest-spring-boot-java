package com.iarasantos.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class DozerMapper {
    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    /**
     * Method to convert an Object into an entity and return the new entity
     * @param <Origin> object to be converted
     * @param <Destiny> object type to convert
     * */
    public static <Origin, Destiny> Destiny parseObject(Origin origin, Class<Destiny> destiny){
        return mapper.map(origin, destiny);

    }

    /**
     * Method to convert a list of Object into a list of entity and return the new list of entity
     * @param <Origin> object's list to be converted
     * @param <Destiny> object type to convert
     * */
    public static <Origin, Destiny> List<Destiny> parseListObjects(List<Origin> origin, Class<Destiny> destiny){
        List<Destiny> destinyObjects = new ArrayList<Destiny>();
        for(Origin o : origin){
            destinyObjects.add(mapper.map(origin, destiny));
        }
        return destinyObjects;
    }
}
