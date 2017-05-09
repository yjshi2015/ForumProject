package com.baobaotao.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.util.Assert;

import com.baobaotao.cons.CommonConstant;
import com.baobaotao.domain.User;

public class BaseController {

	protected static final String ERROR_MSG_KEY = "errorMsg";
	
	//��ȡ������session�е�User����
	protected User getSessionUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(CommonConstant.USER_CONTEXT);
	}
	
	//���û����󱣴浽session��
	protected void setSessionUser(HttpServletRequest request, User user) {
		request.getSession().setAttribute(CommonConstant.USER_CONTEXT, user);
	}
	
	//��ȡ����Ӧ�ó����URL����·��
	public final String getAppbaseUrl(HttpServletRequest request, String url) {
		Assert.hasLength(url, "url����Ϊ��");
		Assert.isTrue(url.startsWith("/"),"������/��ͷ");
		return request.getContextPath() + url;
	}
}
