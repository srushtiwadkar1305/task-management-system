package com.taskmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskmanager.entity.BoardCard;

@Repository
public interface BoardCardRepo extends JpaRepository<BoardCard, Long> {
    
    List<BoardCard> findByBoardIdAndBoardColumnBoardColumnIdOrderByPosition(
        Long boardId,
        Long boardColumnId
    );

    BoardCard findByIssueId(Long issueId);

    Long countByBoardIdAndBoardColumnBoardColumnId(
        Long boardId,
        Long boardColumnId
    );

}
