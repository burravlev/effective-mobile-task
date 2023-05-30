package com.burravlev.task.auth.controller;

import com.burravlev.task.api.dto.ErrorDto;
import com.burravlev.task.auth.model.AuthenticationRequest;
import com.burravlev.task.auth.model.AuthenticationResponse;
import com.burravlev.task.auth.model.RegistrationRequest;
import com.burravlev.task.auth.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication API endpoints")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully registered",
                    content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request, full details in response body.",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    })
    @Operation(method = "POST", description = "Used to register a new user.")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully authenticated, all previous tokens disabled",
                    content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request, full details in response body.",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Invalid username, email or password, detailed info in response body",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    })
    @Operation(method = "POST", description = "Used to authenticate user.")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed, new token in response body",
                    content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request, full details in response body.",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "User with this token can not be found.",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    })
    @Operation(method = "POST", description = "Used to refresh access token.")
    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            authenticationService.refreshToken(request, response);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
