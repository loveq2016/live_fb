package com.fsmeeting.live.interceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SecurityInterceptor  extends HandlerInterceptorAdapter {
	/**
	 * 进入Controller前调用,进行当前登录信息缓存，供AOP等会话无关部分使用
	 */
	@Override  
    public boolean preHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler) throws Exception {
		ServletContext cxt = request.getSession().getServletContext();
		int debug = 0;
		try {
			debug = Integer.parseInt(cxt.getInitParameter("debug"));
		} catch (Exception e) {
			debug = 0;
		}
		if (debug == 1) return true;
		//session中取当前用户session
		/*UserPrincipal user = (UserPrincipal)request.getSession().getAttribute("currentUser");
	
		if(user==null){
			response.sendRedirect("/login.html");
			return false;
		}else{
			return true;
		}*/
		
		return true;
	}
}
