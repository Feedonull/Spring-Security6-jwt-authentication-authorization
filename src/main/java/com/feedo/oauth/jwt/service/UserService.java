package com.feedo.oauth.jwt.service;

import com.feedo.oauth.jwt.dto.UserRequestDto;
import com.feedo.oauth.jwt.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    UserResponseDto saveUser(UserRequestDto userRequestDto);

    UserResponseDto getUser();

    List<UserResponseDto> getAllUser();

}
