package com.spring.yesorno.dao;

import com.spring.yesorno.dto.MemberDto;

public interface IMemberDao {

	public int memberInsert(MemberDto memberDto); 			// ȸ�� ����
	public MemberDto memberSelect(MemberDto memberDto);		// ȸ��  ��ȸ
	public int memberLoginUpdate(MemberDto memberDto);		// ȸ�� �α��� ���� ������Ʈ
}
