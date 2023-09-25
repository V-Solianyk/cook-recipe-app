package com.example.cookrecipeappilication.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
