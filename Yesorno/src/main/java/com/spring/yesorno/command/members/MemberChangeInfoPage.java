package com.spring.yesorno.command.members;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import com.spring.yesorno.dao.MemberDao;
import com.spring.yesorno.dto.MemberDto;
import com.spring.yesorno.util.jwt.JwtMemberService;
import com.spring.yesorno.validator.members.MemberChangeInfoPageValidator;

public class MemberChangeInfoPage implements IMemberCommand {

	@Autowired MemberChangeInfoPageValidator memberChangeInfoPageValidator;
	@Autowired MemberDao memberDao;
	@Autowired JwtMemberService jwtMemberService;
	
	public boolean execute(MemberDto changeinfoMember, HttpServletResponse response, Errors errors) {		
		boolean cmdResult = false;
		
		memberChangeInfoPageValidator.validate(changeinfoMember, errors);
		if (!errors.hasErrors()) {
			String memberEmail = jwtMemberService.authMemberToken(changeinfoMember.getMemberToken(), errors);
			
			// 인증 성공
			if (memberEmail != null) {
				changeinfoMember.setMemberEmail(memberEmail);
				try {
					changeinfoMember.copy(memberDao.memberSelect(changeinfoMember));
					cmdResult = true;
				} catch (Exception e) {
					e.printStackTrace();
					cmdResult = false;
				}
			}
		}

		return cmdResult;
	}
}
