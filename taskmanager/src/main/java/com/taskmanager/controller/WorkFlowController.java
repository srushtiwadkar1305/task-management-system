package com.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanager.entity.WorkFlow;
import com.taskmanager.service.WorkFlowService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/workFlows")
@RequiredArgsConstructor
public class WorkFlowController {
    
    @Autowired
    private WorkFlowService workFlowService;

    @PostMapping("/create")
    public ResponseEntity<?> createWorkFlow(@RequestBody WorkFlow workFlow){

        WorkFlow newWorkFlow = workFlowService.createWorkFlow(workFlow);

        return ResponseEntity.ok(newWorkFlow);

    }

    @PutMapping("/updateWorkFlow/{id}")
    public ResponseEntity<?> updateWorkFlow(
        @PathVariable Long id, 
        @RequestBody WorkFlow workFlow
    ){

        WorkFlow updatedWorkFlow = workFlowService.updateWorkFlow(id, workFlow);

        return ResponseEntity.ok(updatedWorkFlow);

    }

    @GetMapping("/{workFlowId}")
    public ResponseEntity<?> getWorkFlowById(@PathVariable Long workFlowId){

        WorkFlow workFlow = workFlowService.getWorkFlowById(workFlowId);

        return ResponseEntity.ok(workFlow);

    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllWorkFlows(){

        return ResponseEntity.ok(workFlowService.getAllWorkFlows());

    }

    @DeleteMapping("/delete/{workFlowId}")
    public ResponseEntity<?> deleteWorkFlow(@PathVariable Long workFlowId){

        workFlowService.deleteWorkFlow(workFlowId);

        return ResponseEntity.ok("WorkFlow deleted");

    }

}
