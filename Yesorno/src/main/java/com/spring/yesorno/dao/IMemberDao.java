package com.spring.yesorno.dao;

import com.spring.yesorno.dto.MemberDto;

public interface IMemberDao {

	public int memberInsert(MemberDto memberDto) throws Exception; 			// ȸ�� ����
	public MemberDto memberSelect(MemberDto memberDto) throws Exception;	// ȸ��  ��ȸ
	public int memberInfoUpdate(MemberDto memberDto) throws Exception;		// ȸ������ ����
	public int memberLoginUpdate(MemberDto memberDto) throws Exception;		// ȸ�� �α��� ���� ������Ʈ
}
