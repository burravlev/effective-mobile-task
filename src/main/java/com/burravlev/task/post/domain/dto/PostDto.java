package com.burravlev.task.post.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {
    @Schema(name = "id", example = "1", description = "Post id")
    private Long id;
    @Schema(name = "created_by",
    description = "User id", type = "long", example = "12344")
    @JsonProperty("created_by")
    private Long createdBy;
    @Schema(name = "header", description = "My cat photo")
    private String header;
    @Schema(name = "message", example = "So beautiful", description = "Post text")
    private String message;
    @Schema(name = "media_id", example = "My cat photo", description = "ID to media content", type = "long")
    @JsonProperty("media_id")
    private Long mediaId;
    @Schema(name = "created", description = "Created date")
    @JsonProperty("created")
    private LocalDateTime created;
}