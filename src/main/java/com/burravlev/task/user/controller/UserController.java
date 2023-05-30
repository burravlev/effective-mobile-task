package com.burravlev.task.user.controller;

import com.burravlev.task.api.dto.ErrorDto;
import com.burravlev.task.user.domain.model.UserModel;
import com.burravlev.task.user.domain.entity.UserEntity;
import com.burravlev.task.user.service.UserService;
import com.burravlev.task.util.mapper.Mapper;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users API", description = "Methods for working with users")
public class UserController {
    private final UserService userService;
    private final Mapper<UserEntity, UserModel> mapper;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get user by id",
                    content = @Content(schema = @Schema(implementation = UserModel.class),
                    mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class),
                    mediaType = "application/json")),
            })
    @GetMapping("/{id}")
    public ResponseEntity<UserModel> get(@PathVariable("id") Long id) {
        UserModel user = mapper.map(userService.findById(id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
