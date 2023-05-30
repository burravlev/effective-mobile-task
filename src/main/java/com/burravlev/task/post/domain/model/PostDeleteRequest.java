package com.burravlev.task.post.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDeleteRequest {
    @Schema(name = "post_id", description = "ID of the post to be deleted", example = "12")
    @JsonProperty("post_id")
    private Long postId;
}
