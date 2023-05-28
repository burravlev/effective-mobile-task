package com.burravlev.task.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendDeleteRequest {
    @JsonProperty("user_id")
    private Long userId;
}
