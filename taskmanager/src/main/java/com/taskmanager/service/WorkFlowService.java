package com.taskmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taskmanager.entity.WorkFlow;
import com.taskmanager.entity.WorkFlowTransaction;
import com.taskmanager.repository.WorkFlowRepo;
import com.taskmanager.repository.WorkFlowTransactionRepo;

@Service
public class WorkFlowService {
    
    @Autowired
    private WorkFlowRepo workFlowRepo;

    @Autowired
    private WorkFlowTransactionRepo WorkFlowTransactionRepo;

    @Transactional
    public WorkFlow createWorkFlow(WorkFlow workFlow){

        if (workFlow.getWorkFlowTransaction() != null) {
            workFlow.getWorkFlowTransaction()
                .forEach(tx -> tx.setWorkFlow(workFlow));
        }

        return workFlowRepo.save(workFlow);
    }

    @Transactional
    public WorkFlow updateWorkFlow(Long workFlowId, WorkFlow updatedWorkFlow){

        WorkFlow workFlow = getWorkFlowById(workFlowId);

        workFlow.setWorkFlowName(updatedWorkFlow.getWorkFlowName()); 

        workFlow.setWorkFlowDescription(updatedWorkFlow.getWorkFlowDescription()); 

        workFlow.setCreatedAt(updatedWorkFlow.getCreatedAt()); 

        if (updatedWorkFlow.getWorkFlowTransaction() != null) {
            
            for(WorkFlowTransaction wtf: updatedWorkFlow.getWorkFlowTransaction()){
                wtf.setWorkFlow(workFlow);
                workFlow.getWorkFlowTransaction().add(wtf);
            }

        }

        return workFlowRepo.save(workFlow);

    }

    public WorkFlow getWorkFlowByName(String workFlowName){
        return workFlowRepo.findByWorkFlowName(workFlowName);
    }

    public List<WorkFlow> getAllWorkFlows(){
        return workFlowRepo.findAll();
    }

    public WorkFlow getWorkFlowById(Long workFlowId){

        return workFlowRepo.findById(workFlowId)
            .orElseThrow(() -> new RuntimeException("WorkFlow not found"));

    }

    public void deleteWorkFlow(Long workFlowId){
        WorkFlow workFlow = getWorkFlowById(workFlowId);
        workFlowRepo.delete(workFlow);
    }

}
