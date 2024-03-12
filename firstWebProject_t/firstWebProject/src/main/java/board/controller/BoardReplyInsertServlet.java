package board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Board;

/**
 * Servlet implementation class BoardReplyInsertServlet
 */
@WebServlet("/breply")
public class BoardReplyInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardReplyInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 게시글 댓글 등록 처리용 컨트롤러
		
		request.setCharacterEncoding("utf-8");
		
		//댓글에 대한 원글 번호 추출
		int boardNum = Integer.parseInt(request.getParameter("bnum"));
		int currentPage = Integer.parseInt(request.getParameter("page"));
		
		//댓글 정보 저장 객체 준비
		Board reply = new Board();
		
		//전송값 꺼내서 객체 필드에 저장
		reply.setBoardTitle(request.getParameter("title"));
		reply.setBoardWriter(request.getParameter("writer"));
		reply.setBoardContent(request.getParameter("content"));
		
		//원글 정보 조회해 옴
		BoardService bservice = new BoardService();
		Board origin = bservice.selectBoard(boardNum);
		
		//boardLev : 1 (원글), 2 (원글의 댓글)
		//boardRef : 참조하는 원글 번호
		reply.setBoardLev(origin.getBoardLev() + 1);
		reply.setBoardRef(origin.getBoardRef());
		
		//댓글의 댓글일 때(대댓글일 때)는 참조하는 댓글 번호(boardReplyRef)에 원글번호 기록
		//board_reply_ref 컬럼 값을 원글은 null, 원글의 댓글(lev : 2) 자기번호, 
		//댓글의 댓글(lev : 3) 참조하는 댓글번호를 기록해야 함
		if(reply.getBoardLev() == 3) {  //대댓글이면
			reply.setBoardReplyRef(origin.getBoardReplyRef());
		}
		
		//댓글의 순번 처리 : boardReplySeq
		//최신 댓글이 무조건 1이 되게 함
		reply.setBoardReplySeq(1);
		//이전 댓글의 순번을 모두 1증가 처리함
		bservice.updateReplySeq(reply);  //update 로 board_reply_seq 1증가 처리 실행
		
		//댓글 등록 실행
		int result = bservice.insertReply(reply);
		
		if(result > 0) {
			response.sendRedirect("/first/blist?page=" + currentPage);
		}else {
			RequestDispatcher view = request.getRequestDispatcher("views/common/error.jsp");
			request.setAttribute("message", boardNum + "번 게시글의 댓글 등록 실패!");
			view.forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
