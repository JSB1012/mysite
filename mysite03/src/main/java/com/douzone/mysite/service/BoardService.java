package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	public List<BoardVo> getBoardList() {	
		return boardRepository.findAll();
	}
	
	/*** Contents 정보 가져오기 ***/
	public BoardVo getContents(Long no) {
		BoardVo boardVo = boardRepository.findByNo( no );
		
		if( boardVo != null ) {
			boardRepository.updateHit( no );
		}
		return boardVo;
	}
	
	/*** 수정 시 Contents 정보 가져오기 ***/
	public BoardVo getContents(Long no, Long userNo) {
		BoardVo boardVo = boardRepository.findByNoAndUserNo( no, userNo );
		return boardVo;
	}
	
	/*** 수정 ***/
	public boolean modifyContents(BoardVo boardVo) {
		return  boardRepository.update(boardVo);
	}

	/*** 원글, 답글 작성시 한 곳에서 처리 ***/
	public boolean addContents(BoardVo boardVo) {
		if( boardVo.getGroupNo() != null ) {
			updateGroupOrderNo( boardVo );
		}
		return boardRepository.insert(boardVo);
	}

	/*** 답글시 g_no, o_no 수정 ***/
	private boolean updateGroupOrderNo(BoardVo boardVo) {
		return boardRepository.updateGroupOrderNo( boardVo.getGroupNo(), boardVo.getOrderNo() ) > 0;
	}

	/*** 삭제 ***/
	public boolean deleteContents(Long no, Long userNo) {
		return boardRepository.delete( no, userNo );
	}






}
