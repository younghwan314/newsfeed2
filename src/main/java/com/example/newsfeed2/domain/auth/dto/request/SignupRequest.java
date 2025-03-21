package com.example.newsfeed2.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignupRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @Pattern(regexp = "(?=.*\\d.*)(?=.*[A-Z].*).{8,}", message = "비밀번호는 8자 이상이어야 하고, 숫자와 대문자를 포함해야 합니다.")
    private String password;

}
