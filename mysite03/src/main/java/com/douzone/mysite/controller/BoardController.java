package com.douzone.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	/***	메인	***/
	@RequestMapping("")
	public String index(@RequestParam(value="p", required=true, defaultValue="1") Integer page, @RequestParam(value = "kwd", required = true, defaultValue = "") String keyword, Model model) {
		Map<String, Object> map = boardService.getContentsList(page, keyword);
		model.addAttribute("map", map);
		//model.addAllAttributes(map);
		//model.addAttribute("list", map.get("list));
		return "board/index";
	}
	
	/***	View	***/
	@RequestMapping("/view/{no}")
	public String view(@PathVariable("no") Long no, Model model) {
		BoardVo boardVo = boardService.getContents(no);
		model.addAttribute("boardVo", boardVo);
		
		return "board/view";
	}
	
	/***	글쓰기	***/
	@RequestMapping(value="/write", method=RequestMethod.GET) 
	public String write(HttpSession session) {
		// 접근 제어(Access Control)//////////////////////////////////
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/user/login";
		}
		////////////////////////////////////////////////////////////	
		
		return "board/write";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(HttpSession session, BoardVo boardVo) {
		// 접근 제어(Access Control)//////////////////////////////////
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/user/login";
		}
		////////////////////////////////////////////////////////////	
		boardVo.setUserNo(authUser.getNo());
		boardService.addContents(boardVo);
		return "redirect:/board";
	}
	
	/***	답글	***/
	@RequestMapping(value="/reply/{no}")
	public String reply(HttpSession session, @PathVariable("no") Long no, Model model) {
		// 접근 제어(Access Control)//////////////////////////////////
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/user/login";
		}
		////////////////////////////////////////////////////////////
		BoardVo boardVo = boardService.getContents(no);
		boardVo.setOrderNo(boardVo.getOrderNo() + 1);
		boardVo.setDepth(boardVo.getDepth() + 1);

		model.addAttribute("boardVo", boardVo);

		return "board/reply";
	}
	
	/***	수정	***/
	@RequestMapping(value="/modify/{no}", method=RequestMethod.GET)
	public String modify(HttpSession session, @PathVariable("no") Long no, Model model) {
		// 접근 제어(Access Control)//////////////////////////////////
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/user/login";
		}
		////////////////////////////////////////////////////////////	
		BoardVo boardVo = boardService.getContents(no, authUser.getNo());
		model.addAttribute("boardVo", boardVo);
		return "board/modify";
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(HttpSession session, BoardVo boardVo) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		boardVo.setUserNo(authUser.getNo());
		boardService.modifyContents(boardVo);
		return "redirect:/board/view/" + boardVo.getNo();
	}
	
	/***	삭제	***/
	@RequestMapping("/delete/{no}")
	public String delete(HttpSession session, @PathVariable("no") Long no) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		boardService.deleteContents(no, authUser.getNo());
		return "redirect:/board";
	}
	
}
