package com.taskmanager.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taskmanager.entity.Board;
import com.taskmanager.entity.BoardCard;
import com.taskmanager.entity.BoardColumn;
import com.taskmanager.entity.Issue;
import com.taskmanager.enums.IssueStatus;
import com.taskmanager.repository.BoardCardRepo;
import com.taskmanager.repository.BoardColumnRepo;
import com.taskmanager.repository.BoardRepo;
import com.taskmanager.repository.IssueRepo;

@Service
public class BoardService {
    
    @Autowired
    private BoardRepo boardRepo;

    @Autowired
    private BoardColumnRepo boardColumnRepo;

    @Autowired
    private BoardCardRepo boardCardRepo;

    @Autowired
    private IssueRepo issueRepo;

    public Board createBoard(Board board){
        return boardRepo.save(board);
    }

    public Board getBoardById(Long boardId){
        return boardRepo.findById(boardId)
            .orElseThrow(() -> new RuntimeException("Board not found"));
    }

    public List<BoardColumn> getBoardColumns(Long boardId){
        return boardColumnRepo.findByBoardBoardIdOrderByPosition(boardId);
    }

    public List<BoardCard> getBoardCard(Long boardId, Long boardColumnId){
        return boardCardRepo.findByBoardIdAndBoardColumnBoardColumnIdOrderByPosition(boardId, boardColumnId);
    }

    @Transactional
	public BoardCard addIssueToBoard(Long boardId, Long boardColumnId, Long issueId) {
		
		issueRepo.findById(issueId)
            .orElseThrow(()-> new RuntimeException("Issue not found"));
	
        BoardCard boardCard = boardCardRepo.findByIssueId(issueId);
        if(boardCard != null){
            boardCardRepo.delete(boardCard);
        }

        BoardColumn boardColumn = boardColumnRepo.findById(boardColumnId)
            .orElseThrow(()-> new RuntimeException("Board Column not found"));


        if(boardColumn.getWipLimit() !=null && boardColumn.getWipLimit()> 0) {

			Long count = boardCardRepo.countByBoardIdAndBoardColumnBoardColumnId(boardId, boardColumnId);
			
			if(count >= boardColumn.getWipLimit()) {
				throw new RuntimeException("WIP limit reached for column: " + boardColumn.getName());
			}

		}

        List<BoardCard> existing = boardCardRepo.findByBoardIdAndBoardColumnBoardColumnIdOrderByPosition(boardId, boardColumnId);
		
		int postion = existing.size();
		
		BoardCard card = new BoardCard();
		card.setBoardId(boardId);
		card.setBoardColumn(boardColumn);
		card.setIssueId(issueId);
		card.setPosition(postion);
		
		card = boardCardRepo.save(card);

        return card;

	}

    @Transactional
	public void moveCards(
        Long boardId, 
        Long boardColumnId, 
        Long boardCardId, 
        int position, 
        String performedBy
    ) {
		
		BoardCard card = boardCardRepo.findById(boardCardId)
            .orElseThrow(()-> new RuntimeException("Cards not avilable"));
		
		
		BoardColumn fromColumn = card.getBoardColumn();

		BoardColumn toColumn = boardColumnRepo.findById(boardColumnId)
            .orElseThrow(()-> new RuntimeException("column not found"));
		
		if(toColumn.getWipLimit()!=null && toColumn.getWipLimit()>0 ) {

			Long count = boardCardRepo.countByBoardIdAndBoardColumnBoardColumnId(boardId, boardColumnId);
			
			if(!Objects.equals(fromColumn.getBoardColumnId(), toColumn.getBoardColumnId()) && count >=toColumn.getWipLimit() ) {
			    throw new RuntimeException("Wip limit exceeded for column: " + toColumn.getName());
			}
			
		}
		
		List<BoardCard> fromCards = boardCardRepo.findByBoardIdAndBoardColumnBoardColumnIdOrderByPosition(boardId,fromColumn.getBoardColumnId());
		
		for(BoardCard c : fromCards) {
			if(c.getPosition()> card.getPosition() ) {
				c.setPosition(c.getPosition()-1);
				boardCardRepo.save(c);
			}
		}
		
		List<BoardCard> toCards = boardCardRepo.findByBoardIdAndBoardColumnBoardColumnIdOrderByPosition(boardId,toColumn.getBoardColumnId());
		
		for(BoardCard c : toCards) {
			if(c.getPosition()> card.getPosition() ) {
				c.setPosition(c.getPosition()+1);
				boardCardRepo.save(c);
			}
		}
		
		Issue issue = issueRepo.findById(card.getIssueId())
        .orElseThrow(()-> new RuntimeException("Issue not found"));

        updateIssueStatus(issue, toColumn.getStatusKey());
        
	}

    private void updateIssueStatus(Issue issue, IssueStatus issueStatus) {

		if(issueStatus==null || Objects.isNull(issueStatus) ) {
			return;
		}
        
		try {
		
		    issue.setIssueStatus(issueStatus);
		    issueRepo.save(issue);

		}catch(Exception e) {
			throw new RuntimeException("Invalid statusKey mapping: " + issueStatus,e);
		}
	}

    @Transactional
	public void recordColumn(Long boardId, Long columnId, List<Long> orderedCardId) {
		int postion= 0;
		for(Long cardId : orderedCardId) {
			BoardCard card = boardCardRepo.findById(cardId).orElseThrow(()-> new RuntimeException("card not avialable"));
			
			card.setPosition(postion);
			boardCardRepo.save(card);
		}
	}

    @Transactional
	public void startSprint(Long sprintId) {}

    @Transactional
    public void completeSprint(Long sprintId) {}

}
