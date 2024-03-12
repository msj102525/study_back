package test.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import test.model.vo.Position;

/**
 * Servlet implementation class MapPositionServlet
 */
@WebServlet("/positions")
public class MapPositionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MapPositionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 카카오 지도에 표시할 마커 데이터 응답 데스트 컨트롤러
		ArrayList<Position> list = new ArrayList<Position>();
		
		list.add(new Position("카카오", 37.4923615, 127.0292881));
		list.add(new Position("생태연못", 37.4926615, 127.0293881));
		list.add(new Position("텃받", 37.4926615, 127.0295881));
		list.add(new Position("근린공원", 37.4929615, 127.0299881));
		
		System.out.println(list);
		
		JSONArray jarr = new JSONArray();			
		for(Position p : list) {
			JSONObject job = new JSONObject();
			job.put("title", URLEncoder.encode(p.getTitle(), "utf-8"));
			job.put("lat", p.getLatitude());
			job.put("lng", p.getLongitude());
			
			jarr.add(job);		
		}
		
		JSONObject sendjson = new JSONObject();
		sendjson.put("list", jarr);
		System.out.println(sendjson.toJSONString());
		
		RequestDispatcher view = request.getRequestDispatcher("views/testapi/mapExample6.jsp");
		request.setAttribute("sendjson", sendjson.toJSONString());
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
