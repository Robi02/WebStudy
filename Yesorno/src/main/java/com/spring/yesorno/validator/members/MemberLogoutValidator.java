package com.spring.yesorno.validator.members;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.spring.yesorno.dto.MemberDto;

public class MemberLogoutValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return MemberLogoutValidator.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final MemberDto memberDto = (MemberDto)target;
		final String memberToken = memberDto.getMemberToken();

		if (memberToken == null || memberToken.length() == 0) {
			errors.reject("error.invailedToken");
		}
	}
}
