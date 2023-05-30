package com.burravlev.task.post.util.mapper;

import com.burravlev.task.files.domain.entity.ImageEntity;
import com.burravlev.task.files.domain.model.ImageModel;
import com.burravlev.task.post.domain.entity.PostEntity;
import com.burravlev.task.post.domain.model.PostModel;
import com.burravlev.task.util.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostDtoMapper implements Mapper<PostEntity, PostModel> {
    private final Mapper<ImageEntity, ImageModel> mapper;

    @Override
    public PostModel map(PostEntity postModel) {
        return PostModel.builder()
                .id(postModel.getId())
                .ownerId(postModel.getCreator().getId())
                .header(postModel.getHeader())
                .message(postModel.getMessage())
                .content(postModel.getContent().stream().map(ImageEntity::getUrl)
                        .collect(Collectors.toList()))
                .created(postModel.getCreated())
                .build();
    }
}
