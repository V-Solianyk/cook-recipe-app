package com.example.cookrecipeappilication.mapper;

public interface ResponseDtoMapper<D, T> {
    D mapToDto(T model);
}
