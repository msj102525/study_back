package org.myweb.first.member.controller;

import org.myweb.first.member.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
	
	// 서비스 연결  처리 : 자동 DI 처리
	@Autowired
	private MemberService memberService;
	// 스프링에서는 부모 인터페이스 타입으로 레퍼런스 선언함 (다형성 이용함)
	// 실행시 후손의 오버라이딩된 메소드를 연결 실행하게 됨 (동적 바인딩 이라고 함)
}
