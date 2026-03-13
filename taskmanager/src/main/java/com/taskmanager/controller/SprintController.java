package com.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanager.entity.Sprint;
import com.taskmanager.service.SprintService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sprints")
@RequiredArgsConstructor
public class SprintController {
    
    @Autowired
    private SprintService sprintService;

    @PostMapping("/create")
    public ResponseEntity<?> createSprint(@RequestBody Sprint sprint){
        return ResponseEntity.ok(sprintService.createSprint(sprint));
    }

    @PutMapping("/assign/{sprintId}/{issueId}")
    public ResponseEntity<?> assignIssue(
        @PathVariable Long sprintId,
        @PathVariable Long issueId
    ){
        return ResponseEntity.ok(sprintService.assignIssueToSprint(sprintId, issueId));
    }

    @PutMapping("/{sprintId}/start")
    public ResponseEntity<?> startSprint(@PathVariable Long sprintId){
        return ResponseEntity.ok(sprintService.startSprint(sprintId));
    }

    @PutMapping("/{sprintId}/complete")
    public ResponseEntity<?> completeSprint(@PathVariable Long sprintId){
        return ResponseEntity.ok(sprintService.completeSprint(sprintId));
    }

    @GetMapping("/{sprintId}/burnDown")
    public ResponseEntity<?> getBurnDown(@PathVariable Long sprintId){
        return ResponseEntity.ok(sprintService.getBurnDownData(sprintId));
    }

}
