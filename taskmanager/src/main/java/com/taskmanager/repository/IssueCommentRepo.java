package com.taskmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskmanager.entity.IssueComment;

@Repository
public interface IssueCommentRepo extends JpaRepository<IssueComment, Long> { 
    
    List<IssueComment> findByIssueIdOrderByCreatedAtDesc(Long issueId);

}
