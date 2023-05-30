package com.burravlev.task.files.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageEntity     {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;
    @Enumerated(EnumType.STRING)
    private ImageType type;

    @Getter
    public enum ImageType {
        JPEG("image/jpeg", ".jpg"), PNG("image/png", ".png");

        private final String type, extension;

        ImageType(String type, String extension) {
            this.type = type;
            this.extension = extension;
        }
    }
}
