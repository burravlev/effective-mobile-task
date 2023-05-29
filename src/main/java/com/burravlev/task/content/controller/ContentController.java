package com.burravlev.task.content.controller;

import com.burravlev.task.content.domain.dto.ImageDto;
import com.burravlev.task.content.domain.model.Image;
import com.burravlev.task.content.exception.UnsupportedContentType;
import com.burravlev.task.content.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ContentController {
    private final FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<ImageDto> upload(@RequestParam("file") MultipartFile file) {
        if (!"image/jpeg".equals(file.getContentType())) {
            throw new UnsupportedContentType("Only image/jpeg");
        }
        Image image = fileStorageService.save(file);
        return new ResponseEntity<>(ImageDto.builder()
                .id(image.getId())
                .url(image.getUrl())
                .build(), HttpStatus.OK);
    }
}
