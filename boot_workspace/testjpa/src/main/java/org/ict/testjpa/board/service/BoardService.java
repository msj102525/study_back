package org.ict.testjpa.board.service;

import lombok.RequiredArgsConstructor;
import org.ict.testjpa.board.dto.Board;
import org.ict.testjpa.board.jpa.BoardRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService  {

    private final BoardRepository repository;

    @Transactional(readOnly = true)
    public List<Board> selectList() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "boardNum"));
    }

    @Transactional
    public Optional<Board> selectBoard(Long boardNum) {
        return repository.findById(boardNum);
    }

    @Transactional
    public void insertBoard(Board board) {
        repository.save(board);
    }

    @Transactional
    public void updateBoard(Board board) {
        Board boardEntity = repository.getOne(board.getBoardNum());
        boardEntity.setBoardTitle(board.getBoardTitle());
        boardEntity.setBoardContent(board.getBoardContent());
    }

    @Transactional
    public void deleteBoard(Long boardNum) {
        repository.deleteById(boardNum);
    }
}
