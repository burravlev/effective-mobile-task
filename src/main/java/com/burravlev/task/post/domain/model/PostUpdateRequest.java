package com.burravlev.task.post.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostUpdateRequest {
    @Schema(name = "id", example = "1", description = "Post id")
    private Long id;
    @Schema(name = "header", description = "Updated header")
    private String header;
    @Schema(name = "message", example = "Updated message", description = "Post text")
    private String message;
    @Schema(name = "content", example = "[]", description = "Media ids", type = "long")
    @JsonProperty("content")
    private List<Long> content;
}
