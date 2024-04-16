package org.ict.first.member.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

@Component("kakaoLoginAuth")
public class KakaoLoginAuth {
	//자바스크립트 앱키
	private final static String K_CLIENT_ID = 
			"4bde9909d444437dead06610384e9dbe";
	//카카오 개발자에 등록된 로그인용 redirect uri
	private final static String K_REDIRECT_URI = 
			"http://localhost:8888/first/kcallback.do";
	
	//카카오 로그인 요청시 카카오 로그인 페이지로 이동 url 리턴용
	public String getAuthorizationUrl(HttpSession session) {
		String kakaoURL = 
				"https://kauth.kakao.com/oauth/authorize?"
				+ "client_id=" + K_CLIENT_ID 
				+ "&redirect_uri=" + K_REDIRECT_URI
				+ "&response_type=code";
		
		return kakaoURL;
	}
	
	//카카오 api 서버 접근 토큰 얻어와서 리턴용
	public JsonNode getAccessToken(String authorize_code) {
		final String RequestURL = 
				"https://kauth.kakao.com/oauth/token";
		final List<NameValuePair> postParams = 
				new ArrayList<NameValuePair>();
		
		postParams.add(new BasicNameValuePair(
				"grant_type", "authorization_code"));
		postParams.add(new BasicNameValuePair(
				"client_id", K_CLIENT_ID));
		postParams.add(new BasicNameValuePair(
				"redirect_uri", K_REDIRECT_URI));
		postParams.add(new BasicNameValuePair(
				"code", authorize_code));
		
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost post = new HttpPost(RequestURL);
		
		JsonNode returnNode = null;
		
		try {
			post.setEntity(new UrlEncodedFormEntity(postParams));
			final HttpResponse response = client.execute(post);
			ObjectMapper mapper = new ObjectMapper();
			returnNode = mapper.readTree(response.getEntity().getContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnNode;
	}
	
	//사용자 정보 얻어오기
	public static JsonNode getKakaoUserInfo(
			JsonNode accessToken) {
		//url 버전 변경됨 v2로
		final String RequestURL = 
				"https://kapi.kakao.com/v2/user/scopes";
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost post = new HttpPost(RequestURL);
		
		//add header
		//** 주의 : accessToken 값은 JsonNode 형이어야 함
		// Bearer 뒤에 스페이스바 꼭 추가
		post.addHeader("Authorization", "Bearer " + accessToken);
		
		JsonNode returnNode = null;
		
		try {
			final HttpResponse response = 
					client.execute(post);
			ObjectMapper mapper = new ObjectMapper();
			returnNode = mapper.readTree(
					response.getEntity().getContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnNode;
	}
	
	//카카오 로그아웃 처리용
	public void logout() {
		String url = "https://kauth.kakao.com/oauth/logout?"
				+ "client_id=" + K_CLIENT_ID
				+ "&logout_redirect_uri=http://localhost:8888/first/logout.do";		
	}
	
}






