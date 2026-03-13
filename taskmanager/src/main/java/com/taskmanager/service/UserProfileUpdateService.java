package com.taskmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanager.entity.UserProfileUpdate;
import com.taskmanager.enums.Role;
import com.taskmanager.repository.UserProfileUpdateRepo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileUpdateService {

    @Autowired
    UserProfileUpdateRepo userProfileUpdateRepo;

    public String updateUserProfile(UserProfileUpdate userProfileUpdate){
        userProfileUpdateRepo.save(userProfileUpdate);
        return "User Profile Update"; 
    }

    public List<UserProfileUpdate> getAllUserProfiles(){
        return userProfileUpdateRepo.findAll();
    }

    public UserProfileUpdate getUserProfileByEmail(String email){
        return userProfileUpdateRepo.findByEmail(email); 
    }

    public UserProfileUpdate updateUserRole(String email, Role newRole){
        
        UserProfileUpdate user = userProfileUpdateRepo.findByEmail(email);

        if(user == null){
            throw new RuntimeException("User not found");
        }

        user.setRole(newRole);

        return userProfileUpdateRepo.save(user);

    }

}
