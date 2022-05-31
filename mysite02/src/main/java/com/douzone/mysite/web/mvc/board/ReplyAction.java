package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.WebUtil;

public class ReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			WebUtil.redirect(request, response, request.getContextPath() + "/user?a=loginform ");
			return;
		} else {
			
			int no = Integer.parseInt(request.getParameter("no"));
			String title = request.getParameter("title"); 
			String contents = request.getParameter("contents"); 
			
			Long userNo = authUser.getNo();
			
			BoardVo board = new BoardRepository().boardInfo(no);
			
			Long groupNo = board.getGroupNo();
			Long orderNo = board.getOrderNo();
			Long depth = board.getDepth();
			
			BoardVo boardVo = new BoardVo();
			
			boardVo.setTitle(title);
			boardVo.setContents(contents);
			boardVo.setGroupNo(groupNo);
			boardVo.setOrderNo(orderNo);
			boardVo.setDepth(depth);
			boardVo.setUserNo(userNo);
			
			new BoardRepository().replyUpdate(boardVo);
			new BoardRepository().replyInsert(boardVo);
			
			WebUtil.redirect(request, response, request.getContextPath() + "/board");
		}
		
		
		
	}

}
