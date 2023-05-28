package com.burravlev.task.user.controller;

import com.burravlev.task.api.dto.ErrorDto;
import com.burravlev.task.user.domain.dto.PasswordUpdateRequest;
import com.burravlev.task.user.domain.dto.UpdateRequest;
import com.burravlev.task.user.domain.dto.UserDto;
import com.burravlev.task.user.domain.model.UserModel;
import com.burravlev.task.user.service.UserService;
import com.burravlev.task.util.mapper.Mapper;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final Mapper<UserModel, UserDto> mapper;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get user by id",
                    content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "403", description = "Not authenticated",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> get(@PathVariable("id") Long id) {
        UserDto user = mapper.map(userService.findById(id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    Authentication authentication,
                                    @RequestBody UpdateRequest request) {
        if (!id.toString().equals(authentication.getName()))
            return new ResponseEntity<>(new ErrorDto("No access to update this user"), HttpStatus.FORBIDDEN);
        UserModel user = userService.update(id, request);
        return new ResponseEntity<>(mapper.map(user), HttpStatus.OK);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<?> updatePassword(@PathVariable("id") Long id, @RequestBody PasswordUpdateRequest request) {
        userService.updatePassword(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
