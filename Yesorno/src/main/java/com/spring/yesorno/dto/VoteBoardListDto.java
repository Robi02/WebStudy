package com.spring.yesorno.dto;

/** ��ǥ �Խñ� ��� */
public class VoteBoardListDto {

	public enum EnumVoteBoardState {
		BS_UNKOWN,
		BS_PROGRESSING,			// ������
		BS_IMMINENT,			// �ӹ�
		BS_ENDED,				// ����
	}
	
	private String voteBoardTitle;				// ��ǥ�Խñ� ����
	private String voteBoardImageURL;			// ��ǥ�Խñ� �̹��� ���
	private EnumVoteBoardState voteBoardState;	// ��ǥ�Խñ� ����
	private int voteAgreeCnt;					// ��ǥ�Խñ� ���� ����
	private int voteDisagreeCnt;				// ��ǥ�Խñ� �ݴ� ����

	public String getVoteBoardTitle() {
		return voteBoardTitle;
	}
	
	public void setVoteBoardTitle(String voteBoardTitle) {
		this.voteBoardTitle = voteBoardTitle;
	}
	
	public String getVoteBoardImageURL() {
		return voteBoardImageURL;
	}
	
	public void setVoteBoardImageURL(String voteBoardImageURL) {
		this.voteBoardImageURL = voteBoardImageURL;
	}
	
	public EnumVoteBoardState getVoteBoardState() {
		return voteBoardState;
	}
	
	public void setVoteBoardState(EnumVoteBoardState voteBoardState) {
		this.voteBoardState = voteBoardState;
	}
	
	public int getVoteAgreeCnt() {
		return voteAgreeCnt;
	}
	
	public void setVoteAgreeCnt(int voteAgreeCnt) {
		this.voteAgreeCnt = voteAgreeCnt;
	}
	
	public int getVoteDisagreeCnt() {
		return voteDisagreeCnt;
	}
	
	public void setVoteDisagreeCnt(int voteDisagreeCnt) {
		this.voteDisagreeCnt = voteDisagreeCnt;
	}
}
