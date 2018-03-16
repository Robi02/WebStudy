package com.spring.yesorno.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import com.spring.yesorno.command.VoteBoardWriteCmd;
import com.spring.yesorno.dao.MemberDao;
import com.spring.yesorno.dao.VoteBoardDao;
import com.spring.yesorno.dao.VoteProgressDao;
import com.spring.yesorno.dto.MemberDto;
import com.spring.yesorno.dto.VoteBoardDto;
import com.spring.yesorno.dto.VoteBoardListDto;
import com.spring.yesorno.dto.VoteProgressDto;
import com.spring.yesorno.exception.MemberException;
import com.spring.yesorno.util.jwt.JwtMemberAuth;

public class VoteBoardService {

	final String ImageRepoURL = "/resources/images";
	final String DefaultImageURL = "/resources/images/defaultImg.png";	// 기본 표시 이미지 경로
	final int BoardPerPage;												// 한 페이지에 보여줄 게시글 개수
	final long VoteBoardImminentMillisecond = 1000 * 60 * 60 * (24);	// 게시글 임박 상태: 24시간 전
	final float VoteBoardImminentVoterPercent = 0.95f;					// 게시글 임박 상태: 95% 투표

	private Log log = LogFactory.getLog(VoteBoardService.class);

	@Autowired private MemberDao memberDao;
	@Autowired private VoteBoardDao voteBoardDao;
	@Autowired private VoteProgressDao voteProgressDao;
	@Autowired private JwtMemberAuth jwtMemberAuth;
	
	// 생성자
	public VoteBoardService(int dataPerPage) {
		BoardPerPage = dataPerPage;
	}
	
	// 투표 게시글 리스팅
	public boolean voteBoardListing(HttpServletRequest request, int page, Model model) {
		boolean svcResult = true;
		ArrayList<VoteBoardListDto> voteBoardDtoList = new ArrayList<VoteBoardListDto>();
		
		try {
			// 게시글 리스트 가져오기
			ArrayList<VoteBoardDto> voteBoardList = null;
			int begin = (page - 1) * BoardPerPage; // 시작 게시글 위치
	
	 		if ((voteBoardList = voteBoardDao.selectVoteBoardList(begin, BoardPerPage)) == null) {
	 			return false;
	 		}
	 		
	 		// 게시글에 연관된 투표 진행상황 가져오기
	 		ArrayList<VoteProgressDto> voteProgressList = null;	// 특정 게시글의 투표 결과들
	 		ArrayList<Integer> boardIdList = new ArrayList<Integer>();
	 		
	 		for (VoteBoardDto voteBoard : voteBoardList) {
	 			int boardId = voteBoard.getVoteBoardId();
	 			if (!boardIdList.contains(boardId)) {
	 				boardIdList.add(boardId);
	 			}
	 		}

	 		voteProgressList = voteProgressDao.selectVoteProgressList(boardIdList);

	 		// 게시글 리스트 DTO 생성 (DB조인 작업을 서버에서 대리 수행)
	 		for (VoteBoardDto voteBoard : voteBoardList) {
	 			
	 			VoteBoardListDto boardDto = new VoteBoardListDto();	// 특정 게시글 DTO, voteBoardDtoList에 추가하여 반환
	 			
	 			// 게시글 기본항목 초기화
	 			boardDto.setVoteBoardTitle(voteBoard.getVoteBoardTitle());
	 			String voteBoardImgURL = voteBoard.getVoteBoardImageURL();
	 			String realPath = request.getSession().getServletContext().getRealPath("");
	 			if (voteBoardImgURL == null || voteBoardImgURL.length() == 0) {
	 				voteBoardImgURL = (realPath + DefaultImageURL);
	 			} else {
	 				voteBoardImgURL = (realPath + voteBoardImgURL);
	 				// 파일 존재 확인
	 				File imgFile = new File(voteBoardImgURL);
	 				if (!imgFile.exists()) {
	 					voteBoardImgURL = (realPath + DefaultImageURL);
	 				}
	 			}

	 			boardDto.setVoteBoardImageURL(voteBoardImgURL);
 				boardDto.setVoteAgreeCnt(0);
 				boardDto.setVoteDisagreeCnt(0);
 				boardDto.setVoteBoardState(VoteBoardListDto.EnumVoteBoardState.BS_PROGRESSING);

	 			// 게시글 투표 결과 설정
 				int totalVoteCnt = 0;

	 			if (voteProgressList != null) {
	 				int agreeCnt = 0, disagreeCnt = 0;
	 				
	 				for (VoteProgressDto voteProgress : voteProgressList) {
	 					if (voteBoard.getVoteBoardId() == voteProgress.getVoteBoardId() ) {
		 					if (voteProgress.getVoteResult() > 0) {
		 						++agreeCnt;
		 					} else if (voteProgress.getVoteResult() < 0) {
		 						++disagreeCnt;
		 					} else {
		 						++totalVoteCnt;
		 					}
	 					}
		 			}
	 				// 투표 결과 종합
	 				boardDto.setVoteAgreeCnt(agreeCnt);
	 				boardDto.setVoteDisagreeCnt(disagreeCnt);
	 				totalVoteCnt = agreeCnt + disagreeCnt;
	 			}
	 			
	 			// 게시글 상태 설정
	 			final long endDate = voteBoard.getVoteEndDate().getTime();
	 			final long today = new Date().getTime();
	 			final long timeLeft = endDate - today;
	 			final int voteLimit = voteBoard.getVoteEndCnt();
	 			final int voteImmerseCnt = (int)(VoteBoardImminentVoterPercent * voteLimit);

	 			if (timeLeft <= 0 || totalVoteCnt >= voteLimit) { // 투표 마감
	 				boardDto.setVoteBoardState(VoteBoardListDto.EnumVoteBoardState.BS_ENDED);
	 			} else if (timeLeft <= VoteBoardImminentMillisecond || totalVoteCnt >= voteImmerseCnt) { // 마감 임박 
	 				boardDto.setVoteBoardState(VoteBoardListDto.EnumVoteBoardState.BS_IMMINENT);
	 			} else { // 진행중
	 				boardDto.setVoteBoardState(VoteBoardListDto.EnumVoteBoardState.BS_PROGRESSING);
	 			}

	 			// Model에 담아보낼 리스트에 추가
	 			svcResult |= voteBoardDtoList.add(boardDto);
	 		}
		} catch (DataAccessException e) {
			log.debug(e.getMessage());
			svcResult = false;
		}

		model.addAttribute("voteBoardList", voteBoardDtoList);

		return svcResult;
	}

	// 투표 게시글 쓰기
	public boolean voteBoardWrite(String memberToken, HttpServletRequest request, VoteBoardWriteCmd cmd, Errors errors) {
		boolean svcResult = false;
		MemberDto writeMemberDto = null;
		VoteBoardDto writeVoteBoardDto = new VoteBoardDto();
		
		try {
			// 회원토큰 인증
			if ((writeMemberDto = jwtMemberAuth.getMemberFromToken(memberToken)) != null) {}
			else {
				svcResult = false;
				throw new MemberException(MemberException.EnumMemberExceptionType.ET_TOKEN_VALIDATION_FAIL);
			}

			// 게시글 추가
			MultipartFile thumbnailFile = cmd.getVoteBoardThumbnailImage();
			Date curDate = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(curDate);
			String todayFolder = null;
			String imgName = null;
			String imageURLforDB = DefaultImageURL;
			String imageURLforFolder = DefaultImageURL;
			
			if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
				String realPath = request.getSession().getServletContext().getRealPath("");
				todayFolder = String.format("%s/%d/%d/%d", ImageRepoURL, calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE));
				imgName = String.format("/%d_%d_%s", curDate.getTime(), writeMemberDto.getMemberId(), cmd.getVoteBoardThumbnailImage().getOriginalFilename());
				imageURLforDB = (todayFolder + imgName);
				imageURLforFolder = (realPath + todayFolder);
			}

			writeVoteBoardDto.setVoteBoardId(0);
			writeVoteBoardDto.setWriterMemberId(writeMemberDto.getMemberId());
			writeVoteBoardDto.setVoteBoardWrittenDate(curDate);
			writeVoteBoardDto.setVoteBoardViewCnt(0);
			writeVoteBoardDto.setVoteBoardTitle(cmd.getVoteBoardTitle());
			writeVoteBoardDto.setVoteBoardContent(cmd.getVoteBoardContent());
			writeVoteBoardDto.setVoteBoardImageURL(imageURLforDB);
			writeVoteBoardDto.setVoteEndCnt(cmd.getVoteEndCnt());
			//writeVoteBoardDto.setVoteEndDate(cmd.getVoteEndDate());
			writeVoteBoardDto.setVoteEndDate(curDate); // test
			
			if (voteBoardDao.insertVoteBoard(writeVoteBoardDto) != 1) {
				return false; // 게시글 추가 실패
			} else {
				svcResult = true;
			}
			
			// 이미지파일 저장
			if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
				File imgFolder = new File(imageURLforFolder);
				imgFolder.mkdirs();
				File imgFile = new File(imageURLforFolder + imgName);
				thumbnailFile.transferTo(imgFile);
				svcResult = true;
			}
		} catch (DataAccessException e) {
			log.debug(e.getMessage());
			svcResult = false;
		} catch (IOException ioe) {
			log.debug(ioe.getMessage());
			svcResult = false;			
		}
		
		return svcResult;
	}
}
