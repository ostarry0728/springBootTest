package com.zeus.common.Interceptor;

import java.lang.reflect.Method;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccessLoggingInterceptor implements HandlerInterceptor {
	private static final String USER_INFO = "userInfo";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String requestURL = request.getRequestURI();
		log.info("requestURL : " + requestURL);
		// requestURL : /board/list

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();

		Class cla = method.getDeclaringClass();
		// com.zeus.controller.BoardController
		String className = cla.getName();
		String classSimpleName = cla.getSimpleName();
		// list
		String methodName = method.getName();
		// [ACCESS CONROLLER] com.zeus.controller.Boardcontroller.list
		log.info("[ACCESS CONTROLLER] " + className + "." + methodName);
	}
}
