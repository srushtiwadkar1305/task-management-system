package com.taskmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskmanager.entity.WorkFlowTransaction;
import com.taskmanager.enums.IssueStatus;

@Repository
public interface WorkFlowTransactionRepo extends JpaRepository<WorkFlowTransaction, Long> {
    
    List<WorkFlowTransaction> findByWorkFlow_WorkFlowId(Long workFlowId);

    List<WorkFlowTransaction> findByWorkFlow_WorkFlowIdAndFromIssueStatus(
        Long workFlowId, 
        IssueStatus fromIssueStatus
    );

}
