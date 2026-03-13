package com.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskmanager.entity.Board;

@Repository
public interface BoardRepo extends JpaRepository<Board, Long> {

    Board findByProjectKey(String projectKey);

}   
