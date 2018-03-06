package com.spring.yesorno.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.spring.yesorno.dto.MemberDto;

public class MemberDao {

	@Autowired private SqlSessionTemplate sqlSession;
	
	// È¸¿ø °¡ÀÔ
	public int memberRegistration(MemberDto memberDto) {
 		return sqlSession.insert("memberRegistration", memberDto);
	}
	
	// È¸¿ø Å»Åð
	public int memberDeregistration(MemberDto memberDto) {
		return sqlSession.update("memberDeregistration", memberDto);
	}
}
