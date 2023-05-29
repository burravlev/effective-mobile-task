package com.burravlev.task.files.domain.mapper;

import com.burravlev.task.files.domain.entity.Image;
import com.burravlev.task.files.domain.model.ImageModel;
import com.burravlev.task.util.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class ImageDtoMapper implements Mapper<Image, ImageModel> {

    @Override
    public ImageModel map(Image image) {
        return ImageModel.builder()
                .id(image.getId())
                .filename(image.getFilename())
                .contentType(image.getType().getType())
                .build();
    }
}
