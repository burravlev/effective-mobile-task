package com.burravlev.task.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoUpdateRequest {
    @Schema(name = "username", type = "string",
            description = "Leave field blank to not update username",
            example = "user")
    private String username;
    @Schema(name = "email", type = "string",
            description = "Receives valid email. Leave field blank to not update email",
            example = "email@email.com")
    private String email;
    @Schema(name = "first_name", type = "string",
            description = "Leave field blank to not update name",
            example = "Mary")
    @JsonProperty("first_name")
    private String firstName;
    @Schema(name = "last_name", type = "string",
            description = "Leave field blank to not update last name",
            example = "Jane")
    @JsonProperty("last_name")
    private String lastName;
}
