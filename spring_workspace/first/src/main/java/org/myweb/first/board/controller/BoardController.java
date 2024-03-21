package org.myweb.first.board.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.myweb.first.board.model.service.BoardService;
import org.myweb.first.board.model.vo.Board;
import org.myweb.first.common.FIleNameChange;
import org.myweb.first.common.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BoardController {
	// 이 클래스에서 메소드 안의 요청과 반환값달의 결과 출력 확인을 위한 로그 객체 생성
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardService boardService;
	
	//뷰 페이지 내보내기용 메소드 작성부 ---------------------------------------------------
	
	// 새 게시글 등록 페이지 내보내기용 메소드
	@RequestMapping("bwform.do")
	public String moveBoardWritePage() {
		return "board/boardWriteForm";
	}
	
	
	// 요청 처리용 메소드 작성부 --------------------------------------
	
	// 게시글 상세보기 요청 처리용
	@RequestMapping("bdetail.do")
	public String boardDetailMethod(
					@RequestParam("bnum") int boardNum,
					@RequestParam(name="page", required=false) String page, Model model) {
		// 목록으로 돌아갈 때의 페이지를 기억 저장
		int currentPage = 1;
		if(page != null) {
			currentPage = Integer.parseInt(page);
		}
		
		// 조회수 1증가 처리
		boardService.updateAddReadCount(boardNum);
		
		// 요청된 글번호의 게시글 조회
		Board board = boardService.selectBoard(boardNum);
		
		if(board != null) {
			model.addAttribute("board", board);
			model.addAttribute("currentPage", currentPage);
			
			return "board/boardDetailView";
		} else {
			model.addAttribute("message", boardNum + "번 게시글 상세보기 요청 실패!");
			return "common/error";
		}
	}
	
	
	// 새 게시원글 등록 요청 처리용 (첨부파일 업로드 기능 추가됨)
	
	@RequestMapping(value="binsert.do", method=RequestMethod.POST)
	public String boardInsertMethod(Board board, Model model, HttpServletRequest request, 
			@RequestParam(name="upfile", required=false) MultipartFile mfile) {
		logger.info("binsert.do : " + board);
		
		// 게시글 첨부파일 저장영 폴더 지정 : 톰켓(WAS)이 구동하고 있는 어플리케이션 프로젝트 안의 폴더 지정
		// el 절대경로 표기인 ${ pageContext.servletContext.contextPath } 와 같은 의미의 코드
		String savePath = request.getSession().getServletContext().getRealPath(
				"resources/board_upfiles");
		
		// 첨부파일이 있을 때
		if(!mfile.isEmpty()) {
			// 전송온 첨부파일명 추출함
			String fileName = mfile.getOriginalFilename();
			String renameFileName = null;
			
			// 저장 폴더에는 변경된 파일이름으로 파일을 저장함
			// 파일 이름 바꾸기함 => 년월일시분초.확장자
			if(fileName != null && fileName.length() > 0) {
				// 바꿀 파일명에 대한 문자열 포멧 만들기
				renameFileName = FIleNameChange.change(fileName, "yyyyMMddHHmmss");
				logger.info("첨부 파일명 변경 확인 : " + fileName + ", " + renameFileName);
				
				try {
					// 지정한 저장 폴더에 파일명 바꾸기 처리함
					mfile.transferTo(new File(savePath + "\\" + renameFileName));
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("message", "첨부 파일 저장 실패!");
					return "common/error";
				}
			} // 파일명 바꾸기
			// board 에 첨부파일 정보 저장 처리
			board.setBoardOriginalFileName(fileName);
			board.setBoardRenameFileName(renameFileName);
			
		} // 첨부 파일 있을 때
		
		if(boardService.insertOriginBoard(board) > 0) {
			// 게시글 등록 성공시 목록 보기 페이지로 이동
			return "redirect:blist.do";
		} else {
			model.addAttribute("message", "새 게시 원글 등록 실패!");
			return "common/error";
		}
		
	}
	
	
	
	
	@RequestMapping(value="btop3.do", method=RequestMethod.POST)
	@ResponseBody
	public String boardTop3Method() throws UnsupportedEncodingException {
		// ajax 요청시 리턴방법은 여러가지가 있음 
		// response 객체 이용시에는 2가지 중 선택 사용하면 됨
		// 1. 출력스트림으로 응답하는 방법
		// 2. 뷰리졸버 리턴하는 방법 : response body 에 내보낼 값을 저장함
		//			=> sevlet-context.xml 에 설정된 JSONView 클래스가 받아서 내보냄 : 등록 설정 필요
		
		// 조회수 많은 순으로 인기 게시글 3개 조회해 옴
		ArrayList<Board> list = boardService.selectTop3();
		
		// list 를 json 배열에 옮기기
		JSONArray jarr = new JSONArray();
		
		for(Board board : list) {
			// board 객체 저장용 json 객체 생성
			JSONObject job = new JSONObject();
			
			job.put("bnum", board.getBoardNum());
			// 한굴에 대해서는 인코딩해서 json에 담음 (한글 깨짐 방지)
			job.put("btitle", URLEncoder.encode(board.getBoardTitle(), "UTF-8") );
			job.put("rcount", board.getBoardReadCount());
			
			// job 를 jarr 에 추가
			jarr.add(job);
		}
		
		// 전송용 json 객체 준비
		JSONObject sendJson = new JSONObject();
		// 전송용 객체에 jarr 을 담음
		sendJson.put("list", jarr);
		
		// 전송용 json 을 json stirng 으로 바꿔서 전송되게 함
		return sendJson.toJSONString();
	}
	
	// 게시글 목록 보기 요청 처리용 (페이징 처리)
	@RequestMapping("blist.do")
	public String boardListMethod(
			@RequestParam(name="page", required=false) String page,
			@RequestParam(name="limit", required=false) String slimit,
			Model model) {
		int currentPage = 1;
		if(page != null) {
			currentPage = Integer.parseInt(page);
		}
		
		// 한 페이지에 게시글 10개씩 출력되게 한다면
		int limit = 10;
		if(slimit != null) {
			limit = Integer.parseInt(slimit);   // 전송받은 한 페이지에 출력할 목록 갯수를 적용
		}
		
		// 총페이지수 계산을 위해 게시글 전체 갯수 조회해 옴
		int listCount = boardService.selectListCount();
		// 페이징 계산 처리 실행
		Paging paging = new Paging(listCount, currentPage, limit, "blist.do");
		paging.calculate();
		
		// 출력할 페이지에 대한 목록 조회
		ArrayList<Board> list = boardService.selectList(paging);
		
		// 받은 결과로 성공/실패 페이지 내보냄
		if(list != null && list.size() > 0) {
			model.addAttribute("list", list);
			model.addAttribute("paging", paging);
			model.addAttribute("currnetPage", currentPage);
			model.addAttribute("limit", limit);
			
			return "board/boardListView";
		} else {
			model.addAttribute("message", currentPage + " 페이지 목록 조회 실패");
			return "common/error";
		}
	}
	
}






