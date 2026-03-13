package com.taskmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanager.entity.UserProfileUpdate;
import com.taskmanager.enums.Role;
import com.taskmanager.service.UserProfileUpdateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profile_update")
@RequiredArgsConstructor
public class UserProfileUpdateController {

    @Autowired
    private UserProfileUpdateService userProfileUpdateService;

    @PutMapping("/updateProfile")
    public ResponseEntity<String> updateUserProfile(@RequestBody UserProfileUpdate userProfileUpdate){
        userProfileUpdateService.updateUserProfile(userProfileUpdate);
        return ResponseEntity.ok("User Profile Saved");
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserProfileUpdate>> getAllUserProfiles(){
        return ResponseEntity.ok(userProfileUpdateService.getAllUserProfiles());
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserProfileUpdate> getUserProfileByEmail(@PathVariable String email){
        return ResponseEntity.ok(userProfileUpdateService.getUserProfileByEmail(email));
    }

    @PutMapping("/updateUserRole")
    public ResponseEntity<String> updateUserRole(
        @RequestParam String email,
        @RequestParam Role newRole
    ){
        userProfileUpdateService.updateUserRole(email, newRole);
        return ResponseEntity.ok("User Role Updated");
    }

}
