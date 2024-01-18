package com.feedo.oauth.jwt.controller;

import com.feedo.oauth.jwt.dto.AuthRequestDto;
import com.feedo.oauth.jwt.dto.JwtResponseDto;
import com.feedo.oauth.jwt.dto.UserRequestDto;
import com.feedo.oauth.jwt.dto.UserResponseDto;
import com.feedo.oauth.jwt.service.JwtService;
import com.feedo.oauth.jwt.service.UserService;
import com.feedo.oauth.jwt.service.impl.UserDetailsServiceImpl;
import com.feedo.oauth.jwt.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AuthController {

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    private final UserServiceImpl userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping(value = "/register")
    public ResponseEntity saveUser(@RequestBody UserRequestDto userRequest) {
        try {
            UserResponseDto userResponse = userService.saveUser(userRequest);
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public JwtResponseDto AuthenticateAndGetToken(@RequestBody AuthRequestDto authRequestDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(), authRequestDto.getPassword()));
        if(authentication.isAuthenticated()){
            return JwtResponseDto.builder()
                    .accessToken(jwtService.GenerateToken(authRequestDto.getUsername())).build();
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

}
