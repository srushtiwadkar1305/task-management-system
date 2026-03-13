package com.taskmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanager.service.BacklogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/backlog")
@RequiredArgsConstructor
public class BacklogController {
    
    @Autowired
    private BacklogService backlogService;

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getBacklog(@PathVariable Long projectId){
        return ResponseEntity.ok(backlogService.getBacklog(projectId));
    }

    @PostMapping("/{projectId}/record")
    public ResponseEntity<?> recordBacklog(
        @PathVariable Long projectId,
        @RequestBody List<Long> issueIds
    ){
        backlogService.recordBacklog(projectId, issueIds);  
        return ResponseEntity.ok("Backlog Recorded");
    }

    @PutMapping("/addIssueToSprint/{issueId}/{sprintId}")
    public ResponseEntity<?> addIssueToSprint(
        @PathVariable Long issueId,
        @PathVariable Long sprintId
    ){
        return ResponseEntity.ok(backlogService.addIssueToSprint(issueId, sprintId));
    }

    @GetMapping("/{projectId}/getBacklogHierarchy")
    public ResponseEntity<?> getBacklogHierarchy(@PathVariable Long projectId){
        return ResponseEntity.ok(backlogService.getBacklogHierarchy(projectId));
    }

}
