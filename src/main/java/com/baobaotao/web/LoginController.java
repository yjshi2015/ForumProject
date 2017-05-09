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
	
	//�û���¼
	@RequestMapping("/doLogin")
	public ModelAndView login(HttpServletRequest request, User user) {
		User dbUser = userService.getUserByUserName(user.getUserName());
		ModelAndView mav = new ModelAndView();
		mav.setViewName("forward:/login.jsp");
		if(dbUser == null) {
			mav.addObject("errorMsg","�û���������");
		}else if(!dbUser.getPassword().equals(user.getPassword())) {
			mav.addObject("errorMsg","�������");
		}else if(dbUser.getLocked() == User.USER_UNLOCK) {
			mav.addObject("errorMsg","�û��ѱ����������ܹ���¼");
		}else {
			dbUser.setLastIp(request.getRemoteAddr());
			dbUser.setLastVisit(new Date());
			userService.loginSuccess(dbUser);
			//���ø��෽���������¼�Ŀͻ�
			setSessionUser(request, dbUser);
			String toUrl = (String)request.getSession().getAttribute(CommonConstant.LOGIN_TO_URL);
			//���ػ��б����to_urlɾ��
			request.getSession().removeAttribute(CommonConstant.LOGIN_TO_URL);
			
			//�����ǰ�Ự��û�б����¼֮ǰ������URL����ֱ����ת����ҳ
			if(StringUtils.isEmpty(toUrl)) {
				toUrl = "/index.html";
			}
			
			mav.setViewName("redirect:" + toUrl);
		}
		return mav;
	}
	
	//��¼ע��
	@RequestMapping("/doLogout")
	public String logout(HttpSession session) {
		session.removeAttribute(CommonConstant.USER_CONTEXT);
		return "forward:/index.jsp";
	}
}
