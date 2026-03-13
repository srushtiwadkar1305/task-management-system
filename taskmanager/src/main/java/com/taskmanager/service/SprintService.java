package com.taskmanager.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taskmanager.entity.Issue;
import com.taskmanager.entity.Sprint;
import com.taskmanager.enums.IssueStatus;
import com.taskmanager.enums.SprintState;
import com.taskmanager.repository.IssueRepo;
import com.taskmanager.repository.SprintRepo;

@Service
public class SprintService {
    
    @Autowired
    private SprintRepo sprintRepo;

    @Autowired
    private IssueRepo issueRepo;

    public Sprint createSprint(Sprint sprint){
        sprint.setSprintState(SprintState.PLANNED);
        return sprintRepo.save(sprint);
    }

    @Transactional
    public Issue assignIssueToSprint(Long sprintId, Long issueId){

        Sprint sprint = sprintRepo.findById(sprintId)
            .orElseThrow(() -> new RuntimeException("Sprint not found"));

        Issue issue = issueRepo.findById(issueId)
            .orElseThrow(() -> new RuntimeException("Issue not found"));

        if(sprint.getSprintState() == SprintState.COMPLETED){
            throw new RuntimeException("cannot add task to completed sprint");
        }

        issue.setSprintId(sprintId);

        return issueRepo.save(issue);

    }

    @Transactional
    public Sprint startSprint(Long sprintId){
     
        Sprint sprint = sprintRepo.findById(sprintId)
            .orElseThrow(() -> new RuntimeException("Sprint not found"));

        if(sprint.getSprintState() == SprintState.PLANNED){
            throw new RuntimeException("spring is not started yet.");
        }

        sprint.setSprintState(SprintState.ACTIVE);

        if(sprint.getStartDate() == null){
            sprint.setStartDate(LocalDateTime.now());
        }

        return sprintRepo.save(sprint);

    }

    @Transactional
    public Sprint completeSprint(Long sprintId){

        Sprint sprint = sprintRepo.findById(sprintId)
            .orElseThrow(() -> new RuntimeException("Sprint not found"));

        sprint.setSprintState(SprintState.COMPLETED);
        sprint.setEndDate(LocalDateTime.now());

        List<Issue> issues = issueRepo.findBySprintId(sprintId);

        for(Issue i: issues){
            if(i.getIssueStatus() != IssueStatus.DONE){
                i.setSprintId(null);
                issueRepo.save(i);
            }
        }

        return sprintRepo.save(sprint);

    }

    public Map<String, Object> getBurnDownData(Long sprintId){

        Sprint sprint = sprintRepo.findById(sprintId)
            .orElseThrow(() -> new RuntimeException("Sprint not found"));

        LocalDateTime startDate = sprint.getStartDate();
        LocalDateTime endDate = sprint.getEndDate() != null? sprint.getEndDate() : LocalDateTime.now();

        List<Issue> issues = issueRepo.findBySprintId(sprintId);

        Integer totalTasks = issues.size();

        Map<String, Object> chart = new LinkedHashMap<>();

        LocalDateTime cursor = startDate;

        while(cursor.isAfter(endDate)){

            int complete = (int)issues.stream().filter(i -> i.getIssueStatus() == IssueStatus.DONE).count();

            chart.put(cursor.toString(), totalTasks - complete);

            cursor = cursor.plusDays(1);

        }

        Map<String, Object> response = new HashMap<>();
        response.put("sprintId", sprintId);
        response.put("startDate", startDate);
        response.put("endDate", endDate);
        response.put("BurnDown", chart);

        return response;
    }

}
