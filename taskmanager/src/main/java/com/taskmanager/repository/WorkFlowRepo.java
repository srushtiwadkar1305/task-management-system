package com.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskmanager.entity.WorkFlow;

@Repository
public interface WorkFlowRepo extends JpaRepository<WorkFlow, Long> {
    
    WorkFlow findByWorkFlowName(String workFlowName);

}
