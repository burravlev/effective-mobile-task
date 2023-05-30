package com.burravlev.task.auth.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
    @NotNull
    @NotEmpty(message = "Username can't be empty")
    @Schema(name = "username", description = "Username", example = "user.user")
    private String username;
    @NotNull
    @NotEmpty(message = "Email can't be empty")
    @Email(message = "Email is not valid")
    @Schema(name = "email", description = "User's email address", example = "example@email.com")
    private String email;
    @NotEmpty(message = "Password can't be empty")
    @Schema(name = "password", description = "User's email address", example = "password")
    private String password;
}
