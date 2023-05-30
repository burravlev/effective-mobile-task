package com.burravlev.task.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {
    @Schema(name = "error", description = "Contains error messages", example = "[Entity not found]")
    private List<String> error = new ArrayList<>();

    public ErrorDto(String message) {
        this.error.add(message);
    }
}
