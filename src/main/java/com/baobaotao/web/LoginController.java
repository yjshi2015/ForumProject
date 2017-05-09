package com.baobaotao.web;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baobaotao.cons.CommonConstant;
import com.baobaotao.domain.User;
import com.baobaotao.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController{

	@Autowired
	private UserService userService;
	
	//用户登录
	@RequestMapping("/doLogin")
	public ModelAndView login(HttpServletRequest request, User user) {
		User dbUser = userService.getUserByUserName(user.getUserName());
		ModelAndView mav = new ModelAndView();
		mav.setViewName("forward:/login.jsp");
		if(dbUser == null) {
			mav.addObject("errorMsg","用户名不存在");
		}else if(!dbUser.getPassword().equals(user.getPassword())) {
			mav.addObject("errorMsg","密码错误");
		}else if(dbUser.getLocked() == User.USER_UNLOCK) {
			mav.addObject("errorMsg","用户已被锁定，不能够登录");
		}else {
			dbUser.setLastIp(request.getRemoteAddr());
			dbUser.setLastVisit(new Date());
			userService.loginSuccess(dbUser);
			//调用父类方法，保存登录的客户
			setSessionUser(request, dbUser);
			String toUrl = (String)request.getSession().getAttribute(CommonConstant.LOGIN_TO_URL);
			//将回话中保存的to_url删除
			request.getSession().removeAttribute(CommonConstant.LOGIN_TO_URL);
			
			//如果当前会话中没有保存登录之前的请求URL，则直接跳转到主页
			if(StringUtils.isEmpty(toUrl)) {
				toUrl = "/index.html";
			}
			
			mav.setViewName("redirect:" + toUrl);
		}
		return mav;
	}
	
	//登录注销
	@RequestMapping("/doLogout")
	public String logout(HttpSession session) {
		session.removeAttribute(CommonConstant.USER_CONTEXT);
		return "forward:/index.jsp";
	}
}
