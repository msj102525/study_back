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

import notice.model.service.NoticeService;
import notice.model.vo.Notice;

/**
 * Servlet implementation class NotcieNewTop3Servlet
 */
@WebServlet("/ntop3")
public class NotcieNewTop3Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NotcieNewTop3Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ajax 통신으로 최근 공지글 3개 조회 요청 처리용 컨트롤러
		
		ArrayList<Notice> list = new NoticeService().selectNewTop3();
		
		JSONArray jarr = new JSONArray();
		
		for(Notice notice : list) {
			JSONObject job = new JSONObject();
			
			job.put("no", notice.getNoticeNo());
			//한글 데이터는 반드시 인코딩 처리함
			job.put("title", URLEncoder.encode(notice.getNoticeTitle(), "utf-8"));
			//날짜데이터는 반드시 toString() 으로 바꾸어 저장해야 함 => 날짜 그대로 담으면 뷰에서 출력안됨
			job.put("date", notice.getNoticeDate().toString());
			
			jarr.add(job);
		}
		
		JSONObject sendJson = new JSONObject();
		sendJson.put("nlist", jarr);
		
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
