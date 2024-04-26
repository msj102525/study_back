package org.ict.testjpa2.board.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.testjpa2.board.jpa.entity.BoardEntity;
import org.ict.testjpa2.board.jpa.repository.BoardNativeVo;
import org.ict.testjpa2.board.jpa.repository.BoardRepository;
import org.ict.testjpa2.board.model.dto.BoardDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    //Service 에 대한 interface 를 만들어서, 상속받은 ServiceImpl 클래스를 만드는 구조로 작성해도 됨

    private final BoardRepository boardRepository;

    public ArrayList<BoardDto> selectTop3(){
        List<BoardNativeVo> nativeVoList = boardRepository.findTop3();
        ArrayList<BoardDto> list = new ArrayList<>();
        //내림차순정렬된 상위 3개만 추출함
        //JPQL 은 WHERE, GROUP BY 절에서만 서브쿼리 사용 가능함 => FROM 절에서 서브쿼리 사용 못 함
        for(int i = 0; i < 3; i++){
            BoardDto boardDto = new BoardDto();
            boardDto.setBoardNum(nativeVoList.get(i).getBoard_num());
            boardDto.setBoardTitle(nativeVoList.get(i).getBoard_title());
            boardDto.setBoardReadCount(nativeVoList.get(i).getBoard_readcount());
            list.add(boardDto);
        }

        return list;
    }

    //게시글 상세 조회 처리용
    public BoardDto selectBoard(Integer boardNum){
        Optional<BoardEntity> optionalBoardEntity =  boardRepository.findById(boardNum);
        BoardEntity boardEntity = optionalBoardEntity.get();

        //게시글 조회수 1증가 처리
        boardEntity.setBoardReadCount(boardEntity.getBoardReadCount() + 1);
        boardRepository.save(boardEntity);  //존재하는 글에 save 하면 수정됨

        return boardEntity.toDto();
    }

    public void insertBoard(BoardDto boardDto){
        //데이터베이스 시퀀스객체를 사용하지 않는 경우
        // (BoardEntity  의 primary key 에 시퀀스 사용 등록 안 된 경우)
        //JPQL 에서는 WHERE 절과 GROUP BY 절에서만 서브쿼리 사용 가능함
        //SELECT 절과 FROM 절에서 서브쿼리 사용 못 함 => 서브쿼리 구문을 따로 작성해서 실행되게 하면 됨
        //마지막 게시글 번호 조회해 옴
        boardDto.setBoardNum(boardRepository.findLastBoardNum() + 1);
        boardRepository.save(boardDto.toEntity());
    }

    public void updateBoard(BoardDto boardDto){
        BoardEntity boardEntity = boardRepository.getReferenceById(boardDto.getBoardNum());
        log.info("boardService.updateBoard : " + boardEntity);  //수정 글 확인
        boardEntity.setBoardTitle(boardDto.getBoardTitle());        //제목 수정
        boardEntity.setBoardContent(boardDto.getBoardContent());    //내용 수정
        boardEntity.setBoardReadCount(boardEntity.getBoardReadCount() + 1);  //조회수 1증가

        boardRepository.save(boardEntity);   //존재하는 글번호이면 수정임
    }

    public void deleteBoard(int boardNum){
        boardRepository.deleteById(boardNum);
    }
}
