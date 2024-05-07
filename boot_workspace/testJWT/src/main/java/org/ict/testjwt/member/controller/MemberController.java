package org.ict.testjwt.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.testjwt.config.ApplicationConfig;
import org.ict.testjwt.jwt.JwtTokenUtill;
import org.ict.testjwt.member.jpa.entity.MemberEntity;
import org.ict.testjwt.member.model.dto.MemberDto;
import org.ict.testjwt.member.model.service.MemberService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping/*("/members")*/
@RequiredArgsConstructor
@CrossOrigin     //리액트 애플리케이션(포트가 다름)의 자원 요청을 처리하기 위함
public class MemberController {

    private final MemberService memberService;
    private final ApplicationConfig passwordEncode;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody MemberDto memberDto) {
        MemberDto member = memberService.selectMember(memberDto.getUserId());
        Map<String, String> token = new HashMap<>();

        if(passwordEncode.matches(memberDto.getUserPwd(), member.getUserPwd())) {
            // 로그인 토큰 생성
            String accessToken = JwtTokenUtill.createToken();
            String refreshToken = JwtTokenUtill.createRefreshToken();
            token.put("access_token", accessToken);
            token.put("refresh_token", refreshToken);
            // 토큰을 db에 저장할 수도 있음, Radis 에 저장할 수도 있음
            // memberService.insertToken(refreshToken);

        } else {
            // 로그인 실패 상태코드 리턴
            token.put("error", "888");
        }
        return ResponseEntity.ok().body(token);
    }

}
