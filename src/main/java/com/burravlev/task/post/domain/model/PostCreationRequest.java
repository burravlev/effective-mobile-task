package com.burravlev.task.post.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostCreationRequest {
    @Schema(name = "creator",
            description = "User id", type = "long", example = "12344")
    @JsonProperty("creator")
    private Long creator;
    @Schema(name = "header",
            description = "Post header", type = "long", example = "Top 5 films")
    private String header;
    @Schema(name = "message", example = "My cat photo", description = "Optional if media content id applied")
    private String message;
    @Schema(name = "content", example = "[12, 1, 44]", description = "Optional if message text applied", type = "long[]")
    @JsonProperty("content")
    private List<Long> content;
}
