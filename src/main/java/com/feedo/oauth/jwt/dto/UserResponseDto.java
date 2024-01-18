package com.feedo.oauth.jwt.dto;

import com.feedo.oauth.jwt.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponseDto {
    private Long id;
    private String username;
    private Set<UserRole> roles;
}
