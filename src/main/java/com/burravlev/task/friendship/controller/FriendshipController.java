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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
            @ApiResponse(responseCode = "200", description = "Get all user friends.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserModel.class)),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = "application/json"))

    })
    @Operation(method = "GET", description = "Get user friends list")
    @GetMapping("/users/{id}/friends")
    public ResponseEntity<List<?>> findAllUserFriends(
            @PathVariable("id") Long id,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        List<UserModel> users = friendshipService.getAllFriends(id, size, page)
                .stream().map(mapper::map).collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Creates or approves friendship request",
                    content = @Content(schema = @Schema(implementation = SuccessDto.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Unauthorized request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = "application/json"))
    })
    @Operation(method = "POST", description = "Approves or creates a friend request.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
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

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete user from friends or unsubscribe from user.",
                    content = @Content(schema = @Schema(implementation = SuccessDto.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Unauthorized request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = "application/json"))
    })
    @Operation(method = "DELETE", description = "Removes the user from the friends list or rejects the friend request.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/me/friends")
    public ResponseEntity<?> deleteOrReject(Authentication auth, @RequestBody FriendDeleteRequest request) {
        final Long userId = Long.parseLong(auth.getName());
        Friendship friendship = friendshipService.delete(userId, request);
        final SuccessDto response;
        if (friendship.getId() != null)
            response = new SuccessDto("User: " + request.getUserId() + " successfully deleted from friends");
        else
            response = new SuccessDto("Unsubscribed from user: " + request.getUserId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all user friends.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserModel.class)),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Unauthorized request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = "application/json"))
    })
    @Operation(method = "GET", description = "Get authenticated user's friends list",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(name = "size", allowEmptyValue = true, description = "Controls friends list size"),
                    @Parameter(name = "page", description = "Controls response page", allowEmptyValue = true)})
    @GetMapping("/me/friends")
    public ResponseEntity<List<?>> findAllUserFriends(
            Authentication auth,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        List<UserModel> users = friendshipService.getAllFriends(Long.parseLong(auth.getName()), size, page)
                .stream().map(mapper::map).collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all user requests.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserModel.class)),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Unauthorized request",
                    content = @Content(schema = @Schema(),
                            mediaType = "application/json"))
    })
    @Operation(method = "GET", description = "Get authenticated user's friendship requests",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(name = "size", allowEmptyValue = true, description = "Controls friends list size"),
                    @Parameter(name = "page", description = "Controls response page", allowEmptyValue = true)})
    @GetMapping("/me/friends/requests")
    public ResponseEntity<List<?>> getAllUserRequests(
            Authentication auth,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page
    ) {
        List<UserModel> users = friendshipService.getAllUserRequests(Long.parseLong(auth.getName()), size, page)
                .stream().map(mapper::map).collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all user subscribers.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserModel.class)),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Unauthorized request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = "application/json"))
    })
    @Operation(method = "GET", description = "Get user's subscribers list",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(name = "size", allowEmptyValue = true, description = "Controls friends list size"),
                    @Parameter(name = "page", description = "Controls response page", allowEmptyValue = true)})
    @GetMapping("/me/friends/subscribers")
    public ResponseEntity<List<?>> getAllUserSubscribers(
            Authentication auth,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page
    ) {
        List<UserModel> users = friendshipService.getAllUserSubscribers(Long.parseLong(auth.getName()), size, page)
                .stream().map(mapper::map).collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
