package com.burravlev.task.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
    @Schema(name = "access_token", description = "Used to authenticate requests",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    @JsonProperty("access_token")
    private String accessToken;
    @Schema(name = "refresh_token", description = "Used to refresh access token",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxNDU2Nzg5MCIsIm5hbWUiOiJKb2huIERvZSIsImlhdCI6MTUxNjIzOTAyMn0.x0k4eGp9hMx5vTHrm3r2XDJuYsXD0zQKSZelmT1ZAYc")
    @JsonProperty("refresh_token")
    private String refreshToken;
}
