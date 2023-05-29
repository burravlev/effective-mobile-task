package com.burravlev.task.post.util.mapper;

import com.burravlev.task.files.domain.entity.Image;
import com.burravlev.task.post.domain.entity.PostModel;
import com.burravlev.task.post.domain.model.PostDto;
import com.burravlev.task.util.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PostDtoMapper implements Mapper<PostModel, PostDto> {
    @Override
    public PostDto map(PostModel postModel) {
        return PostDto.builder()
                .id(postModel.getId())
                .createdBy(postModel.getCreator().getId())
                .header(postModel.getHeader())
                .message(postModel.getMessage())
                .content(postModel.getContent().stream().map(Image::getId)
                        .collect(Collectors.toList()))
                .created(postModel.getCreated())
                .build();
    }
}
