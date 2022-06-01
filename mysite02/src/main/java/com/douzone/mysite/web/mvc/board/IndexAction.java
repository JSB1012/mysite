package com.douzone.mysite.web.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PageVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.WebUtil;

public class IndexAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int cur;
		
		if(request.getParameter("cur") == null) {
			cur = 1;
		} else {
			cur = Integer.parseInt(request.getParameter("cur"));	
		}
		
		String kwd;
		
		if(request.getParameter("kwd") == null) {
			kwd = "";
		} else {
			kwd = request.getParameter("kwd");
		}
		
		if("".equals(kwd)) {
			int total = new BoardRepository().getCount();
			PageVo page = new PageVo(cur, total);
			
			List<BoardVo> list = new BoardRepository().findAll(page, kwd);
			
			request.setAttribute("page", page);
			request.setAttribute("list", list); // 저장
			System.out.println(list);
			
		} else if(kwd != null) {
			int total = new BoardRepository().getCountSearch(kwd);	
			PageVo page = new PageVo(cur, total);

			List<BoardVo> list = new BoardRepository().findAll(page, kwd);
			
			request.setAttribute("page", page);
			request.setAttribute("list", list); // 저장
			System.out.println(list);
		}

		WebUtil.forward(request, response, "board/index");
	}

}
