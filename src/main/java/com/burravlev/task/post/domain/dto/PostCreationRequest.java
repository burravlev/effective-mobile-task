package com.burravlev.task.post.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreationRequest {
    @Schema(name = "created_by",
            description = "User id", type = "long", example = "12344")
    @JsonProperty("created_by")
    private Long createdBy;
    @Schema(name = "header",
            description = "Post header", type = "long", example = "Top 5 films")
    private String header;
    @Schema(name = "message", example = "My cat photo", description = "Optional if media content id applied")
    private String message;
    @Schema(name = "media_id", example = "My cat photo", description = "Optional if message text applied", type = "long")
    @JsonProperty("media_id")
    private Long mediaId;
}
