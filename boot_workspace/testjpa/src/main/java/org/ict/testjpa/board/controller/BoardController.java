package org.ict.testjpa.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.testjpa.board.dto.Board;
import org.ict.testjpa.board.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/boards")
public class BoardController {
    // @Autowired
    private final BoardService service;

    @GetMapping
    public ResponseEntity<List<Board>> selectAll() {
        log.info("selectAll()");
        return new ResponseEntity<>(service.selectList(), HttpStatus.OK);
    }

    @GetMapping("/{boardNum}")
    public ResponseEntity<Board> selectOne(@PathVariable("boardNum") long boardNum) {
        log.info("selectOne()");
        Optional<Board> board = service.selectBoard(boardNum);
        return new ResponseEntity<>(board.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Board> insert(@RequestBody Board board) {
        log.info("insert");
        board.setBoardDate(new Date(Calendar.getInstance().getTimeInMillis()));
        service.insertBoard(board);
        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Board> update(@RequestBody Board board) {
        log.info("update");
        board.setBoardDate(new Date(Calendar.getInstance().getTimeInMillis()));
        service.updateBoard(board);
        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @DeleteMapping("/{boardNum}")
    public ResponseEntity<Void> delete(@PathVariable("boardNum") long boardNum) {
        log.info("delete");
        service.deleteBoard(boardNum);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }


}
