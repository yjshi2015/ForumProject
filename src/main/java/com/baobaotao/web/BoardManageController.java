package com.baobaotao.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.baobaotao.cons.CommonConstant;
import com.baobaotao.dao.Page;
import com.baobaotao.domain.Board;
import com.baobaotao.service.ForumService;

@Controller
public class BoardManageController extends BaseController{

	@Autowired
	private ForumService forumService;
	
	//�г���̳����µ���������
	//��ʹ��@RequestMapping URI template ��ʽӳ��ʱ�� �� someUrl/{paramId}, ��ʱ��paramId��ͨ�� @Pathvariableע�������������ֵ�������Ĳ�����
	@RequestMapping(value="/board/listBoardTopics-{boardId}",method=RequestMethod.GET)
	public ModelAndView listBoardTopics(@PathVariable Integer boardId,
			@RequestParam(value="pageNo",required=false) Integer pageNo) {
		ModelAndView view = new ModelAndView();
		Board board = forumService.getBoardById(boardId);
		pageNo = pageNo==null?1:pageNo;
		Page pagedTopic = forumService.getPagedTopics(boardId, 
				pageNo, CommonConstant.PAGE_SIZE);
		view.addObject("board",board);
		view.addObject("pagedTopic",pagedTopic);
		view.setViewName("/listBoardTopics");
		return view;
	}
}
