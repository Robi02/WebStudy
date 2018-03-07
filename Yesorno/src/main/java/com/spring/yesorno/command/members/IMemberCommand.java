package com.spring.yesorno.command.members;

import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.Errors;

import com.spring.yesorno.dto.MemberDto;

public interface IMemberCommand {
	
	public boolean execute(MemberDto memberDto, HttpServletResponse response, Errors errors);
}
