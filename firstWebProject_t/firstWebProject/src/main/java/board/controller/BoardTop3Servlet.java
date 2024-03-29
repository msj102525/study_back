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

import board.model.service.BoardService;
import board.model.vo.Board;

/**
 * Servlet implementation class BoardTop3Servlet
 */
@WebServlet("/btop3")
public class BoardTop3Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardTop3Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ajax 요청으로 조회수 많은 인기 게시글 3개 조회 요청 처리용 컨트롤러
		
		ArrayList<Board> list = new BoardService().selectTop3();
		
		JSONArray jarr = new JSONArray();
		
		for(Board board : list) {
			JSONObject job = new JSONObject();
			
			job.put("bnum", board.getBoardNum());
			job.put("btitle", URLEncoder.encode(board.getBoardTitle(), "utf-8"));
			job.put("rcount", board.getBoardReadCount());
			
			jarr.add(job);
		}
		
		JSONObject sendJson = new JSONObject();
		sendJson.put("list", jarr);
		
		response.setContentType("application/json; charset=utf-8");
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
