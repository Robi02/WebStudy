package com.spring.yesorno.validator.members;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.spring.yesorno.dto.MemberDto;

public class MemberChangeInfoValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return MemberChangeInfoValidator.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final MemberDto memberDto = (MemberDto)target;
		final String memberNickname = memberDto.getMemberNickname();

		// memberNickname ��� Ȯ��
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "memberNickname", "error.required");

		// memberNickname ���� Ȯ��
		if (memberNickname.length() > 16) {
			errors.rejectValue("memberNickname", "error.lengthOver");
		}
	}
}
