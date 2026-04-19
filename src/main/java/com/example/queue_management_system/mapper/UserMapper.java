package com.example.queue_management_system.mapper;

import com.example.queue_management_system.domain.User;
import com.example.queue_management_system.dto.UserResponse;

public class UserMapper {
    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
