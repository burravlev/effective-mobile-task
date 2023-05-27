package com.burravlev.task.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequest {
    private String username;
    private String email;
}
