package com.boot.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.test.dto.Board;
import com.boot.test.service.TestService;


@RestController
public class TestController {
	
	@Autowired
	private TestService testService;
	
	
//	@RequestMapping("/test")
//	@ResponseBody
	@GetMapping("/test")
	public String welcome() {
		return "Welcome! Spring Boot Project with JSP";
	}
	
	@GetMapping("/home")
	public ModelAndView home(ModelAndView mv) {
		//mv.addObject("name", "홍길동");
		List<Board> list = testService.selectList();
		
		mv.addObject("list", list);		
		mv.setViewName("home");
		return mv;
	}
}
