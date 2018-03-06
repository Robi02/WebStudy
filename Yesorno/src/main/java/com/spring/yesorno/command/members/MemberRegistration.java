package com.spring.yesorno.command.members;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.spring.yesorno.dao.MemberDao;
import com.spring.yesorno.dto.MemberDto;

public class MemberRegistration implements IMemberCommand {

	@Autowired private Validator memberRegistrationValidator;
	@Autowired private MemberDao memberDao;

	public boolean execute(MemberDto memberDto, Errors errors) {		
		boolean cmdResult = false;
		
		memberRegistrationValidator.validate(memberDto, errors);
		if (!errors.hasErrors()) {
			try {
				memberDto.setMemberGradeId(MemberDto.EnumMemberGradeId.MG_NORMAL.getValue());
				memberDto.setMemberJoinDate(new Date());
				cmdResult = (memberDao.memberRegistration(memberDto) == 1 ? true : false);
			} catch (Exception e) {
				e.printStackTrace();
				cmdResult = false;
			}
		}

		return cmdResult;
	}
}
