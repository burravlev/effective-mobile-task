package com.burravlev.task.files.controller;

import com.burravlev.task.files.domain.model.ImageModel;
import com.burravlev.task.files.service.ImageStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Files API")
public class FilesController {
    private final ImageStorageService storageService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image png.",
                    content = @Content(mediaType = "image/jpeg")),
            @ApiResponse(responseCode = "200", description = "Image jpeg.",
                    content = @Content(mediaType = "image/png")),
            @ApiResponse(responseCode = "404", description = "Image doesn't exists")
    })
    @Operation(method = "GET", description = "Get image",
            security = @SecurityRequirement(name = "bearerAuth"))
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
