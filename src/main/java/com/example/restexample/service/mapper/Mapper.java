package com.example.restexample.service.mapper;


public interface Mapper<E, D> {
    E mapToEntity(D d);

    D mapToDto(E e);
}
