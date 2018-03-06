package com.spring.yesorno.dto;

import java.util.Date;

public class MemberDto {

	public enum EnumMemberGradeId {
		MG_ADMIN(1),
		MG_SUBADMIN(2),
		MG_NORMAL(3),
		MG_UNIDENTIFIED(4),
		MG_BANNED(5),
		MG_RETIRED(6);
		
		private int value;
		
		EnumMemberGradeId(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
	}

	private int memberId;				// INT				PK			��� ��ȣ
	private int memberGradeId;			// INT				FK,NN		��� ��� �ڵ�
	private String memberEmail;			// VARCHAR(128)		UQ,NN		��� �̸���
	private String memberNickname;		// VARCHAR(16)		UQ,NN		��� �г���
	private Date memberJoinDate;		// DATETIME			NN			��� ��������
	private Date memberLastLoginDate;	// DATETIME						��� ������ �α��� ����
	private String memberToken;			// VARCHAR(300)					��� ��ū
	
	public int getMemberId() {
		return memberId;
	}
	
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getMemberGradeId() {
		return memberGradeId;
	}
	
	public void setMemberGradeId(int memberGradeId) {
		this.memberGradeId = memberGradeId;
	}
	
	public String getMemberEmail() {
		return memberEmail;
	}
	
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	
	public String getMemberNickname() {
		return memberNickname;
	}
	
	public void setMemberNickname(String memberNickname) {
		this.memberNickname = memberNickname;
	}
	
	public Date getMemberJoinDate() {
		return memberJoinDate;
	}
	
	public void setMemberJoinDate(Date memberJoinDate) {
		this.memberJoinDate = memberJoinDate;
	}
	
	public Date getMemberLastLoginDate() {
		return memberLastLoginDate;
	}
	
	public void setMemberLastLoginDate(Date memberLastLoginDate) {
		this.memberLastLoginDate = memberLastLoginDate;
	}
	
	public String getMemberToken() {
		return memberToken;
	}
	
	public void setMemberToken(String memberToken) {
		this.memberToken = memberToken;
	}
}