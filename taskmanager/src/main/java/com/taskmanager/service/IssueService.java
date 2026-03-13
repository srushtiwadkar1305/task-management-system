package com.taskmanager.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taskmanager.dto.IssueCommentDto;
import com.taskmanager.dto.IssueDto;
import com.taskmanager.entity.Issue;
import com.taskmanager.entity.IssueComment;
import com.taskmanager.entity.Sprint;
import com.taskmanager.repository.IssueCommentRepo;
import com.taskmanager.repository.IssueRepo;
import com.taskmanager.repository.SprintRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueService {
    
    @Autowired
    private IssueRepo issueRepo;

    @Autowired
    private IssueCommentRepo issueCommentRepo;

    @Autowired
    private SprintRepo sprintRepo;

    private String generateIssueKey(Long issueId){
        return "PROJECT_" + issueId;
    }

    @Transactional
    public IssueDto createIssue(IssueDto issueDto){

        Issue issue = new Issue();

        issue.setIssueKey(generateIssueKey(issue.getIssueId()));
        issue.setIssueTitle(issueDto.getIssueTitle());
        issue.setIssueDescription(issueDto.getIssueDescription());
        issue.setIssueType(issueDto.getIssueType());
        issue.setIssuePriority(issueDto.getIssuePriority());
        issue.setIssueStatus(issueDto.getIssueStatus());
        issue.setAssignedByEmail(issueDto.getAssignedByEmail());
        issue.setReportToEmail(issueDto.getReportToEmail());
        issue.setDueDate(issueDto.getDueDate());

        issueRepo.save(issue);

        return convertIssueToDto(issue);

    }

    @Transactional
    public IssueComment addComment(
        IssueCommentDto issueCommentDto
    ){

        issueRepo.findById(issueCommentDto.issueId)
            .orElseThrow(() -> new RuntimeException("Issue not found"));

        IssueComment issueComment = new IssueComment();

        issueComment.setIssueId(issueCommentDto.issueId);
        issueComment.setAuthorEmail(issueCommentDto.authorEmail);
        issueComment.setBody(issueCommentDto.body);

        return issueCommentRepo.save(issueComment);

    }

    public IssueDto getByIssueBy(Long issueId){

        Issue issue = issueRepo.findById(issueId)
            .orElseThrow(() -> new RuntimeException("Issue not found"));

        return convertIssueToDto(issue);

    }

    public List<IssueDto> getByAssignedByEmail(String email){
        
        return issueRepo.findByAssignedByEmail(email)
            .stream().map(this::convertIssueToDto)
            .collect(Collectors.toList());

    }

    public List<IssueDto> getBySprintId(Long sprintId){

        return issueRepo.findBySprintId(sprintId)
            .stream().map(this::convertIssueToDto)
            .collect(Collectors.toList());

    }

    @Transactional
    public IssueDto updateIssueStatus(IssueDto issueDto){
        Issue issue = issueRepo.findById(issueDto.issueId)
            .orElseThrow(() -> new RuntimeException("Issue not found"));

        issue.setIssueStatus(issueDto.issueStatus);
        issue.setUpdatedAt(LocalDateTime.now());

        issueRepo.save(issue);

        return convertIssueToDto(issue);

    }

    @Transactional
    public Sprint createSprint(Sprint sprint){
        return sprintRepo.save(sprint);
    }

    public List<IssueDto> searchIssues(
        Map<String, String> filterOptions
    ){
        if(filterOptions.containsKey("assignedByEmail")){
            return getByAssignedByEmail(filterOptions.get("assignedByEmail"));
        }

        if(filterOptions.containsKey("sprintId")){
            return getBySprintId(Long.valueOf(filterOptions.get("sprintId")));
        }

        if(filterOptions.containsKey("issueStatus")){
            
            return issueRepo.findByIssueStatus(filterOptions.get("issueStatus"))
                .stream().map(this::convertIssueToDto)
                .collect(Collectors.toList());

        }

        return issueRepo.findAll()
            .stream().map(this::convertIssueToDto)
            .collect(Collectors.toList());

    }



    private IssueDto convertIssueToDto(Issue issue){

        IssueDto issueDto = new IssueDto();

        issueDto.setIssueId(issue.getIssueId());

        issueDto.setIssueTitle(issue.getIssueTitle());

        issueDto.setIssueKey(issue.getIssueKey());

        issueDto.setIssueDescription(issue.getIssueDescription());

        issueDto.setIssueType(issue.getIssueType());

        issueDto.setIssueStatus(issue.getIssueStatus());

        issueDto.setIssuePriority(issue.getIssuePriority());

        issueDto.setAssignedByEmail(issue.getAssignedByEmail());

        issueDto.setReportToEmail(issue.getReportToEmail());

        issueDto.setCreatedAt(issue.getCreatedAt());

        issueDto.setUpdatedAt(issue.getUpdatedAt());

        issueDto.setDueDate(issue.getDueDate());

        issueDto.setSprintId(issue.getSprintId());

        issueDto.setEpicId(issue.getEpicId());

        return issueDto;

    }

}
