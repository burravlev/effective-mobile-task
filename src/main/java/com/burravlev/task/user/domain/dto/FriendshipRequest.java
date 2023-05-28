package com.burravlev.task.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendshipRequest {
    @JsonProperty("user_id")
    private Long userId;
    private boolean follow;
}
