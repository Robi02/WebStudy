package com.spring.yesorno.command.members;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.spring.yesorno.dto.MemberDto;
import com.spring.yesorno.util.jwt.JwtMemberService;

public class MemberLogout implements IMemberCommand {

	@Autowired private Validator memberLogoutValidator;
	@Autowired JwtMemberService jwtMemberService;

	@Override
	public boolean execute(MemberDto memberDto, HttpServletResponse response, Errors errors) {
		boolean cmdResult = false;
		
		memberLogoutValidator.validate(memberDto, errors);
		
		if (!errors.hasErrors()) {
			if (jwtMemberService.authMemberToken(memberDto.getMemberToken(), errors) != null) {
				// 로그아웃 성공: 토큰 쿠키를 삭제
				Cookie memberCookie = new Cookie("memberToken", null);
				memberCookie.setPath("/");
				memberCookie.setComment("memberToken");
				memberCookie.setMaxAge(0);
				response.addCookie(memberCookie);
				cmdResult = true;
			}
		}

		return cmdResult;
	}
}
