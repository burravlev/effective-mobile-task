package com.burravlev.task.friendship.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendshipRequest {
    @Schema(name = "user_id",
            description = "The ID of the user to whom the request needs to be sent, or the request from which it is necessary to approve.")
    @JsonProperty("user_id")
    private Long userId;
}
