package com.burravlev.task.user.mapper;

import com.burravlev.task.user.dto.UserDto;
import com.burravlev.task.user.model.UserModel;
import com.burravlev.task.util.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper implements Mapper<UserModel, UserDto> {
    @Override
    public UserDto map(UserModel userModel) {
        return UserDto.builder()
                .id(userModel.getId())
                .username(userModel.getUsername())
                .email(userModel.getEmail())
                .build();
    }
}
