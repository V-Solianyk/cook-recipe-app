package com.example.cookrecipeappilication.mapper;

public interface RequestDtoMapper<D, T> {
    T mapToModel(D dto);
}