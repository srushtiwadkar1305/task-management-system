package com.taskmanager.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.taskmanager.enums.IssuePriority;
import com.taskmanager.enums.IssueStatus;
import com.taskmanager.enums.IssueType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "issues") 
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Issue {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issueId;

    @Column(nullable=false)
    private String issueTitle;

    @Column(nullable=false, unique=true) 
    private String issueKey;

    @Column(length=2000)
    private String issueDescription;

    @Enumerated(EnumType.STRING)
    private IssueType issueType;

    @Enumerated(EnumType.STRING)
    private IssueStatus issueStatus;

    @Enumerated(EnumType.STRING)
    private IssuePriority issuePriority;

    private String assignedByEmail;

    private String reportToEmail;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @CreationTimestamp
    private LocalDateTime updatedAt; 

    private LocalDateTime dueDate;

    private Long sprintId;

    private Long epicId;

    private Long parentIssueId;

    private Integer backlogPosition;

    private Long projectId;

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public String getIssueTitle() {
		return issueTitle;
	}

	public void setIssueTitle(String issueTitle) {
		this.issueTitle = issueTitle;
	}

	public String getIssueKey() {
		return issueKey;
	}

	public void setIssueKey(String issueKey) {
		this.issueKey = issueKey;
	}

	public String getIssueDescription() {
		return issueDescription;
	}

	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}

	public IssueType getIssueType() {
		return issueType;
	}

	public void setIssueType(IssueType issueType) {
		this.issueType = issueType;
	}

	public IssueStatus getIssueStatus() {
		return issueStatus;
	}

	public void setIssueStatus(IssueStatus issueStatus) {
		this.issueStatus = issueStatus;
	}

	public IssuePriority getIssuePriority() {
		return issuePriority;
	}

	public void setIssuePriority(IssuePriority issuePriority) {
		this.issuePriority = issuePriority;
	}

	public String getAssignedByEmail() {
		return assignedByEmail;
	}

	public void setAssignedByEmail(String assignedByEmail) {
		this.assignedByEmail = assignedByEmail;
	}

	public String getReportToEmail() {
		return reportToEmail;
	}

	public void setReportToEmail(String reportToEmail) {
		this.reportToEmail = reportToEmail;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public Long getSprintId() {
		return sprintId;
	}

	public void setSprintId(Long sprintId) {
		this.sprintId = sprintId;
	}

	public Long getEpicId() {
		return epicId;
	}

	public void setEpicId(Long epicId) {
		this.epicId = epicId;
	}

	public Long getParentIssueId() {
		return parentIssueId;
	}

	public void setParentIssueId(Long parentIssueId) {
		this.parentIssueId = parentIssueId;
	}

	public Integer getBacklogPosition() {
		return backlogPosition;
	}

	public void setBacklogPosition(Integer backlogPosition) {
		this.backlogPosition = backlogPosition;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
    
    
    
    
}
