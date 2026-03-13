package com.taskmanager.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taskmanager.entity.Issue;
import com.taskmanager.entity.Sprint;
import com.taskmanager.enums.IssueType;
import com.taskmanager.enums.SprintState;
import com.taskmanager.repository.IssueRepo;
import com.taskmanager.repository.SprintRepo;

@Service
public class BacklogService {

    @Autowired
    private SprintRepo sprintRepo;

    @Autowired
    private IssueRepo issueRepo;

    public List<Issue> getBacklog(Long projectId){
        return issueRepo.findByProjectIdAndSprintIdIsNullOrderByBacklogPosition(projectId);
    }

    @Transactional
    public void recordBacklog(Long projectId, List<Long> ordereIssueIds){
        int position=0;
        for(Long issueId: ordereIssueIds){
            Issue issue = issueRepo.findById(issueId).orElseThrow(() -> new RuntimeException("Issue not found"));
            issue.setBacklogPosition(position++);
            issueRepo.save(issue);
        }

    }

    @Transactional
    public Issue addIssueToSprint(Long issueId, Long sprintId){
        Issue issue = issueRepo.findById(issueId).orElseThrow(() -> new RuntimeException("Issue not found"));        

        Sprint sprint = sprintRepo.findById(sprintId).orElseThrow(() -> new RuntimeException("Sprint not found"));

        SprintState sprintState = sprint.getSprintState(); 

        if(!sprintState.equals(SprintState.PLANNED) && !sprintState.equals(SprintState.ACTIVE)){
            throw new RuntimeException("Cannot add issue to sprint in state: " + sprintState);
        }

        issue.setSprintId(sprintId);
        issue.setBacklogPosition(null);

        return issueRepo.save(issue);

    }

    public Map<String, Object> getBacklogHierarchy(Long projectId){
        
        List<Issue> backlogs = getBacklog(projectId); 

        Map<Long, Map<String, Object>> epicMap = new HashMap<>();

        Map<Long, Issue> storyMap = new HashMap<>();

        for(Issue issue: backlogs){
            if(issue.getIssueType() == IssueType.EPIC){
                Map<String, Object> epicData = new HashMap<>();
                epicData.put("epic", issue);
                epicData.put("stories", new ArrayList<Issue>()); 
                epicData.put("subtasks", new HashMap<Long, List<Issue>>());
                epicMap.put(issue.getIssueId(), epicData);
            }

            if(issue.getIssueType() == IssueType.STORIES){
                storyMap.put(issue.getIssueId(), issue);
            }
        }

        for(Issue issue: backlogs){
            Long epicId = issue.getEpicId();
            if(epicId != null && epicMap.containsKey(epicId)){
                @SuppressWarnings("unchecked")
                List<Issue> stories = (List<Issue>)epicMap.get(epicId).get("stories");
                stories.add(issue);
            }
        }

        return Collections.singletonMap("epics", epicMap); 

    }

}