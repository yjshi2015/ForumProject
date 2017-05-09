package com.baobaotao.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.baobaotao.domain.User;
import com.baobaotao.exception.UserExistException;
import com.baobaotao.service.UserService;

@Controller
public class RegisterController extends BaseController{

	@Autowired
	private UserService userService;
	
	
	//�û���¼
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ModelAndView register(HttpServletRequest request, User user) {
		ModelAndView view = new ModelAndView();
		view.setViewName("/success");
		try {
			userService.register(user);
		} catch (UserExistException e) {
			view.addObject("errorMsg", "�û����Ѿ����ڣ���ѡ����������");
			view.setViewName("forward:/register.jsp");
		}
		setSessionUser(request, user);
		return view;
	}
}
