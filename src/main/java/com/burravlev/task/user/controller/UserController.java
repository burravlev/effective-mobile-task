package com.burravlev.task.user.controller;

import com.burravlev.task.api.dto.ErrorDto;
import com.burravlev.task.user.dto.UpdateRequest;
import com.burravlev.task.user.dto.UserDto;
import com.burravlev.task.user.model.UserModel;
import com.burravlev.task.user.service.UserService;
import com.burravlev.task.util.mapper.Mapper;
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

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> get(@PathVariable("username") String username) {
        UserDto user = mapper.map(userService.findByUsername(username));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> update(@PathVariable("username") String username,
                                    Authentication authentication,
                                    @RequestBody UpdateRequest request) {
        if (!username.equals(authentication.getName()))
            return new ResponseEntity<>(new ErrorDto("No access to update this user"), HttpStatus.FORBIDDEN);
        userService.update(username, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
