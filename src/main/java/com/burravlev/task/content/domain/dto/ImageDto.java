package com.burravlev.task.content.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDto {
    @Schema(name = "id", example = "1", description = "Media id for adding it to messages or posts")
    private Long id;
    @Schema(name = "url", example = "/api/img/filename.jpg", description = "Url to access image file")
    private String url;
}
