package com.spring.yesorno.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.spring.yesorno.dto.MemberDto;

public class MemberDao {

	@Autowired private SqlSessionTemplate sqlSession;
	
	// ȸ�� ����
	public int memberRegistration(MemberDto memberDto) {
 		return sqlSession.insert("memberRegistration", memberDto);
	}
	
	// ȸ�� Ż��
	public int memberDeregistration(MemberDto memberDto) {
		return sqlSession.update("memberDeregistration", memberDto);
	}
}
