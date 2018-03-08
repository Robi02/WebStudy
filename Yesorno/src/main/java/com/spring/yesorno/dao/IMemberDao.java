package com.spring.yesorno.dao;

import com.spring.yesorno.dto.MemberDto;

public interface IMemberDao {

	public int memberInsert(MemberDto memberDto) throws Exception; 			// 회원 가입
	public MemberDto memberSelect(MemberDto memberDto) throws Exception;	// 회원  조회
	public int memberInfoUpdate(MemberDto memberDto) throws Exception;		// 회원정보 수정
	public int memberLoginUpdate(MemberDto memberDto) throws Exception;		// 회원 로그인 정보 업데이트
}
