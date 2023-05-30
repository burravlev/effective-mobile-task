package com.burravlev.task.auth.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {
    @Schema(name = "username", description = "Can be empty if email is not empty", example = "user123")
    private String username;
    @Schema(name = "username", description = "Can be empty if email is not empty", example = "valid@meai.com")
    @Email(message = "Email is not valid")
    private String email;
    @NotBlank
    private String password;
}
