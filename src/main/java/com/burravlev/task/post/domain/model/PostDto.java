package com.burravlev.task.post.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @Schema(name = "content", example = "My cat photo", description = "ID to media content", type = "long")
    @JsonProperty("content")
    private List<Long> content;
    @Schema(name = "created", description = "Created date")
    @JsonProperty("created")
    private LocalDateTime created;
}