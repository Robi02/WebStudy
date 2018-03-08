package com.spring.yesorno.command.members;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.Errors;

import com.spring.yesorno.dao.MemberDao;
import com.spring.yesorno.dto.MemberDto;
import com.spring.yesorno.util.jwt.JwtMemberService;
import com.spring.yesorno.validator.members.MemberChangeInfoValidator;

public class MemberChangeInfo implements IMemberCommand {

	@Autowired MemberChangeInfoValidator memberChangeInfoValidator;
	@Autowired MemberDao memberDao;
	@Autowired JwtMemberService jwtMemberService;
	
	public boolean execute(MemberDto changeinfoMember, HttpServletResponse response, Errors errors) {		
		boolean cmdResult = false;
		
		memberChangeInfoValidator.validate(changeinfoMember, errors);
		if (!errors.hasErrors()) {
			String memberEmail = jwtMemberService.authMemberToken(changeinfoMember.getMemberToken(), errors);

			// 인증 성공
			if (memberEmail != null) {
				changeinfoMember.setMemberEmail(memberEmail);
				try {
					// 닉네임 변경
					cmdResult = (memberDao.memberInfoUpdate(changeinfoMember) == 1 ? true : false);
				} catch (Exception e) {
					e.printStackTrace();
					if (e instanceof DuplicateKeyException) { // 중복 닉네임
						errors.rejectValue("memberNickname", "error.alreadyUsed");
					}
					cmdResult = false;
				}
			}
		}

		return cmdResult;
	}
}
