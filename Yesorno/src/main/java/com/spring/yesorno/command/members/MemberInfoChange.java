package com.spring.yesorno.command.members;

import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.Errors;

import com.spring.yesorno.dto.MemberDto;

public class MemberInfoChange implements IMemberCommand {

	//@Autowired Validator memberRegistrationValidator;
	//@Autowired MemberDao memberDao;
	
	public boolean execute(MemberDto memberDto, HttpServletResponse response, Errors errors) {		
		boolean cmdResult = false;
		/*
		memberRegistrationValidator.validate(memberDto, errors);
		if (!errors.hasErrors()) {
			try {
				memberDto.setMemberGradeId(MemberDto.EnumMemberGrade.MG_NORMAL);
				memberDto.setMemberJoinDate(new Date());
				if (memberDao.memberRegistration(memberDto) == 1) {
					cmdResult = true;
				}
			} catch (Exception e) {
				e.printStackTrace();	
			}
		}
		*/
		return cmdResult;
	}
}
