package com.example.cookrecipeappilication.mapper;

import com.example.cookrecipeappilication.config.MapperConfig;
import com.example.cookrecipeappilication.dto.response.UserResponseDto;
import com.example.cookrecipeappilication.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserResponseDtoMapper {
    UserResponseDto mapToDto(User user);
}
