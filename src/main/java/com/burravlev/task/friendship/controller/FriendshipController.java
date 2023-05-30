package com.burravlev.task.friendship.controller;

import com.burravlev.task.api.dto.ErrorDto;
import com.burravlev.task.api.dto.SuccessDto;
import com.burravlev.task.friendship.domain.model.FriendDeleteRequest;
import com.burravlev.task.friendship.domain.model.FriendshipRequest;
import com.burravlev.task.user.domain.model.UserModel;
import com.burravlev.task.friendship.domain.entity.Friendship;
import com.burravlev.task.user.domain.entity.UserEntity;
import com.burravlev.task.friendship.service.FriendshipService;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Friends API", description = "Methods for working with friends")
public class FriendshipController {
    private final FriendshipService friendshipService;
    private final Mapper<UserEntity, UserModel> mapper;

    @ApiResponses(value = {
    })
    @Operation(method = "POST", description = "Get user friend list")
    @GetMapping("/users/{id}/friends")
    public ResponseEntity<List<?>> findAllUserFriends(
            @PathVariable("id") Long id,
            @RequestParam("size") Integer size,
            @RequestParam("page") Integer page)
    {
        List<UserModel> users = friendshipService.getAllFriends(id, size, page)
                .stream().map(mapper::map).collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(method = "POST", description = "Approves or creates a friend request.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Friendship request created or approved",
                    content = @Content(schema = @Schema(implementation = SuccessDto.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Unauthorized request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = "application/json"))
    })
    @PostMapping("/me/friends")
    public ResponseEntity<SuccessDto> acceptOrNewRequest(Authentication auth, @RequestBody FriendshipRequest request) {
        Long userId = Long.parseLong(auth.getName());
        Friendship friendship = friendshipService.createOrApprove(userId, request);
        final SuccessDto successDto;
        if (friendship.getStatus() == Friendship.FriendshipStatus.REQUEST
                && friendship.getRequester().getId().equals(userId))
            successDto = new SuccessDto("Friendship request has been sent");
        else
            successDto = new SuccessDto("Friendship request accepted");
        return new ResponseEntity<>(successDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/me/friends")
    @Operation(method = "DELETE", description = "Removes the user from the friends list or rejects the friend request.",
            security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> deleteOrReject(Authentication auth, @RequestBody FriendDeleteRequest request) {
        return null;
    }

    @GetMapping("/me/friends")
    @Operation(method = "GET", description = "Get user's friends list",
            security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<?>> findAllUserFriends(
            Authentication auth,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        List<UserModel> users = friendshipService.getAllFriends(Long.parseLong(auth.getName()), size, page)
                .stream().map(mapper::map).collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/me/friends/requests")
    @Operation(method = "GET", description = "Get user's friendship requests",
            security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<?>> getAllUserRequests(
            Authentication auth,
            @RequestParam(value = "size",required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page
    ) {
        List<UserModel> users = friendshipService.getAllUserRequests(Long.parseLong(auth.getName()), size, page)
                .stream().map(mapper::map).collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/me/friends/subscribers")
    @Operation(method = "GET", description = "Get user's subscribers list",
            security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<?>> getAllUserSubscribers(
            Authentication auth,
            @RequestParam(value = "size",required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page
    ) {
        List<UserModel> users = friendshipService.getAllUserSubscribers(Long.parseLong(auth.getName()), size, page)
                .stream().map(mapper::map).collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
