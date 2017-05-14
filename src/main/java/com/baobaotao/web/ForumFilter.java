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
	
	//不需要登录即可访问的URL
	private static final String[] INHERENT_ESCAPE_URIS = {"/index.jsp","/index.html",
		"/login.jsp","/login/doLogin.html","/register.jsp",
		"/register.html","/board/listBoardTopics-","/board/listTopicPosts-"};
	
	//执行过滤
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		//保证该过滤器在一次请求中只被调用一次
		if(request != null && request.getAttribute(FILTERED_REQUEST) != null) {
			chain.doFilter(request, response);
		} else {
			//设置过滤标志，防止一次请求多次过滤
			request.setAttribute(FILTERED_REQUEST, Boolean.TRUE);
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			User userContext = getSessionUser(httpRequest);
			
			//用户未登录，且当前URL资源需要登录才能访问
			if(userContext == null && !isURILogin(httpRequest.getRequestURI(),httpRequest)) {
				String toUrl = httpRequest.getRequestURL().toString();
				if(!StringUtils.isEmpty(httpRequest.getQueryString())) {
					toUrl += "?" + httpRequest.getQueryString();
				}
				
				//将用户请求URL保存在session中，用于登陆后，调到目标URL
				httpRequest.getSession().setAttribute(CommonConstant.LOGIN_TO_URL, 
						toUrl);
				
				//转发到登录页面
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			chain.doFilter(request, response);
		}
	}
	
	//当前URL资源是否需要登录才能访问
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
