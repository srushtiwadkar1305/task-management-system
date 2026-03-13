package com.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskmanager.entity.TokenBlockList;

@Repository 
public interface TokenBlockListRepo extends JpaRepository<TokenBlockList, Long> {
    boolean existsByToken(String token); 
}
