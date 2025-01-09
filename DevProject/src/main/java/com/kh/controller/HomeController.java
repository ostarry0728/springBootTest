package com.kh.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {
	// 멤버변수
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		// locale, model 객체가 주입된것을 활용해서 출력
		Date date = new Date();
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formatedDate = df.format(date);
		model.addAttribute("작업시간", formatedDate);
		logger.info("logger2" + model);
		System.out.println("sysout2" + model);

		return "home";
	}

	@RequestMapping(value = "/memberInsert", method = RequestMethod.GET)
	public String memberInsert() {
		return "memberInsert";
	}

	@RequestMapping(value = "/ajaxhome6", method = RequestMethod.GET)
	public String ajaxhome6() {
		return "ajaxhome6";
	}

	@RequestMapping(value = "/registerFileUpForm", method = RequestMethod.GET)
	public String registerFileUpForm() {
		log.info("registerFileUpForm");
		return "registerFileUpForm";
	}
}
