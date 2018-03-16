package com.spring.yesorno.dao;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.spring.yesorno.dto.VoteBoardDto;

public class VoteBoardDao implements IVoteBoardDao{

	@Autowired private SqlSessionTemplate sqlSession;
	
	// 게시글 개수 조회
	public int selectVoteBoardRowCount() throws DataAccessException {
		IVoteBoardDao voteBoardDao = sqlSession.getMapper(IVoteBoardDao.class);
		return voteBoardDao.selectVoteBoardRowCount();
	}
	
	// 게시글 페이지로 조회
	public ArrayList<VoteBoardDto> selectVoteBoardList(int begin, int dataPerPage) throws DataAccessException {
		IVoteBoardDao voteBoardDao = sqlSession.getMapper(IVoteBoardDao.class);
		return voteBoardDao.selectVoteBoardList(begin, dataPerPage);
	}
	
	// 게시글 추가
	public int insertVoteBoard(VoteBoardDto voteBoardDto) throws DataAccessException {
		IVoteBoardDao voteBoardDao = sqlSession.getMapper(IVoteBoardDao.class);
		return voteBoardDao.insertVoteBoard(voteBoardDto);
	}
}
