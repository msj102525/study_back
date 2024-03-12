package board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import board.model.dto.Board;
import board.model.service.BoardService;

/**
 * Servlet implementation class BoardTop3
 */
@WebServlet("/btop3")
public class BoardTop3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardTop3() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 조회수 많은 순으로 인기게시글 3개 조회 요청 처리용 컨트롤러 : ajax 통신
		
				ArrayList<Board> list = new BoardService().selectTop3();
				// json 객체 3개로 바꾸고, json array 객체에 담아서 내보냄
				
				JSONArray jarr = new JSONArray();
				
				for(Board board : list) {
					JSONObject job = new JSONObject();
					
					job.put("bnum", board.getBoardNum());
					// 한글 데이터는 반드시 인코딩 처리해야 함
					job.put("btitle", URLEncoder.encode(board.getBoardTitle(), "UTF-8"));
					// 날짜데이터는 반드시 toString()으로 문자열로 바꿔서 저장할 것 => 날짜 그대로 저장하면 뷰에 출력안됨
					job.put("rcount", board.getBoardReadcount());
					
					jarr.add(job);
				}
				
				// 전송용 객체를 준비함
				JSONObject sendJson = new JSONObject();
				sendJson.put("blist", jarr);
				
				// ajax 통신은 별도의 스트림을 만들어서 전송함
				response.setContentType("application/json; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print(sendJson.toJSONString());
				out.flush();
				out.close();
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
