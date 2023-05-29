package com.burravlev.task.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordUpdateRequest {
    @Schema(name = "old_password", type = "string", description = "Old user password", example = "password")
    @JsonProperty("old_password")
    private String oldPassword;
    @Schema(name = "new_password", type = "string", description = "New user password", example = "new")
    @JsonProperty("new_password")
    private String newPassword;
}
