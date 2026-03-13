package com.taskmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskmanager.entity.BoardColumn;

@Repository
public interface BoardColumnRepo extends JpaRepository<BoardColumn, Long> {

    List<BoardColumn> findByBoardBoardIdOrderByPosition(Long boardId);

}
