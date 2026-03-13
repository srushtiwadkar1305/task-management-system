package com.taskmanager.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanager.dto.IssueCommentDto;
import com.taskmanager.dto.IssueDto;
import com.taskmanager.service.IssueService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueController {
    
    @Autowired
    private IssueService issueService;

    @PostMapping("/create")
    public ResponseEntity<String> createIssue(@RequestBody IssueDto issueDto){
        issueService.createIssue(issueDto);
        return ResponseEntity.ok("Issue Created");
    } 

    @GetMapping("/{issueId}")
    public ResponseEntity<?> getByIssueId(@PathVariable Long issueId){
        return ResponseEntity.ok(issueService.getByIssueBy(issueId));
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getByAssignedByEmail(@PathVariable String email){
        return ResponseEntity.ok(issueService.getByAssignedByEmail(email));
    }

    @PutMapping("/addComment")
    public ResponseEntity<?> addIssueComment(@RequestBody IssueCommentDto issueCommentDto){
        return ResponseEntity.ok(issueService.addComment(issueCommentDto));
    }

    @GetMapping("/{sprintId}")
    public ResponseEntity<?> getBySprintId(@PathVariable Long sprintId){
        return ResponseEntity.ok(issueService.getBySprintId(sprintId));
    }

    @PutMapping("/updateIssueStatus")
    public ResponseEntity<?> updateIssueStatus(@RequestBody IssueDto issueDto){
        return ResponseEntity.ok(issueService.updateIssueStatus(issueDto));
    }

    @GetMapping("/searchIssues")
    public ResponseEntity<?> searchIssues(@RequestBody Map<String, String> filterOptions){
        return ResponseEntity.ok(issueService.searchIssues(filterOptions));
    }

}
