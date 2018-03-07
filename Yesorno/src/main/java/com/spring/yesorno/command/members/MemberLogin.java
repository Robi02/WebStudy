package com.spring.yesorno.command.members;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.spring.yesorno.dao.MemberDao;
import com.spring.yesorno.dto.MemberDto;
import com.spring.yesorno.util.jwt.JwtMemberService;

public class MemberLogin implements IMemberCommand {

	@Autowired private JwtMemberService jwtMemberService;
	@Autowired private Validator memberLoginValidator;
	@Autowired private MemberDao memberDao;

	@Override
	public boolean execute(MemberDto memberDto, HttpServletResponse response, Errors errors) {
		boolean cmdResult = false;
		
		memberLoginValidator.validate(memberDto, errors);
		if (!errors.hasErrors()) {
			try {
				// 로그인 이메일 정보 셀렉트
				cmdResult = (memberDao.memberSelect(memberDto) != null ? true : false);

				if (cmdResult) {
					// 해당 계정 로그인정보 업데이트
					memberDto.setMemberLastLoginDate(new Date());
					cmdResult = (memberDao.memberLoginUpdate(memberDto) == 1 ? true : false);

					// 로그인 성공: 맴버토큰을 생성하여 쿠키에 담음
					if (cmdResult) {
						String memberToken = jwtMemberService.createMemberToken(memberDto, errors);
						response.addCookie(new Cookie("memberToken", memberToken));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				cmdResult = false;
			}
		}

		return cmdResult;
	}
}
