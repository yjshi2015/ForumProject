package com.baobaotao.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.util.Assert;

import com.baobaotao.cons.CommonConstant;
import com.baobaotao.domain.User;

public class BaseController {

	protected static final String ERROR_MSG_KEY = "errorMsg";
	
	//获取保存在session中的User对象
	protected User getSessionUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(CommonConstant.USER_CONTEXT);
	}
	
	//将用户对象保存到session中
	protected void setSessionUser(HttpServletRequest request, User user) {
		request.getSession().setAttribute(CommonConstant.USER_CONTEXT, user);
	}
	
	//获取基于应用程序的URL绝对路径
	public final String getAppbaseUrl(HttpServletRequest request, String url) {
		Assert.hasLength(url, "url不能为空");
		Assert.isTrue(url.startsWith("/"),"必须以/打头");
		return request.getContextPath() + url;
	}
}
