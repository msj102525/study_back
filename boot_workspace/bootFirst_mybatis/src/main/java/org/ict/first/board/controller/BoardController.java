package org.ict.first.board.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.ict.first.board.service.BoardService;
import org.ict.first.board.vo.Board;
import org.ict.first.common.FileNameChange;
import org.ict.first.common.Paging;
import org.ict.first.common.Search;
import org.ict.first.common.SearchDate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BoardController {
	//이 컨트롤러 안의 메소드들이 구동되는지, 값이 잘 전달되었는지 확인을 위한 로그 객체를 생성
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardService boardService;
	
	//뷰 페이지 이동처리용 ----------------------------------------------------
	//새 게시원글 등록 페이지 이동 처리용
	@RequestMapping("bwform.do")
	public String moveBoardWritePage() {
		return "board/boardWriteForm";
	}
	
	//게시글 수정페이지로 이동 처리용
	@RequestMapping("bupview.do")
	public ModelAndView moveBoradUpdatePage(
			@RequestParam("bnum") int boardNum, 
			@RequestParam("page") int currentPage, 
			ModelAndView mv) {
		
		//수정페이지에 출력할 board 객체 조회해 옴
		Board board = boardService.selectBoard(boardNum);
		
		if(board != null) {
			mv.addObject("board", board);
			mv.addObject("currentPage", currentPage);
			mv.setViewName("board/boardUpdateView");
		}else {
			mv.addObject("message", boardNum + "번 게시글 수정페이지로 이동 실패!");
			mv.setViewName("common/error");
		}
		
		return mv;
	}
	
	//게시글 댓글 등록페이지로 이동 처리용
	@RequestMapping("breplyform.do")
	public ModelAndView moveReplyPage(
			@RequestParam("bnum") int boardNum, 
			@RequestParam("page") int currentPage, 
			ModelAndView mv) {
		
		mv.addObject("bnum", boardNum);
		mv.addObject("currentPage", currentPage);
		mv.setViewName("board/boardReplyForm");		
		
		return mv;
	}
	
	
	//요청 처리용 ---------------------------------------------------------------
	@RequestMapping(value="btop3.do", method=RequestMethod.POST)
	@ResponseBody
	public String boardRCountTop3Method() throws UnsupportedEncodingException {
		//ajax 요청시 리턴방법은 여러가지가 있음
		//response 객체 이용시에는 2가지중 선택 가능
		//1. 출력스트림으로 응답하는 방법 (아이디 중복 체크 예)
		//2. 뷰리졸버로 리턴하는 방법 : response body 에 내보낼 값을 저장함
		//	JSONView 클래스 등록 처리되어 있어야 함 : servlet-context.xml  
		
		//조회수 많은순으로 인기 게시글 3개 조회해 옴
		ArrayList<Board> list = boardService.selectTop3();
		
		//전송용 json 객체 준비
		JSONObject sendJson = new JSONObject();
		//list 저장할 json 배열 객체 준비
		JSONArray jarr = new JSONArray();
		
		//list 를 jarr 로 옮기기
		for(Board board : list) {
			//notice 의 각 필드값 저장할 json 객체 생성
			JSONObject job = new JSONObject();
			
			job.put("bnum", board.getBoardNum());
			//한글에 대해서는 인코딩해서 json에 담음 (한글 깨짐 방지)
			job.put("btitle", URLEncoder.encode(board.getBoardTitle(), "utf-8"));
			//날짜는 반드시 toString() 으로 문자열로 바꿔서 json 에 담아야 함
			job.put("rcount", board.getBoardReadCount());
			
			//job 를 jarr 에 추가함
			jarr.add(job);
		}
		
		//전송용 객체에 jarr 을 담음
		sendJson.put("list", jarr);
		
		//전송용 json 을 json string 으로 바꿔서 전송되게 함
		return sendJson.toJSONString();  //뷰리졸버로 리턴함
		//servlet-context.xml 에 jsonString 내보내는 JSONView 라는 뷰리졸버를 추가 등록해야 함
	}
	
	//게시글 목록보기 요청 처리용 (페이징 처리)
	@RequestMapping("blist.do")
	public String boardListMethod(
			@RequestParam(name="page", required=false) String page, 
			@RequestParam(name="limit", required=false) String slimit, 
			Model model) {
		int currentPage = 1;
		if (page != null) {
			currentPage = Integer.parseInt(page);
		}
		
		//한 페이지 게시글 10개씩 출력되게 한다면
		int limit = 10;
		if (slimit != null) {
			limit = Integer.parseInt(slimit);
		}
		
		//총 페이지 수 계산을 위한 게시글 총갯수 조회
		int listCount = boardService.selectListCount();
		//페이지 관련 항목 계산 처리
		Paging paging = new Paging(listCount, currentPage, limit, "blist.do");
		paging.calculator();
		
		//페이지에 출력할 목록 조회해 옴
		ArrayList<Board> list = boardService.selectList(paging);
		
		if(list != null && list.size() > 0) {
			model.addAttribute("list", list);
			model.addAttribute("paging", paging);
			model.addAttribute("currentPage", currentPage);
			model.addAttribute("limit", limit);
			
			return "board/boardListView";
		}else {
			model.addAttribute("message", currentPage + "페이지 목록 조회 실패!");
			return "common/error";
		}
	}
	
	//게시글 제목 검색용 (페이징 처리 포함)
	@RequestMapping(value="bsearchTitle.do", method= RequestMethod.POST)
	public ModelAndView boardSearchTitleMethod(
			@RequestParam("action") String action,			
			@RequestParam("keyword") String keyword,
			@RequestParam(name="limit", required=false) String slimit,
			@RequestParam(name="page", required=false) String page,
			ModelAndView mv) {
		
		//검색결과에 대한 페이징 처리
		//출력할 페이지 지정
		int currentPage = 1;
		//전송온 페이지 값이 있다면 추출함
		if(page != null) {
			currentPage = Integer.parseInt(page);
		}
		
		//한 페이지당 출력할 목록 갯수 지정
		int limit = 10;
		//전송 온 limit 값이 있다면
		if(slimit != null) {
			limit = Integer.parseInt(slimit);
		}
		
		//총 페이지수 계산을 위한 검색 결과 적용된 총 목록 갯수 조회
		int listCount = boardService.selectSearchTitleCount(keyword);
		
		//뷰 페이지에 사용할 페이징 관련 값 계산 처리
		Paging paging = new Paging(listCount, currentPage, limit, "bsearchTitle.do");
		paging.calculator();
		
		//서비스 메소드 호출하고 리턴결과 받기		
		Search search = new Search();
		search.setStartRow(paging.getStartRow());
		search.setEndRow(paging.getEndRow());
		search.setKeyword(keyword);
		
		ArrayList<Board> list = boardService.selectSearchTitle(search);
		
		//받은 결과에 따라 성공/실패 페이지 내보내기
		if(list != null && list.size() > 0) {
			mv.addObject("list", list);
			mv.addObject("paging", paging);
			mv.addObject("currentPage", currentPage);
			mv.addObject("limit", limit);
			mv.addObject("action", action);
			mv.addObject("keyword", keyword);			
			
			mv.setViewName("board/boardListView");
		}else {
			mv.addObject("message", action + "에 대한 " 
						+ keyword + " 검색 결과가 존재하지 않습니다.");			
			mv.setViewName("common/error");
		}
		
		return mv;
	}
	
	//게시글 작성자 검색용 (페이징 처리 포함)
	@RequestMapping(value="bsearchWriter.do", method= RequestMethod.POST)
	public ModelAndView boardSearchWriterMethod(
			@RequestParam("action") String action,			
			@RequestParam("keyword") String keyword,
			@RequestParam(name="limit", required=false) String slimit,
			@RequestParam(name="page", required=false) String page,
			ModelAndView mv) {
		
		//검색결과에 대한 페이징 처리
		//출력할 페이지 지정
		int currentPage = 1;
		//전송온 페이지 값이 있다면 추출함
		if(page != null) {
			currentPage = Integer.parseInt(page);
		}
		
		//한 페이지당 출력할 목록 갯수 지정
		int limit = 10;
		//전송 온 limit 값이 있다면
		if(slimit != null) {
			limit = Integer.parseInt(slimit);
		}
		
		//총 페이지수 계산을 위한 검색 결과 적용된 총 목록 갯수 조회
		int listCount = boardService.selectSearchWriterCount(keyword);
		
		//뷰 페이지에 사용할 페이징 관련 값 계산 처리
		Paging paging = new Paging(listCount, currentPage, limit, "bsearchWriter.do");
		paging.calculator();
		
		//서비스 메소드 호출하고 리턴결과 받기		
		Search search = new Search();
		search.setStartRow(paging.getStartRow());
		search.setEndRow(paging.getEndRow());
		search.setKeyword(keyword);
		
		ArrayList<Board> list = boardService.selectSearchWriter(search);
		
		//받은 결과에 따라 성공/실패 페이지 내보내기
		if(list != null && list.size() > 0) {
			mv.addObject("list", list);
			mv.addObject("paging", paging);
			mv.addObject("currentPage", currentPage);
			mv.addObject("limit", limit);
			mv.addObject("action", action);
			mv.addObject("keyword", keyword);			
			
			mv.setViewName("board/boardListView");
		}else {
			mv.addObject("message", action + "에 대한 " 
						+ keyword + " 검색 결과가 존재하지 않습니다.");			
			mv.setViewName("common/error");
		}
		
		return mv;
	}
	
	//게시글 등록날짜로 검색용 (페이징 처리 포함)
	@RequestMapping(value="bsearchDate.do", method= RequestMethod.POST)
	public ModelAndView boardSearchDateMethod(
			SearchDate searchDate,
			@RequestParam("action") String action,
			@RequestParam(name="limit", required=false) String slimit,
			@RequestParam(name="page", required=false) String page,
			ModelAndView mv) {
		
		//검색결과에 대한 페이징 처리
		//출력할 페이지 지정
		int currentPage = 1;
		//전송온 페이지 값이 있다면 추출함
		if(page != null) {
			currentPage = Integer.parseInt(page);
		}
		
		//한 페이지당 출력할 목록 갯수 지정
		int limit = 10;
		//전송 온 limit 값이 있다면
		if(slimit != null) {
			limit = Integer.parseInt(slimit);
		}
		
		//총 페이지수 계산을 위한 검색 결과 적용된 총 목록 갯수 조회
		int listCount = boardService.selectSearchDateCount(searchDate);
		
		//뷰 페이지에 사용할 페이징 관련 값 계산 처리
		Paging paging = new Paging(listCount, currentPage, limit, "bsearchDate.do");
		paging.calculator();
		
		//서비스 메소드 호출하고 리턴결과 받기		
		Search search = new Search();
		search.setStartRow(paging.getStartRow());
		search.setEndRow(paging.getEndRow());
		search.setBegin(searchDate.getBegin());
		search.setEnd(searchDate.getEnd());
		
		ArrayList<Board> list = boardService.selectSearchDate(search);
		
		//받은 결과에 따라 성공/실패 페이지 내보내기
		if(list != null && list.size() > 0) {
			mv.addObject("list", list);
			mv.addObject("paging", paging);
			mv.addObject("currentPage", currentPage);
			mv.addObject("limit", limit);
			mv.addObject("action", action);
			mv.addObject("begin", searchDate.getBegin());
			mv.addObject("end", searchDate.getEnd());			
			
			mv.setViewName("board/boardListView");
		}else {
			mv.addObject("message", action + "에 대한 " + searchDate.getBegin() + "부터 "
					+ searchDate.getEnd() + " 기간 사이에 가입한 회원 정보가 존재하지 않습니다.");		
			mv.setViewName("common/error");
		}
		
		return mv;
	}
	
	//게시글 상세보기 요청 처리용
	@RequestMapping("bdetail.do")
	public ModelAndView boardDetailMethod(
			@RequestParam("bnum") int boardNum, 
			@RequestParam(name="page", required=false) String page,
			ModelAndView mv) {		
		
		//출력할 페이지 지정
		int currentPage = 1;
		//전송온 페이지 값이 있다면 추출함
		if(page != null) {
			currentPage = Integer.parseInt(page);
		}
		
		//조회수 1증가 처리		
		boardService.updateAddReadCount(boardNum);
				
		Board board = boardService.selectBoard(boardNum);
		
		if(board != null) {
			mv.addObject("board", board);
			mv.addObject("currentPage", currentPage);			
			
			mv.setViewName("board/boardDetailView");
			
		}else {
			mv.addObject("message", boardNum + "번 게시글 상세보기 조회 실패!");
			mv.setViewName("common/error");
		}
		
		return mv;
	}

	//새 게시원글 등록 요청 처리용 (파일 업로드 기능 추가)
	@RequestMapping(value="binsert.do", method=RequestMethod.POST)
	public String boardInsertMethod(Board board, Model model, HttpServletRequest request, 
			@RequestParam(name="upfile", required=false) MultipartFile mfile) {
		logger.info("binsert.do : " + board);
		
		//게시글 첨부파일 저장 폴더 경로 지정
		String savePath = request.getSession().getServletContext().getRealPath(
				"resources/board_upfiles");
		
		//첨부파일이 있을 때
		if(!mfile.isEmpty()) {
			//전송온 파일이름 추출함
			String fileName = mfile.getOriginalFilename();
			String renameFileName = null;
			
			//저장폴더에는 변경된 이름을 저장 처리함
			//파일 이름바꾸기함 : 년월일시분초.확장자
			if(fileName != null && fileName.length() > 0) {				
				//바꿀 파일명에 대한 문자열 만들기
				renameFileName = FileNameChange.change(fileName, 	"yyyyMMddHHmmss");
				logger.info("첨부파일명 확인 : " + fileName + ", " + renameFileName);
				try {	
					//저장 폴더에 파일명 바꾸기 처리
					mfile.transferTo(new File(savePath + "\\" + renameFileName));
				
				}catch(Exception e) {
					e.printStackTrace();
					model.addAttribute("message", "첨부파일 저장 실패!");
					return "common/error";
				}
			}  //파일명 바꾸기
			//board 객체에 첨부파일 정보 저장 처리
			board.setBoardOriginalFileName(fileName);
			board.setBoardRenameFileName(renameFileName);
		} //첨부파일 있을 때		
		
		if(boardService.insertOriginBoard(board) > 0) {
			//게시글 등록 성공시 목록 보기 페이지로 이동
			return "redirect:blist.do";
		}else {
			model.addAttribute("message", "새 게시 원글 등록 실패!");
			return "common/error";
		}
	}

	//게시글 삭제 요청 처리용
	@RequestMapping("bdelete.do")
	public String boardDeleteMethod(
			Board board, 	HttpServletRequest request, Model model) {
		
		if(boardService.deleteBoard(board) > 0) {
			//게시글 삭제 성공시 저장 폴더에 있는 첨부파일도 삭제함
			if(board.getBoardRenameFileName() != null) {
				//게시글 첨부파일 저장 폴더 경로 지정
				String savePath = request.getSession().getServletContext().getRealPath(
						"resources/board_upfiles");
				//저장 폴더에서 파일 삭제함
				new File(savePath + "\\" + board.getBoardRenameFileName()).delete();
			}
			
			return "redirect:blist.do";
		}else {
			model.addAttribute("message", board.getBoardNum() + "번 게시글 삭제 실패!");
			return "common/error";
		}
	}
	
	//첨부파일 다운로드 요청 처리용
	@RequestMapping("bfdown.do")
	public ModelAndView fileDownMethod(
			ModelAndView mv, HttpServletRequest request, 
			@RequestParam("ofile") String originalFileName,
			@RequestParam("rfile") String renameFileName) {
		//파일 다운 메소드는 리턴 타입이 ModelAndView 로 정해져 있음
		
		//게시글 첨부파일 저장 폴더 경로 지정
		String savePath = request.getSession().getServletContext().getRealPath(
				"resources/board_upfiles");
		
		//저장 폴더에서 읽을 파일에 대한 파일 객체 생성함
		File renameFile = new File(savePath + "\\" + renameFileName);
		//파일 다운시 브라우저 내보낼 원래 파일이름에 대한 파일 객체 생성함
		File originFile = new File(originalFileName);
		
		//파일 다운로드용 뷰로 전달할 정보 저장 처리
		mv.setViewName("filedown");   //등록된 파일다운로드용 뷰클래스의 id명
		mv.addObject("renameFile", renameFile);
		mv.addObject("originFile", originFile);
		
		return mv;
	}
	
	//게시 원글 수정 요청 처리용 (파일 업로드 기능 사용)
	@RequestMapping(value="boriginupdate.do", method=RequestMethod.POST)
	public String boardOriginUpdateMethod(Board board, Model model, 
			HttpServletRequest request, 
			@RequestParam(name="deleteFlag", required=false) String delFlag,
			@RequestParam(name="page", required=false) String page,
			@RequestParam(name="upfile", required=false) MultipartFile mfile) {
		logger.info("boriginupdate.do : " + board);
		
		int currentPage = 1;
		if(page != null) {
			currentPage = Integer.parseInt(page);
		}
		
		//게시 원글 첨부파일 저장 폴더 경로 지정
		String savePath = request.getSession().getServletContext().getRealPath(
				"resources/board_upfiles");
		
		//첨부파일이 변경된 경우의 처리 --------------------------------------------------------
		//1. 원래 첨부파일이 있는데 '파일삭제'를 선택한 경우
		//   또는 원래 첨부파일이 있는데 새로운 첨부파일이 업로드된 경우
		if(board.getBoardOriginalFileName() != null && 
				(delFlag != null && delFlag.equals("yes")) || !mfile.isEmpty()) {
			//저장 폴더에서 파일 삭제함
			new File(savePath + "\\" + board.getBoardRenameFileName()).delete();
			//board 안의 파일정보도 제거함
			board.setBoardOriginalFileName(null);
			board.setBoardRenameFileName(null);
		}
		
		//2. 새로운 첨부파일이 있을 때 (공지글 첨부파일은 1개임)
		if(!mfile.isEmpty()) {			
			//전송온 파일이름 추출함
			String fileName = mfile.getOriginalFilename();
			String renameFileName = null;
			
			//저장폴더에는 변경된 이름을 저장 처리함
			//파일 이름바꾸기함 : 년월일시분초.확장자
			if(fileName != null && fileName.length() > 0) {				
				//바꿀 파일명에 대한 문자열 만들기
				renameFileName = FileNameChange.change(fileName, 	"yyyyMMddHHmmss");
				logger.info("첨부파일명 확인 : " + fileName + ", " + renameFileName);
				try {	
					//저장 폴더에 파일명 바꾸기 처리
					mfile.transferTo(new File(savePath + "\\" + renameFileName));
				
				}catch(Exception e) {
					e.printStackTrace();
					model.addAttribute("message", "첨부파일 저장 실패!");
					return "common/error";
				}
			}  //파일명 바꾸기
			//board 객체에 첨부파일 정보 저장 처리
			board.setBoardOriginalFileName(fileName);
			board.setBoardRenameFileName(renameFileName);
		} //첨부파일 있을 때	
		
		if(boardService.updateOriginBoard(board) > 0) {
			//게시원글 수정 성공시 상세 보기 페이지로 이동			
			return "redirect:bdetail.do?bnum=" + board.getBoardNum() + "&page=" + currentPage;
		}else {
			model.addAttribute("message", board.getBoardNum() + "번 게시원글 수정 실패!");
			return "common/error";
		}
	}
	
	//게시 댓글, 대댓글 등록 처리용
	@RequestMapping(value="breply.do", method=RequestMethod.POST)
	public String replyInsertMethod(Board reply, 
			@RequestParam("bnum") String bnum,
			@RequestParam("page") String page, Model model) {
		//새로 등록할 댓글의 원글 또는 대댓글의 댓글을 조회해 옴
		int boardNum = Integer.parseInt(bnum);
		Board origin = boardService.selectBoard(boardNum);
		
		//새로 등록할 댓글 또는 대댓글의 레벨을 설정
		reply.setBoardLev(origin.getBoardLev() + 1);
		//logger.info("breply.do : " + reply);
		
		//참조 원글 번호
		reply.setBoardRef(origin.getBoardRef());
		
		//대댓글(댓글의 댓글)일 때는 boardReplyRef (참조 댓글번호) 값 지정
		if(reply.getBoardLev() == 3) {			
			//참조 댓글 번호
			reply.setBoardReplyRef(origin.getBoardNum());
		}
		//logger.info("breply.do : " + reply);
		
		//최근 등록 댓글과 대댓글의 순번을 1로 지정함
		reply.setBoardReplySeq(1);
		//logger.info("breply.do : " + reply);
		//기존 댓글과 대댓글의 순번을 1증가 처리함
		boardService.updateReplySeq(reply);
		
		
		if(boardService.insertReply(reply) > 0) {
			return "redirect:blist.do?page=" + page;
		}else {
			model.addAttribute("message", reply.getBoardRef() + "번에 대한 댓글 등록 실패!");
			return "common/error";
		}
	}
	
	//댓글과 대댓글 수정 처리용
	@RequestMapping(value="breplyupdate.do", method=RequestMethod.POST)
	public String replyUpdateMethod(Board reply, 
			@RequestParam("page") String page, Model model) {
		
		if(boardService.updateReplyBoard(reply) > 0) {
			//댓글과 대댓글 수정 성공시 다시 상세보기가 보여지게 처리			
			return "redirect:bdetail.do?bnum=" + reply.getBoardNum() + "&page=" + page;
		}else {
			model.addAttribute("message", reply.getBoardNum() + "번 글 수정 실패!");
			return "common/error";
		}
	}
}














