package com.taskmanager.dto;

import com.taskmanager.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDto {
        
    public String username;

    public String password;

    public String email;

    public Role role;

}
