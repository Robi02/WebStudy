package com.spring.yesorno.dao;

import java.util.ArrayList;

import org.springframework.dao.DataAccessException;

import com.spring.yesorno.dto.VoteProgressDto;

public interface IVoteProgressDao {

	public ArrayList<VoteProgressDto> selectVoteProgressList(ArrayList<Integer> voteBoardIdList) throws DataAccessException; // 특정 게시글 투표결과들 반환
}
