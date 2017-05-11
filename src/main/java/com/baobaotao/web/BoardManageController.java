package com.baobaotao.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

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
import com.baobaotao.domain.Topic;
import com.baobaotao.domain.User;
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
	
	//������������ҳ��
	@RequestMapping(value="/board/addTopicPage-{boardId}",method=RequestMethod.GET)
	public ModelAndView addTopicPage(@PathVariable Integer boardId) {
		ModelAndView view = new ModelAndView();
		view.addObject("boardId",boardId);
		view.setViewName("/addTopic");
		return view;
	}
	
	//���һ��������
	@RequestMapping(value="/board/addTopic",method=RequestMethod.POST)
	public String addTopic(HttpServletRequest request, Topic topic) {
		User user = getSessionUser(request);
		topic.setUser(user);
		Date now = new Date();
		topic.setCreateTime(now);
		topic.setLastPost(now);
		forumService.addTopic(topic);
		String targetUrl = "/board/listBoardTopics-" + topic.getBoardId() + ".html";
		return "redirect:" + targetUrl;
	}
	
	//�г��������������
	@RequestMapping(value="/board/listTopicPosts-{topicId}",method=RequestMethod.GET)
	public ModelAndView listTopicPosts(@PathVariable Integer topicId,
			@RequestParam(value="pageNo",required=false) Integer pageNo) {
		ModelAndView view = new ModelAndView();
		Topic topic = forumService.getTopicByTopicId(topicId);
		pageNo = pageNo==null?1:pageNo;
		Page pagePost = forumService.getPagedPosts(topicId, 
				pageNo, CommonConstant.PAGE_SIZE);
		view.addObject("topic",topic);
		view.addObject("pagePost",pagePost);
		view.setViewName("/listTopicPosts");
		return view;
	}
}
