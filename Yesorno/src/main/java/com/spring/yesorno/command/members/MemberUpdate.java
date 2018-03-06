package com.spring.yesorno.command.members;

import org.springframework.validation.Errors;

import com.spring.yesorno.dto.MemberDto;

public class MemberUpdate implements IMemberCommand {

	//@Autowired Validator memberRegistrationValidator;
	//@Autowired MemberDao memberDao;
	
	public boolean execute(MemberDto memberDto, Errors errors) {		
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
