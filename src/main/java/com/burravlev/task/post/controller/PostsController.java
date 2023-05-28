package com.burravlev.task.post.controller;

import com.burravlev.task.post.domain.dto.PostDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PostsController {


    @GetMapping("/feed")
    public ResponseEntity<List<PostDto>> getAll(Authentication auth) {
        return null;
    }

    @GetMapping("/{id}/feed")
    public ResponseEntity<List<PostDto>> getAllUserPosts(@PathVariable("id") Long id) {
        return null;
    }
}
