package com.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskmanager.entity.UserProfileUpdate;

@Repository 
public interface UserProfileUpdateRepo extends JpaRepository<UserProfileUpdate, Long> {
    UserProfileUpdate findByEmail(String email);
}
