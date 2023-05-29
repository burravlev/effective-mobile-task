package com.burravlev.task.content.controller;

import com.burravlev.task.content.domail.dto.UploadResponse;
import com.burravlev.task.content.exception.UnsupportedContentType;
import com.burravlev.task.content.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ContentController {
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

    @PostMapping("/upload")
    public ResponseEntity<UploadResponse> upload(MultipartFile file) {
        if (!"image/jpeg".equals(file.getContentType())) {
            throw new UnsupportedContentType("Only image/jpeg");
        }
        return new ResponseEntity<>(new UploadResponse(), HttpStatus.OK);
    }
}
