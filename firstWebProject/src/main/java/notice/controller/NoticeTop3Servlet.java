package notice.controller;

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

import notice.model.dto.Notice;
import notice.model.service.NoticeService;

/**
 * Servlet implementation class NoticeTop3Servlet
 */
@WebServlet("/ntop3")
public class NoticeTop3Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeTop3Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 새 공지글 3개 조회 요청 처리용 컨트롤러 : ajax 통신
		
		ArrayList<Notice> list = new NoticeService().selectTop3();
		// json 객체 3개로 바꾸고, json array 객체에 담아서 내보냄
		
		JSONArray jarr = new JSONArray();
		
		for(Notice notice : list) {
			JSONObject job = new JSONObject();
			
			job.put("no", notice.getNoticeNo());
			// 한글 데이터는 반드시 인코딩 처리해야 함
			job.put("title", URLEncoder.encode(notice.getNoticeTitle(), "UTF-8"));
			// 날짜데이터는 반드시 toString()으로 문자열로 바꿔서 저장할 것 => 날짜 그대로 저장하면 뷰에 출력안됨
			job.put("date", notice.getNoticeDate().toString());
			
			jarr.add(job);
		}
		
		// 전송용 객체를 준비함
		JSONObject sendJson = new JSONObject();
		sendJson.put("nlist", jarr);
		
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
