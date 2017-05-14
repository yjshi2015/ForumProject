package com.baobaotao.web;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.baobaotao.cons.CommonConstant;
import com.baobaotao.domain.User;

public class ForumFilter implements Filter{

	private static final String FILTERED_REQUEST = "@@session_context_filtered_request";
	
	//����Ҫ��¼���ɷ��ʵ�URL
	private static final String[] INHERENT_ESCAPE_URIS = {"/index.jsp","/index.html",
		"/login.jsp","/login/doLogin.html","/register.jsp",
		"/register.html","/board/listBoardTopics-","/board/listTopicPosts-"};
	
	//ִ�й���
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		//��֤�ù�������һ��������ֻ������һ��
		if(request != null && request.getAttribute(FILTERED_REQUEST) != null) {
			chain.doFilter(request, response);
		} else {
			//���ù��˱�־����ֹһ�������ι���
			request.setAttribute(FILTERED_REQUEST, Boolean.TRUE);
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			User userContext = getSessionUser(httpRequest);
			
			//�û�δ��¼���ҵ�ǰURL��Դ��Ҫ��¼���ܷ���
			if(userContext == null && !isURILogin(httpRequest.getRequestURI(),httpRequest)) {
				String toUrl = httpRequest.getRequestURL().toString();
				if(!StringUtils.isEmpty(httpRequest.getQueryString())) {
					toUrl += "?" + httpRequest.getQueryString();
				}
				
				//���û�����URL������session�У����ڵ�½�󣬵���Ŀ��URL
				httpRequest.getSession().setAttribute(CommonConstant.LOGIN_TO_URL, 
						toUrl);
				
				//ת������¼ҳ��
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			chain.doFilter(request, response);
		}
	}
	
	//��ǰURL��Դ�Ƿ���Ҫ��¼���ܷ���
	private boolean isURILogin(String requestURL,HttpServletRequest request) {
		if(request.getContextPath().equalsIgnoreCase(requestURL)
				||(request.getContextPath() + "/").equalsIgnoreCase(requestURL)) {
			return true;
		}
		for(String uri : INHERENT_ESCAPE_URIS) {
			if(requestURL != null && requestURL.indexOf(uri) >= 0) {
				return true;
			}
		}
		return false;
	}
	
	protected User getSessionUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(CommonConstant.USER_CONTEXT);
	}
	
	@Override
	public void destroy() {
	}


	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
