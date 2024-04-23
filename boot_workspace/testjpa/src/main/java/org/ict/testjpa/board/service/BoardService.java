package org.ict.testjpa.board.service;

import org.ict.testjpa.board.dto.Board;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    public List<Board> selectList();
    public Optional<Board> selectBoard(Long boardNum);
    public void insertBoard(Board board);
    public void updateBoard(Board board);
    public void deleteBoard(Long boardNum);
}
