package org.ict.testjpa2.board.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class) // JUnit4 에서는 @RunWith 임, JUnit5에서는 ExtendWith 임
@WebMvcTest(BoardController.class) // () 안에 작성된 클래스만 테스트를 진행함
// @WebMvcTest(controllers={BoardController.class, ReplyController.class})
// @WebMvcTest => @Controller, @RestController, @RestControllerAdvice 등 컨트롤러 관련 Bean 들 모두 로드함
class BoardControllerTest {

    @Autowired // 의존성 주입
    private MockMvc mockMvc; // Controller API 테스트 용도인 객체임
    // perform() 메소드를 사용해서 컨트로러의 동작을 확인함, andExpect(), andDo(), andReturn() 메소드들도 같이 사용할 수 있음

    // return 타입은 void, 접근제한자는 public | default 상관없음 : 이 클래스에서 테스트 구동하고 끝내기 때문임
    // 메소드명도 마음대로 이름 지어줌
    @Test   // 테스트용 메소드에 반드시 표기함
    public void testTop3() {
        System.out.println("testTop3 : test 시작 #################");

    }

    @Test   // 테스트용 메소드에 반드시 표기함
    public void testList() {
        System.out.println("testList : test 시작 #################");

    }

    @Test   // 테스트용 메소드에 반드시 표기함
    public void testBoard() {
        System.out.println("testBoard : test 시작 #################");

    }

}