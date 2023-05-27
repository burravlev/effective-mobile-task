package com.burravlev.task.user.controller;

import com.burravlev.task.user.dto.UserDto;
import com.burravlev.task.user.model.UserModel;
import com.burravlev.task.user.service.UserService;
import com.burravlev.task.util.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final Mapper<UserModel, UserDto> mapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> get(@PathVariable("id") Long id) {
        System.out.println(id);
        UserDto user = mapper.map(userService.findById(id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
