package com.burravlev.task.user.controller;

import com.burravlev.task.api.dto.ErrorDto;
import com.burravlev.task.auth.dto.AuthenticationRequest;
import com.burravlev.task.auth.dto.AuthenticationResponse;
import com.burravlev.task.auth.service.AuthenticationService;
import com.burravlev.task.user.domain.dto.PasswordUpdateRequest;
import com.burravlev.task.user.domain.dto.UserInfoUpdateRequest;
import com.burravlev.task.user.domain.dto.UserDto;
import com.burravlev.task.user.domain.model.UserModel;
import com.burravlev.task.user.service.UserService;
import com.burravlev.task.util.mapper.Mapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
@Tag(name = "Account API", description = "Methods to manage personal account")
public class AccountController {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final Mapper<UserModel, UserDto> mapper;

    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Password was successfully updated, user receives new token",
                    content = @Content(schema = @Schema(implementation = AuthenticationResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "User is unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = "application/json"))
    })
    @Operation(summary = "Password endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/password")
    public ResponseEntity<AuthenticationResponse> updatePassword(Authentication auth, @RequestBody PasswordUpdateRequest request) {
        UserModel user = userService.updatePassword(Long.parseLong(auth.getName()), request);
        return new ResponseEntity<>(authenticationService.authenticate(
                AuthenticationRequest.builder().username(user.getPublicUsername())
                        .password(request.getNewPassword()).build()
        ), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User info updated",
                    content = @Content(schema = @Schema(implementation = UserDto.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = "application/json"))
    })
    @Operation(summary = "Account endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping
    public ResponseEntity<?> updateUserInfo(Authentication auth, @RequestBody UserInfoUpdateRequest request) {
        UserModel user = userService.update(Long.parseLong(auth.getName()), request);
        return new ResponseEntity<>(mapper.map(user), HttpStatus.OK);
    }
}
