package com.burravlev.task.files.controller;

import com.burravlev.task.files.service.ImageStorageService;
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
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FilesController {
    private final ImageStorageService storageService;

    @GetMapping("/img/{name}")
    public ResponseEntity<Resource> get(@PathVariable("name") String name) {
        ByteArrayResource resource = storageService.getResource(name);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(name.endsWith(".jpg") ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG)
                .contentLength(resource.contentLength())
                .body(resource);
    }
}
