package org.ict.test_security.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.test_security.member.jpa.entity.MemberEntity;
import org.ict.test_security.member.model.dto.MemberDto;
import org.ict.test_security.member.model.service.MemberService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@CrossOrigin     //리액트 애플리케이션(포트가 다름)의 자원 요청을 처리하기 위함
public class MemberController {
    private final MemberService memberService;

    // 요청 메서드 구현부 ------------------------------------
    @GetMapping("/list")
    public ResponseEntity<ArrayList<MemberDto>> selectList(
            @RequestParam int page, @RequestParam int limit) {
        log.info("members/list" + page + ", " + limit);
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "enrollDate"));
        return new ResponseEntity<>(memberService.selectList(pageable), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<MemberDto> selectNoticeDetail(@PathVariable("userId") String userId){
        log.info("/members/" + userId + "요청");
        return new ResponseEntity<>(memberService.selectMember(userId), HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<MemberDto> insertMember(@RequestBody MemberDto memberDto){
        log.info("members/insert" + memberDto);
        MemberEntity memberEntity = new MemberEntity();
        log.info(memberEntity.getAdminYN());
        memberDto.setSignType("direct");
        memberDto.setAdminYN("N");
        memberDto.setLoginOk("Y");
        return new ResponseEntity<>(memberService.insertMember(memberDto), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateMember(@PathVariable("userId") String userId, @RequestBody MemberDto memberDto){
        log.info("updateMember" + memberDto);
        MemberDto memberDto1 = memberService.selectMember(userId);
        log.info(memberDto1.toString());

        memberDto.setLastModified(new Date(System.currentTimeMillis()));
        memberDto.setSignType(memberDto1.getSignType());
        memberDto.setAdminYN(memberDto1.getAdminYN());
        memberDto.setLoginOk(memberDto1.getLoginOk());

        log.info("updateMember222222222222222222" + memberDto);
        memberService.updateMember2(memberDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteMember(@PathVariable("userId") String userId){
        log.info("deleteMember" + userId);
        memberService.deleteMember(userId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
