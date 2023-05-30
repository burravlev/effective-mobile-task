package com.burravlev.task.post.controller;

import com.burravlev.task.api.dto.ErrorDto;
import com.burravlev.task.exception.UnauthenticatedException;
import com.burravlev.task.post.domain.entity.PostModel;
import com.burravlev.task.post.domain.model.PostCreationRequest;
import com.burravlev.task.post.domain.model.PostDeleteRequest;
import com.burravlev.task.post.domain.model.PostDto;
import com.burravlev.task.post.domain.model.PostUpdateRequest;
import com.burravlev.task.post.service.PostService;
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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Posts API")
public class PostController {
    private final PostService postService;
    private final Mapper<PostModel, PostDto> mapper;


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all followed users' posts.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PostDto.class)),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Unauthenticated request")
    })
    @Operation(method = "GET", description = "Get all followed users' posts",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(name = "size", allowEmptyValue = true, description = "Controls friends list size"),
                    @Parameter(name = "page", description = "Controls response page", allowEmptyValue = true)})
    @GetMapping("/users/{id}/posts")
    public ResponseEntity<List<PostDto>> getUserPosts(
            @PathVariable("id") Long id,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        List<PostDto> posts = postService.getAllUserPosts(id, size, page)
                .stream().map(mapper::map).collect(Collectors.toList());
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all user's posts.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PostDto.class)),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Unauthenticated request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @Operation(method = "GET", description = "Get all user's posts",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(name = "size", allowEmptyValue = true, description = "Controls friends list size"),
                    @Parameter(name = "page", description = "Controls response page", allowEmptyValue = true)})
    @GetMapping("/me/posts")
    public ResponseEntity<List<PostDto>> getUserPersonalPosts(
            Authentication auth,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        if (auth == null) throw new UnauthenticatedException("User is unauthenticated");
        final Long userId = Long.parseLong(auth.getName());
        List<PostDto> posts = postService.getAllUserPosts(userId, size, page)
                .stream().map(mapper::map).collect(Collectors.toList());
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New post created",
                    content = @Content(schema = @Schema(implementation = PostDto.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Not authenticated request")
    })
    @Operation(method = "GET", description = "Used to create new post",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/me/posts")
    public ResponseEntity<PostDto> createNewPost(Authentication auth, @RequestBody PostCreationRequest request) {
        final Long userId = Long.parseLong(auth.getName());
        PostModel post = postService.createNewPost(userId, request);
        return new ResponseEntity<>(mapper.map(post), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all followed users' posts.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PostDto.class)),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Unauthenticated request")
    })
    @Operation(method = "GET", description = "Get all followed users' posts",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(name = "size", allowEmptyValue = true, description = "Controls friends list size"),
                    @Parameter(name = "page", description = "Controls response page", allowEmptyValue = true)})
    @GetMapping("/feed")
    public ResponseEntity<List<PostDto>> getAllPosts(
            Authentication auth,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        final Long userId = Long.parseLong(auth.getName());
        Page<PostModel> posts = postService.getPostsFromFollowedUsers(userId, size, page);

        return new ResponseEntity<>(posts.stream().map(mapper::map)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post updated",
                    content = @Content(schema = @Schema(implementation = PostDto.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "No permission to update this post",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @Operation(method = "GET", description = "Used to create new post",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/me/posts")
    public ResponseEntity<PostDto> update(Authentication auth, @RequestBody PostUpdateRequest request) {
        final Long userId = Long.parseLong(auth.getName());
        PostDto post = mapper.map(postService.update(userId, request));
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post deleted",
                    content = @Content(schema = @Schema(implementation = PostDto.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "No permission to update this post",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @Operation(method = "GET", description = "Used to create new post",
            security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/me/posts")
    public ResponseEntity<PostDto> delete(Authentication auth, @RequestBody PostDeleteRequest request) {
        final Long userId = Long.parseLong(auth.getName());
        PostDto post = mapper.map(postService.delete(userId, request));
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
}
