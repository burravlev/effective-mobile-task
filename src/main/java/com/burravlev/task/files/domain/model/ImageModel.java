package com.burravlev.task.files.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageModel {
    @Schema(name = "id", description = "Image id to add to message or post", type = "long", example = "100")
    private Long id;
    @Schema(name = "url", description = "Static file url", example = "08b948ac-d024-4bb1-a97b-5713e6484f08")
    private String url;
    @Schema(name = "content_type", description = "Content type", example = "image/jpeg")
    @JsonProperty("content_type")
    private String contentType;
}
