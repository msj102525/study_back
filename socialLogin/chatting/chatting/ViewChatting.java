package com.kh.spring.chatting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.kh.spring.chatting.vo.RtcMessage;

public class ViewChatting extends BinaryWebSocketHandler {
	//id로 키값유지
	private static Map<String,WebSocketSession> clients=new HashMap();
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		super.afterConnectionEstablished(session);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		super.afterConnectionClosed(session, status);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) {
		ObjectMapper mapper=new ObjectMapper();
		RtcMessage msg=getMessage(message);
		//session에 msg값 넣기
		session.getAttributes().put("msg", msg);
		//session객체에 값 넣기
		clients.put(msg.getToken(),session);
		sessionChecking();//접속자확인 및 정리
		adminBroadCast();//현재접속자 확인
		System.out.println(clients.size());
		for(Map.Entry<String, WebSocketSession> client : clients.entrySet()) {
			WebSocketSession s=client.getValue();
			System.out.println(msg);
			if(!client.getKey().equals(msg.getToken())) {
				try {
					s.sendMessage(new TextMessage(mapper.writeValueAsString(msg)));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	//전송된 메세지변환
	private RtcMessage getMessage(TextMessage message) {
		ObjectMapper mapper=new ObjectMapper();
		RtcMessage rm=null;
		try {
			rm=mapper.readValue(message.getPayload(), RtcMessage.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rm;
	}
	
	//sessionCheck접속 끊긴세션 삭제
	private void sessionChecking() {
		Iterator<Map.Entry<String, WebSocketSession>> it=clients.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, WebSocketSession> client=it.next();
			if(!client.getValue().isOpen()) {
				it.remove();
			}
		}
		System.out.println("정리 후 : "+clients.size());
	}
	
	//현재접속현황알려주기
	private void adminBroadCast() {
		ObjectMapper mapper=new ObjectMapper();
		RtcMessage adminMsg=new RtcMessage();
		adminMsg.setToken("admin");
		adminMsg.setType("member");
		adminMsg.setMembers(new ArrayList(clients.keySet()));
		
		try {
			for(Map.Entry<String, WebSocketSession> client : clients.entrySet()) {
				client.getValue().sendMessage(new TextMessage(mapper.writeValueAsString(adminMsg)));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
