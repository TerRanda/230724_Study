package com.thejoeun.practice1.boilerplate1.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequestDto {
    private String email;
    private String exPassword;
    private String newPassword;
}
