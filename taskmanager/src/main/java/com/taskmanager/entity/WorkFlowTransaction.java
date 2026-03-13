package com.taskmanager.entity;

import com.taskmanager.enums.IssueStatus;
import com.taskmanager.enums.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "workflow_transactions", indexes = {
    @Index(name="idx_workflows_from_to_issue_status", columnList = "workflow_id, fromIssueStatus, toIssueStatus")
}) 
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WorkFlowTransaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workFlowTransactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id", nullable = false)
    private WorkFlow workFlow;

    private IssueStatus fromIssueStatus;

    private IssueStatus toIssueStatus;

    private String workFlowName;

    private Role allowedRole;

	public Long getWorkFlowTransactionId() {
		return workFlowTransactionId;
	}

	public void setWorkFlowTransactionId(Long workFlowTransactionId) {
		this.workFlowTransactionId = workFlowTransactionId;
	}

	public WorkFlow getWorkFlow() {
		return workFlow;
	}

	public void setWorkFlow(WorkFlow workFlow) {
		this.workFlow = workFlow;
	}

	public IssueStatus getFromIssueStatus() {
		return fromIssueStatus;
	}

	public void setFromIssueStatus(IssueStatus fromIssueStatus) {
		this.fromIssueStatus = fromIssueStatus;
	}

	public IssueStatus getToIssueStatus() {
		return toIssueStatus;
	}

	public void setToIssueStatus(IssueStatus toIssueStatus) {
		this.toIssueStatus = toIssueStatus;
	}

	public String getWorkFlowName() {
		return workFlowName;
	}

	public void setWorkFlowName(String workFlowName) {
		this.workFlowName = workFlowName;
	}

	public Role getAllowedRole() {
		return allowedRole;
	}

	public void setAllowedRole(Role allowedRole) {
		this.allowedRole = allowedRole;
	}
    

}
