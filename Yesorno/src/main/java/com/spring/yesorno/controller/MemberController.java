package com.spring.yesorno.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.yesorno.command.members.IMemberCommand;
import com.spring.yesorno.dto.MemberDto;

@Controller
@RequestMapping("/members")
public class MemberController {
	
	@Autowired IMemberCommand memberRegistration;	// 회원 가입
	@Autowired IMemberCommand memberUpdate;			// 회원정보 수정
	
	// 회원 가입
	@RequestMapping(method = RequestMethod.POST)
	public String memberCreate(@ModelAttribute("registerMember")MemberDto memberDto, Errors errors) {
		String resultURL = "error";
		boolean cmdResult = false;

		cmdResult = memberRegistration.execute(memberDto, errors);

		if (cmdResult == false || errors.hasErrors()) {
			resultURL = "members/register"; // Error
		} else {
			resultURL = "members/register_done"; // Okay
		}

		return resultURL;
	}

	// 회원 정보 조회
	@RequestMapping(method = RequestMethod.GET)
	public String memberSelect() {
		return "member";
	}	
	
	// 회원 정보 수정
	@RequestMapping(method = RequestMethod.PUT)
	public String memberUpdate() {
		return "member";
	}
	
	// 회원 탈퇴
	@RequestMapping(method = RequestMethod.DELETE)
	public String memberDelete() {
		return "member";
	}
	
	// 회원 가입 페이지
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String memberRegistPaging(@ModelAttribute("registerMember")MemberDto memberDto, Errors errors) {
		return "members/register";
	}
}
