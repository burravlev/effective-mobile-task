package com.burravlev.task.auth.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {
    private String username;
    private String email;
    //think about access level
    private String password;
}
