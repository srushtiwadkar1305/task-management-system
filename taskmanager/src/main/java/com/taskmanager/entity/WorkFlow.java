package com.taskmanager.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "workflows") 
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WorkFlow {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workFlowId;

    @Column(nullable=false, unique=true) 
    private String workFlowName;

    @Column(length = 5000)
    private String workFlowDescription;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy="workFlow")
    @Builder.Default
    private List<WorkFlowTransaction> workFlowTransaction = new ArrayList<>(); 

//    public void addWorkFlowTransaction(WorkFlowTransaction tx) {
//        this.workFlowTransaction.add(tx);
//        tx.setWorkFlow(this);
//    }
//
//    public void removeWorkFlowTransaction(WorkFlowTransaction tx) {
//        this.workFlowTransaction.remove(tx);
//        tx.setWorkFlow(null);
//    }

	public Long getWorkFlowId() {
		return workFlowId;
	}

	public void setWorkFlowId(Long workFlowId) {
		this.workFlowId = workFlowId;
	}

	public String getWorkFlowName() {
		return workFlowName;
	}

	public void setWorkFlowName(String workFlowName) {
		this.workFlowName = workFlowName;
	}

	public String getWorkFlowDescription() {
		return workFlowDescription;
	}

	public void setWorkFlowDescription(String workFlowDescription) {
		this.workFlowDescription = workFlowDescription;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<WorkFlowTransaction> getWorkFlowTransaction() {
		return workFlowTransaction;
	}

	public void setWorkFlowTransaction(List<WorkFlowTransaction> workFlowTransaction) {
		this.workFlowTransaction = workFlowTransaction;
	}

    
    
    
}
