package com.burravlev.task.user.controller;

import com.burravlev.task.user.domain.dto.FriendDeleteRequest;
import com.burravlev.task.user.domain.dto.FriendshipRequest;
import com.burravlev.task.user.domain.dto.UserDto;
import com.burravlev.task.user.domain.model.UserModel;
import com.burravlev.task.user.service.FriendshipService;
import com.burravlev.task.util.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class FriendshipController {
    private final FriendshipService friendshipService;
    private final Mapper<UserModel, UserDto> mapper;

    @GetMapping("/{id}/friends")
    public ResponseEntity<List<?>> findAllUserFriends(
            @PathVariable("id") Long id,
            Authentication auth,
            @RequestParam("size") Integer size,
            @RequestParam("page") Integer page) {
        List<UserDto> users = friendshipService.getAllFriends(id, size, page)
                .stream().map(mapper::map).collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/me/friends.add")
    public ResponseEntity<?> acceptOrNewRequest(Authentication auth, @RequestBody FriendshipRequest request) {
        friendshipService.newRequest(Long.parseLong(auth.getName()), request.getUserId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/me/friends.delete")
    public ResponseEntity<?> deleteOrReject(Authentication auth, @RequestBody FriendDeleteRequest request) {
        return null;
    }

    @GetMapping("/me/friends")
    public ResponseEntity<List<?>> findAllUserFriends(
            Authentication auth,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        List<UserDto> users = friendshipService.getAllFriends(Long.parseLong(auth.getName()), size, page)
                .stream().map(mapper::map).collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/me/friends/requests")
    public ResponseEntity<List<?>> getAllUserRequests(
            Authentication auth,
            @RequestParam(value = "size",required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page
    ) {
        List<UserDto> users = friendshipService.getAllUserRequests(Long.parseLong(auth.getName()), size, page)
                .stream().map(mapper::map).collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/me/friends/subscribers")
    public ResponseEntity<List<?>> getAllUserSubscribers(
            Authentication auth,
            @RequestParam(value = "size",required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page
    ) {
        List<UserDto> users = friendshipService.getAllUserSubscribers(Long.parseLong(auth.getName()), size, page)
                .stream().map(mapper::map).collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/me/friends/subscribers/{id}")
    public ResponseEntity<?> accept(
            @PathVariable("id") Long id,
            Authentication auth,
            @RequestParam(value = "size",required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page
    ) {
        friendshipService.accept(Long.parseLong(auth.getName()), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
