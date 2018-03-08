package com.spring.yesorno.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.yesorno.dto.MemberDto;

@Controller
public class MainController {

	@RequestMapping("/main")
	public String main(@CookieValue(value = "memberToken", required = false) String memberToken, @ModelAttribute("memberDto")MemberDto memberDto) {
		memberDto.setMemberToken(memberToken);
		
		return "main";
	}
}
