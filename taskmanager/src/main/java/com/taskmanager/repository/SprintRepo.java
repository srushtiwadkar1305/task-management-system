package com.taskmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskmanager.entity.Sprint;
import com.taskmanager.enums.SprintState;

@Repository
public interface SprintRepo extends JpaRepository<Sprint, Long> {
    
    public List<Sprint> findByProjectId(Long projectId);

    public List<Sprint> findBySprintState(SprintState sprintState);

}
