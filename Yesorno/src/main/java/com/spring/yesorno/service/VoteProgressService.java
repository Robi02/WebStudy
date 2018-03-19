package com.spring.yesorno.service;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.spring.yesorno.dao.VoteProgressDao;
import com.spring.yesorno.dto.MemberDto;
import com.spring.yesorno.dto.VoteProgressDto;
import com.spring.yesorno.util.jwt.JwtMemberAuth;

public class VoteProgressService {

	@Autowired private MemberService memberService;
	@Autowired private VoteProgressDao voteProgressDao;
	
	@Autowired private JwtMemberAuth jwtMemberAuth;
	
	private Log log = LogFactory.getLog(VoteProgressService.class);
	
	public boolean vote(String memberToken, int boardId, String agreeOrDisagree, ArrayList<String> result) {
		boolean svcResult = false;
		boolean isAgree = agreeOrDisagree.equals("agree");
		
		try {
			// ȸ�� ��ū �˻�
			MemberDto voteMember = null;
			if ((voteMember = jwtMemberAuth.getMemberFromToken(memberToken)) == null) {
				result.add("needLogin");
				return false;
			}
			
			// ȸ�� ��� �˻�
			int voteMemberId = voteMember.getMemberId();
			if (!memberService.checkMemberGrade(voteMemberId, MemberDto.EnumMemberGradeId.MG_NORMAL)) { // ȸ�� ��� �̴�
				result.add("memberGradeError");
				return false;
			}
			
			// �ش� �Խñۿ� �̹� ��ǥ�ߴ��� �˻�
			ArrayList<Integer> voteBoardIdList = new ArrayList<Integer>();
			voteBoardIdList.add(boardId);
			ArrayList<VoteProgressDto> voteProgressList = voteProgressDao.selectVoteProgressList(voteBoardIdList);			

			for (VoteProgressDto voteProgressDto : voteProgressList) {
				if (voteMemberId == voteProgressDto.getVoterMemberId()) {
					result.add("alreadyVote");
					return false;
				}
			}
			
			// ��ǥ��� ����
			VoteProgressDto voteProgressDto = new VoteProgressDto();
			voteProgressDto.setVoteProgressId(0);
			voteProgressDto.setVoteBoardId(boardId);
			voteProgressDto.setVoteResult((isAgree ? 1 : -1));
			voteProgressDto.setVoterMemberId(voteMember.getMemberId());
			voteProgressDto.setVoteDate(new Date());
			voteProgressDao.insertVoteProgress(voteProgressDto);
			svcResult = true;

		} catch (DataAccessException e) {
			log.debug(e.getMessage());
			svcResult = false;
		} 

		return svcResult;
	}
}
