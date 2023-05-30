package com.burravlev.task.post.controller;

import com.burravlev.task.post.domain.entity.PostModel;
import com.burravlev.task.post.domain.model.PostCreationRequest;
import com.burravlev.task.post.domain.model.PostDto;
import com.burravlev.task.post.service.PostService;
import com.burravlev.task.util.mapper.Mapper;
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
public class PostController {
    private final PostService postService;
    private final Mapper<PostModel, PostDto> mapper;


    @GetMapping("/users/{id}/posts")
    public ResponseEntity<List<PostDto>> getUserPosts(
            @PathVariable("id") Long id,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page)
    {
        List<PostDto> posts = postService.getAllUserPosts(id, size, page)
                .stream().map(mapper::map).collect(Collectors.toList());
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping("/me/posts")
    public ResponseEntity<PostDto> createNewPost(Authentication auth, @RequestBody PostCreationRequest request) {
        final Long userId = Long.parseLong(auth.getName());
        PostModel post = postService.createNewPost(userId, request);
        return new ResponseEntity<>(mapper.map(post), HttpStatus.CREATED);
    }

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
}
