package com.burravlev.task.user.mapper;

import com.burravlev.task.user.domain.model.UserModel;
import com.burravlev.task.user.domain.entity.UserEntity;
import com.burravlev.task.util.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper implements Mapper<UserEntity, UserModel> {
    @Override
    public UserModel map(UserEntity userModel) {
        return UserModel.builder()
                .id(userModel.getId())
                .username(userModel.getPublicUsername())
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .email(userModel.getEmail())
                .build();
    }
}
