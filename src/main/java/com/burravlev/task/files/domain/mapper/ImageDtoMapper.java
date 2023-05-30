package com.burravlev.task.files.domain.mapper;

import com.burravlev.task.files.domain.entity.ImageEntity;
import com.burravlev.task.files.domain.model.ImageModel;
import com.burravlev.task.util.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class ImageDtoMapper implements Mapper<ImageEntity, ImageModel> {

    @Override
    public ImageModel map(ImageEntity image) {
        return ImageModel.builder()
                .id(image.getId())
                .url(image.getUrl())
                .contentType(image.getType().getType())
                .build();
    }
}
