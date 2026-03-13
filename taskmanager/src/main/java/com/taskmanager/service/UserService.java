package com.taskmanager.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskmanager.dto.AuthResponseDto;
import com.taskmanager.dto.LoggedRequestDto;
import com.taskmanager.dto.LoginRequestDto;
import com.taskmanager.dto.RegisterRequestDto;
import com.taskmanager.entity.TokenBlockList;
import com.taskmanager.entity.User;
import com.taskmanager.enums.Role;
import com.taskmanager.repository.TokenBlockListRepo;
import com.taskmanager.repository.UserRepo;
import com.taskmanager.security.JwtUtil;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class UserService {
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EmailService emailService;

    @Autowired 
    private TokenBlockListRepo tokenBlockListRepo;

    public boolean register(RegisterRequestDto requestData){

        User isUserExists = userRepo.findByEmail(requestData.email);
        if(isUserExists != null){
            return false;
        }

        User user = new User();
        user.setEmail(requestData.email);
        user.setPassword(passwordEncoder.encode(requestData.password));
        user.setRole(Role.valueOf(requestData.role.name()));
        user.setUsername(requestData.username);

        userRepo.save(user);

        return true;

    }

    public AuthResponseDto login(LoginRequestDto loginData){

        AuthResponseDto authResponseDto = new AuthResponseDto();

        User user = userRepo.findByEmail(loginData.email);
        if(user == null){

            authResponseDto.setToken(null);
            authResponseDto.setMessage("User does not exists");

        } else if(!passwordEncoder.matches(loginData.password, user.getPassword())){

            authResponseDto.setToken(null);
            authResponseDto.setMessage("Password is incorrect");

        } else {

            String token = jwtUtil.generateToken(user);

            authResponseDto.setToken(token);    
            authResponseDto.setMessage("Login Successful");  

        }

        return authResponseDto;

    }

    public void forgotPassword(String email){

        User user = userRepo.findByEmail(email);

        if(user == null){
            throw new RuntimeException("User not found");
        }

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiration(LocalDateTime.now().plusMinutes(10));

        userRepo.save(user);

        emailService.sendResetPasswordMail(email, token);

    }

    public void resetPassword(String token, String newPassword){

        User user = userRepo.findByResetToken(token);

        if(user == null){
            throw new RuntimeException("User not found");
        }

        if (user.getResetTokenExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Reset token expired");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);  
        user.setResetTokenExpiration(null); 
        userRepo.save(user);

    }

    public void loggedout(LoggedRequestDto loggedRequestDto){

        Claims claims = jwtUtil.getClaims(loggedRequestDto.token); 

        TokenBlockList tokenBlockList = new TokenBlockList();
        tokenBlockList.setToken(loggedRequestDto.token); 
        tokenBlockList.setExpiry(claims.getExpiration()); 

        tokenBlockListRepo.save(tokenBlockList);  

    }

}
