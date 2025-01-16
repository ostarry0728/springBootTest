package com.zeus.common.Interceptor;

import java.lang.reflect.Method;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
	private static final String USER_INFO = "userInfo";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestURL = request.getRequestURI();
		log.info("preHandle requestURL : " + requestURL);

		HandlerMethod handleMethod = (HandlerMethod) handler;
		log.info("preHandle Bean: " + handleMethod.getBean());

		Method methodObj = handleMethod.getMethod();
		log.info("preHandle Method: " + methodObj);

		HttpSession session = request.getSession();
		if (session.getAttribute(USER_INFO) != null) {
			session.removeAttribute(USER_INFO);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("postHandle");
		String requestURL = request.getRequestURI();
		// requestURL : /login

		log.info("postHandle requestURL : " + requestURL);

		HandlerMethod handleMethod = (HandlerMethod) handler;
		log.info("postHandle Bean: " + handleMethod.getBean());
		// Bean: com.zeus.controller.Logincontroller@13ed2e22

		Method methodObj = handleMethod.getMethod();
		log.info("postHandle Method: " + methodObj);
		// Method: public void com.zeus.controller.Logincontroller.login()

		HttpSession session = request.getSession();
		
		
		ModelMap modelMap = modelAndView.getModelMap();
		Object member = modelMap.get("user");

		if (member != null) {
			log.info("member != null");
			session.setAttribute(USER_INFO, member);
			response.sendRedirect("/");
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("afterCompletion");
		String requestURL = request.getRequestURI();
		log.info("afterCompletion requestURL : " + requestURL);

		HandlerMethod handleMethod = (HandlerMethod) handler;

		log.info("afterCompletion Bean: " + handleMethod.getBean());

		Method methodObj = handleMethod.getMethod();
		log.info("afterCompletion Method: " + methodObj);
	}
}
