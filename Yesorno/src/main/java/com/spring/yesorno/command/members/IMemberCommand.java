package com.spring.yesorno.command.members;

import org.springframework.validation.Errors;

import com.spring.yesorno.dto.MemberDto;

public interface IMemberCommand {
	
	public boolean execute(MemberDto memberDto, Errors errors);
}
