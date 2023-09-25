package com.example.cookrecipeappilication.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginRequestDto {
    private String email;
    private String password;
}
