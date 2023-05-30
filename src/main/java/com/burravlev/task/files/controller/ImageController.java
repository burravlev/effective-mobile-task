package com.burravlev.task.files.controller;

import com.burravlev.task.files.domain.entity.ImageEntity;
import com.burravlev.task.files.domain.model.ImageModel;
import com.burravlev.task.files.service.ImageStorageService;
import com.burravlev.task.util.mapper.Mapper;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
@Tag(name = "Images API")
public class ImageController {
    private final ImageStorageService imageStorageService;
    private final Mapper<ImageEntity, ImageModel> mapper;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully uploaded images.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ImageModel.class)),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Unauthenticated request")
    })
    @Operation(method = "POST", description = "Upload multipart/form-data",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/upload")
    public ResponseEntity<List<ImageModel>> upload(@RequestPart("files") List<MultipartFile> files) {
        List<ImageEntity> image = imageStorageService.save(files);
        return new ResponseEntity<>(image.stream().map(mapper::map)
                .collect(Collectors.toList()), HttpStatus.OK);
    }
}
