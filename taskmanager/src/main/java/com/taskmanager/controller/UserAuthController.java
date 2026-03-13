package com.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanager.dto.AuthResponseDto;
import com.taskmanager.dto.ForgotPasswordDto;
import com.taskmanager.dto.LoggedRequestDto;
import com.taskmanager.dto.LoginRequestDto;
import com.taskmanager.dto.RegisterRequestDto;
import com.taskmanager.dto.ResetPasswordDto;
import com.taskmanager.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/Authentication")
@RequiredArgsConstructor
public class UserAuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody RegisterRequestDto requestData){

        try {
            
            Boolean isUserCreated = userService.register(requestData);

            if(!isUserCreated){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(true);


        } catch (Exception e) {
            
            System.out.println("Error at register: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);

        }

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto loginData){

        try {
            
            AuthResponseDto authResponseDto = userService.login(loginData);

            return ResponseEntity.status(HttpStatus.CREATED).body(authResponseDto);

        } catch (Exception e) {

            System.out.println("Error at login: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }

    }

    @PostMapping("/forgot_password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto){

        userService.forgotPassword(forgotPasswordDto.email);  

        return ResponseEntity.ok("Reset password email sent to your registered email address");

    }

    @PostMapping("/reset_password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto){

        userService.resetPassword(resetPasswordDto.token, resetPasswordDto.newPassword);

        return ResponseEntity.ok("Your password is changed. You can now login.");

    }

    @PostMapping("/loggedOut")
    public ResponseEntity<String> loggedOut(
        @RequestBody LoggedRequestDto loggedRequestDto,
        @RequestHeader("Authorization") String authHeader 
    ){
        //String token = authHeader.substring(7);

        userService.loggedout(loggedRequestDto);

        return ResponseEntity.ok("Logout Successful."); 

    }

}
