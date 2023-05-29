package com.burravlev.task.content.controller;

import com.burravlev.task.content.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/img")
@RequiredArgsConstructor
public class ImageController {
    private final FileStorageService fileStorageService;

    @GetMapping("/{image}")
    public ResponseEntity<Resource> get(@PathVariable("image") String imageName) {
        ByteArrayResource resource = fileStorageService.get(imageName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(resource.contentLength())
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }
}
