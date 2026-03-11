package com.raquib.cartapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @Pattern(
            regexp = "^(ROLE_USER|ROLE_ADMIN)?$",
            message = "Role must be ROLE_USER or ROLE_ADMIN"
    )
    private String role;
}
