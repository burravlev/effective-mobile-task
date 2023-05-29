package com.burravlev.task.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    @Schema(name = "id", example = "1", type = "long")
    private Long id;
    @Schema(name = "username", example = "username", type = "string")
    private String username;
    @Schema(name = "email", example = "email@email.com", type = "long", description = "Valid user email")
    private String email;
    @Schema(name = "first_name", example = "John", type = "string")
    @JsonProperty("first_name")
    private String firstName;
    @Schema(name = "last_name", example = "Smith", type = "string")
    @JsonProperty("last_name")
    private String lastName;
}
