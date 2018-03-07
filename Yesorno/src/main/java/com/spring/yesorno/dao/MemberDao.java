package com.spring.yesorno.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.spring.yesorno.dto.MemberDto;

public class MemberDao implements IMemberDao {

	@Autowired private SqlSessionTemplate sqlSession;
	
	// ȸ�� ����
	public int memberInsert(MemberDto memberDto) { 			
		IMemberDao memberDao = sqlSession.getMapper(IMemberDao.class);
		return memberDao.memberInsert(memberDto);
	}

	// ȸ�� ��ȸ
	public MemberDto memberSelect(MemberDto memberDto) {
		IMemberDao memberDao = sqlSession.getMapper(IMemberDao.class);
		return memberDao.memberSelect(memberDto);
	}
	
	// ȸ�� �α��� ���� ������Ʈ
	public int memberLoginUpdate(MemberDto memberDto) {
		IMemberDao memberDao = sqlSession.getMapper(IMemberDao.class);
		return memberDao.memberLoginUpdate(memberDto);
	}
}
