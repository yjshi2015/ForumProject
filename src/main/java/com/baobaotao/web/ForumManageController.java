package com.baobaotao.web;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.baobaotao.domain.Board;
import com.baobaotao.domain.User;
import com.baobaotao.service.ForumService;
import com.baobaotao.service.UserService;

@Controller
public class ForumManageController extends BaseController{

	@Autowired
	private ForumService forumService;
	
	@Autowired
	private UserService userService;
	
	//列出所有的论坛版块
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String listAllBoards() {
		ModelAndView view = new ModelAndView();
		List<Board> boards = forumService.getAllBoards();
		view.addObject("boards",boards);
		view.setViewName("/listAllBoards");
		return "/listAllBoards";
	}

	//添加一个主题帖页面
	@RequestMapping(value="/forum/addBoardPage",method=RequestMethod.GET)
	public String addBoardPage() {
		return "/addBoard";
	}
	
	//添加一个主题帖
	@RequestMapping(value="/forum/addBoard",method=RequestMethod.POST)
	public String addBoard(Board board) {
		forumService.addBoard(board);
		return "/addBoardSuccess";
	}
	
	//指定论坛管理员的页面
	@RequestMapping(value="/forum/setBoardManagerPage",method=RequestMethod.GET)
	public ModelAndView setBoardManagerPage() {
		ModelAndView view = new ModelAndView();
		List<Board> boards = forumService.getAllBoards();
		List<User> users = userService.getAllUsers();
		view.addObject("boards",boards);
		view.addObject("users",users);
		view.setViewName("/setBoardManager");
		return view;
	}
}
