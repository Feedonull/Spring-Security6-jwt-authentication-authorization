package com.feedo.oauth.jwt.service.impl;

import com.feedo.oauth.jwt.dto.UserRequestDto;
import com.feedo.oauth.jwt.dto.UserResponseDto;
import com.feedo.oauth.jwt.model.UserInfo;
import com.feedo.oauth.jwt.repository.UserRepository;
import com.feedo.oauth.jwt.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    ModelMapper modelMapper = new ModelMapper();
    @Override
    public UserResponseDto saveUser(UserRequestDto userRequestDto) {
        if(userRequestDto.getUsername() == null){
            throw new RuntimeException("Parameter username is not found in request..!!");
        } else if(userRequestDto.getPassword() == null){
            throw new RuntimeException("Parameter password is not found in request..!!");
        }
        UserInfo savedUser = null;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = userRequestDto.getPassword();
        String encodedPassword = encoder.encode(rawPassword);

        UserInfo user = modelMapper.map(userRequestDto, UserInfo.class);
        user.setPassword(encodedPassword);
        if(userRequestDto.getId() != null){
            UserInfo oldUser = userRepository.findFirstById(userRequestDto.getId());
            if(oldUser != null){
                oldUser.setId(user.getId());
                oldUser.setPassword(user.getPassword());
                oldUser.setUserName(user.getUserName());
                oldUser.setRoles(user.getRoles());

                savedUser = userRepository.saveAndFlush(oldUser);
            } else {
                throw new RuntimeException("Can't find record with identifier: " + userRequestDto.getId());
            }
        } else {
//            user.setCreatedBy(currentUser);
            savedUser = userRepository.saveAndFlush(user);
        }
        UserResponseDto userResponseDto = modelMapper.map(savedUser, UserResponseDto.class);
        return userResponseDto;
    }

    @Override
    public UserResponseDto getUser() {
        return null;
    }

    @Override
    public List<UserResponseDto> getAllUser() {
        return null;
    }
}
