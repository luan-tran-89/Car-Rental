package com.edu.miu.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gasieugru
 */
public class Mapper<K,V> {

    @Autowired
    protected ModelMapper modelMapper;
    Class<K> entityClass;
    Class<V> dtoClass;
    public Mapper(Class<K> t, Class<V> u){
        this.entityClass = t;
        this.dtoClass = u;
    }
    public V toDto(K t){
        return (V) modelMapper.map(t, dtoClass);
    }

    public K toEntity(V u){
        return (K) modelMapper.map(u, entityClass);
    }

    public List<V> toListDto(Iterable<K> list){
        return ((List<K>)list).stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<K> toListEntity(List<V> list){
        return list.stream().map(this::toEntity).collect(Collectors.toList());
    }

}
