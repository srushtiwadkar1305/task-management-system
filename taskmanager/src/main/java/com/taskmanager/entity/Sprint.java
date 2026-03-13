package com.taskmanager.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.taskmanager.enums.SprintState;

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
@Table(name = "sprints") 
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Sprint {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sprintId;

    private String sprintName;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private SprintState sprintState;

    @Column(length=2000)
    private String sprintDescription;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private Long projectId;

	public Long getSprintId() {
		return sprintId;
	}

	public void setSprintId(Long sprintId) {
		this.sprintId = sprintId;
	}

	public String getSprintName() {
		return sprintName;
	}

	public void setSprintName(String sprintName) {
		this.sprintName = sprintName;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public SprintState getSprintState() {
		return sprintState;
	}

	public void setSprintState(SprintState sprintState) {
		this.sprintState = sprintState;
	}

	public String getSprintDescription() {
		return sprintDescription;
	}

	public void setSprintDescription(String sprintDescription) {
		this.sprintDescription = sprintDescription;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

}
