package org.ict.testjpa.board.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import  org.ict.testjpa.board.dto.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
