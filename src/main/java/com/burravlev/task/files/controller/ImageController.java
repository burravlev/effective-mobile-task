package com.burravlev.task.files.controller;

import com.burravlev.task.files.domain.entity.Image;
import com.burravlev.task.files.domain.model.ImageModel;
import com.burravlev.task.files.service.ImageStorageService;
import com.burravlev.task.util.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageStorageService imageStorageService;
    private final Mapper<Image, ImageModel> mapper;

    @PostMapping("/upload")
    public ResponseEntity<List<ImageModel>> upload(@RequestPart("files") List<MultipartFile> files) {
        List<Image> image = imageStorageService.save(files);
        return new ResponseEntity<>(image.stream().map(mapper::map)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageModel> get(@PathVariable("id") Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
