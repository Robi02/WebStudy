package com.spring.yesorno.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.yesorno.command.members.IMemberCommand;
import com.spring.yesorno.command.members.MemberChangeInfo;
import com.spring.yesorno.dto.MemberDto;

@Controller
@RequestMapping("/members")
public class MemberController {
	
	@Autowired IMemberCommand memberRegistration;	// ȸ�� ����
	@Autowired IMemberCommand memberChangeInfo;		// ȸ������ ����
	@Autowired IMemberCommand memberLogin;			// �α���
	@Autowired IMemberCommand memberLogout;			// �α׾ƿ�
	
	@Autowired IMemberCommand memberChangeInfoPage;	// ȸ������ ���� ������
	
	///////////////	///////////////	///////////////	///////////////	///////////////	///////////////	///////////////
	
	// ȸ�� ���� (C)
	@RequestMapping(method = RequestMethod.POST)
	public String memberRegistration(@ModelAttribute("registerMember")MemberDto memberDto, HttpServletResponse response, Errors errors) {
		String resultURL = "error";
		boolean cmdResult = false;

		cmdResult = memberRegistration.execute(memberDto, response, errors);

		if (cmdResult == false || errors.hasErrors()) {
			resultURL = "members/register"; // Error
		} else {
			resultURL = "members/register_done"; // Okay
		}

		return resultURL;
	}

	// ȸ�� ���� ��ȸ (R)
	@RequestMapping(method = RequestMethod.GET)
	public String memberSelect() {
		return "member";
	}	
	
	// ȸ�� ���� ���� (U)
	@RequestMapping(method = RequestMethod.PUT)
	public String memberUpdate(@ModelAttribute("changeInfoMember")MemberDto memberDto, @CookieValue("memberToken")String memberToken, HttpServletResponse response, Errors errors) {
		String resultURL = "error";
		boolean cmdResult = false;

		memberDto.setMemberToken(memberToken);
		cmdResult = memberChangeInfo.execute(memberDto, response, errors);
		
		if (cmdResult == false || errors.hasErrors()) {
			resultURL = "/members/changeinfo"; // Error
		} else {
			resultURL = "redirect:/main"; // Okay
		}
		
		return resultURL;
	}
	
	// ȸ�� Ż�� (D)
	@RequestMapping(method = RequestMethod.DELETE)
	public String memberDelete() {
		return "member";
	}
	
	///////////////	///////////////	///////////////	///////////////	///////////////	///////////////	///////////////
	
	// �α���
	@RequestMapping(value ="/login", method = RequestMethod.POST)
	public String memberLogin(@ModelAttribute("loginMember")MemberDto memberDto, HttpServletResponse response, Errors errors) {
		String resultURL = "error";
		boolean cmdResult = false;

		cmdResult = memberLogin.execute(memberDto, response, errors);
		
		if (cmdResult == false || errors.hasErrors()) {
			resultURL = "/members/login"; // Error
		} else {
			resultURL = "redirect:/main"; // Okay
		}
		
		return resultURL;
	}
	
	// �α׾ƿ�
	@RequestMapping(value ="/logout", method = RequestMethod.GET)
	public String memberLogout(@ModelAttribute("loginMember")MemberDto memberDto, @CookieValue("memberToken")String memberToken, HttpServletResponse response, Errors errors) {
		String resultURL = "error";
		boolean cmdResult = false;
		//MemberDto memberDto = new MemberDto();

		memberDto.setMemberToken(memberToken);
		cmdResult = memberLogout.execute(memberDto, response, errors);
		
		if (cmdResult == false || errors.hasErrors()) {
			resultURL = "redirect:/main"; // Error
		} else {
			resultURL = "redirect:/main"; // Okay
		}
		
		return resultURL;
	}

	///////////////	///////////////	///////////////	///////////////	///////////////	///////////////	///////////////
	
	// ȸ�� ���� ������
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String memberRegistrationPage(@ModelAttribute("registerMember")MemberDto memberDto, Errors errors) {
		return "members/register";
	}
	
	// �α��� ������
	@RequestMapping(value ="/login", method = RequestMethod.GET)
	public String memberLoginPage(@ModelAttribute("loginMember")MemberDto memberDto, Errors errors) {
		return "members/login";
	}
	
	// ȸ������ ���� ������
	@RequestMapping(value ="/changeinfo", method = RequestMethod.GET)
	public String memberChangeInfoPage(@ModelAttribute("changeInfoMember")MemberDto memberDto, @CookieValue("memberToken")String memberToken, HttpServletResponse response, Errors errors) {
		String resultURL = "error";
		boolean cmdResult = false;

		memberDto.setMemberToken(memberToken);
		cmdResult = memberChangeInfoPage.execute(memberDto, response, errors);
		
		if (cmdResult == false || errors.hasErrors()) {
			resultURL = "redirect:/main"; // Error
		} else {
			resultURL = "members/changeinfo"; // Okay
		}
		
		return resultURL;
	}
}
