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
	
	//�г����е���̳���
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String listAllBoards() {
		ModelAndView view = new ModelAndView();
		List<Board> boards = forumService.getAllBoards();
		view.addObject("boards",boards);
		view.setViewName("/listAllBoards");
		return "/listAllBoards";
	}

	//���һ��������ҳ��
	@RequestMapping(value="/forum/addBoardPage",method=RequestMethod.GET)
	public String addBoardPage() {
		return "/addBoard";
	}
	
	//���һ��������
	@RequestMapping(value="/forum/addBoard",method=RequestMethod.POST)
	public String addBoard(Board board) {
		forumService.addBoard(board);
		return "/addBoardSuccess";
	}
	
	//ָ����̳����Ա��ҳ��
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
