package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	/***	메인	***/
	public List<BoardVo> findAll() {

		return sqlSession.selectList("board.findAll");
	}
	
	public BoardVo findByNo(Long no) {
		return sqlSession.selectOne( "board.findByNo", no );
	}
	
	/***	조회수	***/
	public int updateHit(Long no) {
		return sqlSession.update( "board.updateHit", no );
	}
	
	/***	글 작성	***/
	public boolean insert(BoardVo boardVo) {
		return sqlSession.insert("board.insert", boardVo) ==1;

	}
	
	/*** 답글시 g_no, o_no 수정 ***/
	public int updateGroupOrderNo(Long groupNo, Long orderNo) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put( "groupNo", groupNo );
		map.put( "orderNo", orderNo );
		
		return sqlSession.update( "board.updateGroupOrderNo", map );
	}
	
	public BoardVo findByNoAndUserNo(Long no, Long userNo) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put( "no", no );
		map.put( "userNo", userNo );
		
		return sqlSession.selectOne( "board.findByNoAndUserNo", map );
	}
	
	/*** 수정 ***/
	public boolean update(BoardVo boardVo) {
		return sqlSession.update( "board.update", boardVo ) == 1;
	}
	
	/*** 삭제 ***/
	public boolean delete(Long no, Long userNo) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put( "no", no );
		map.put( "userNo", userNo );
		return sqlSession.delete( "board.delete", map ) ==1;
	}








	

	








}
