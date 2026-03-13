package com.taskmanager.entity;

import com.taskmanager.enums.IssueStatus;

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
@Table(name = "board_column", indexes = {
    @Index(name="idx_board_column", columnList = "board_id, position ")
}) 
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BoardColumn {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardColumnId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    private String name;

    private IssueStatus statusKey;

    private Integer position;

    private Integer wipLimit;

	public Long getBoardColumnId() {
		return boardColumnId;
	}

	public void setBoardColumnId(Long boardColumnId) {
		this.boardColumnId = boardColumnId;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IssueStatus getStatusKey() {
		return statusKey;
	}

	public void setStatusKey(IssueStatus statusKey) {
		this.statusKey = statusKey;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getWipLimit() {
		return wipLimit;
	}

	public void setWipLimit(Integer wipLimit) {
		this.wipLimit = wipLimit;
	}

}
