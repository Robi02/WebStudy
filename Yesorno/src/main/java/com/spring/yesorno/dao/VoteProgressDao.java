package com.spring.yesorno.dao;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.spring.yesorno.dto.VoteProgressDto;

public class VoteProgressDao implements IVoteProgressDao {

	@Autowired SqlSessionTemplate sqlSession;
	
	// 특정 게시글 투표결과들 반환
	public ArrayList<VoteProgressDto> selectVoteProgressList(ArrayList<Integer> voteBoardIdList) throws DataAccessException {
		IVoteProgressDao voteProgressDao = sqlSession.getMapper(IVoteProgressDao.class);
		return voteProgressDao.selectVoteProgressList(voteBoardIdList);
	}
}
