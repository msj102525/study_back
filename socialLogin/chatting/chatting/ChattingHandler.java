package com.kh.spring.chatting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;




/*public class ChattingHandler extends TextWebSocketHandler {*/
public class ChattingHandler extends BinaryWebSocketHandler{
	
	
	private List<WebSocketSession> client=new ArrayList();
	private String fileName;
	
	/*�������� ����Ǹ� ����Ǵ� �޼ҵ�*/
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		//HttpSession requsetSession=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession(false);
		//System.out.println("requestSession : "+requsetSession.getId());
		//System.out.println("open : "+session);
		//System.out.println(session.getRemoteAddress());
		//client.add(session);//������ ����� ����!
		
		
	}
	
	/*���ӵ� Ŭ���̾�Ʈ�� ���� �޼����� �����ϸ� ����Ǵ¸޼ҵ�*/
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) {
		System.out.println("Text : "+session+" : "+message);
		Map<String,String> map=null;
		ObjectMapper mapper=new ObjectMapper();
		try {
			map=mapper.readValue(message.getPayload(), HashMap.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(map);			
		TextMessage tm=null;
		try {
			tm = new TextMessage(mapper.writeValueAsString(map));
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		if(map.get("flag")!=null&&map.get("flag").equals("file"))
		{
			fileName=map.get("msg");
		}
		
		else {
			for(WebSocketSession s:client)
			{	
				/*if(s==session) {continue;}*/
				try {
					s.sendMessage(tm);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
		System.out.println("binary"+ session+" : "+message);		
		String dir="c:\\";
		FileOutputStream fos=new FileOutputStream(new File(dir+session.getId()+"_"+fileName));
		ByteBuffer bb=message.getPayload();
		fos.write(bb.array());
		fos.close();
		ObjectMapper mapper=new ObjectMapper();
		Map<String,String> msg=new HashMap();
		msg.put("msg", "���Ͼ��ε� �Ϸ�");
		session.sendMessage(new TextMessage(mapper.writeValueAsString(msg)));
		
	}

	/*���ӵ� Ŭ���̾�Ʈ�� ������ ���������� ����Ǵ¸޼ҵ�*/
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("close : "+session);
		client.remove(session);
		
	}

	
	
	
	
}
