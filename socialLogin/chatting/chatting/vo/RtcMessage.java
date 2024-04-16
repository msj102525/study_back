package com.kh.spring.chatting.vo;

import java.util.List;
import java.util.Map;

import org.springframework.web.socket.WebSocketMessage;

public class RtcMessage{

	private String token;
	private String type;
	private List<String> members;
	private Sdp sdp;
	private Map<String,String> candidate;
	
	public RtcMessage() {
		// TODO Auto-generated constructor stub
	}

	public RtcMessage(String token, String type, Sdp sdp) {
		super();
		this.token = token;
		this.type = type;
		this.sdp = sdp;
	}

	public RtcMessage(String token, String type, Sdp sdp, Map<String, String> candidate) {
		super();
		this.token = token;
		this.type = type;
		this.sdp = sdp;
		this.candidate = candidate;
	}

	
	
	public RtcMessage(String token, String type, List members) {
		super();
		this.token = token;
		this.type = type;
		this.members = members;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Sdp getSdp() {
		return sdp;
	}

	public void setSdp(Sdp sdp) {
		this.sdp = sdp;
	}

	public Map<String, String> getCandidate() {
		return candidate;
	}

	public void setCandidate(Map<String, String> candidate) {
		this.candidate = candidate;
	}

	
	
	public List<String> getMembers() {
		return members;
	}

	public void setMembers(List<String> members) {
		this.members = members;
	}

	@Override
	public String toString() {
		return "RtcMessage [token=" + token + ", type=" + type + ", members=" + members + ", sdp=" + sdp
				+ ", candidate=" + candidate + "]";
	}

	
	
	
	
}
