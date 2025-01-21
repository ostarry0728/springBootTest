package com.kh.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.azul.tooling.in.Model;
import com.kh.domain.FileMember;
import com.kh.domain.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {

	@PostMapping(value = "/insert")
	public String insertMemebr(Member member) {
		log.info("insertMemebr");
		return "home";
	}

	@PostMapping(value = "/redirect")
	public String redirecttMemebr(Member member, RedirectAttributes rttr) {
		log.info("redirecttMemebr");
		rttr.addFlashAttribute("member", member);
		return "redirect:/member/result";
	}

	@GetMapping(value = "/result")
	public String redirectResult() {
		log.info("redirectResult");
		return "result";
	}

	@RequestMapping(value = "/registerFileUp01", method = RequestMethod.POST)
	public String registerFileUp01(FileMember filemember) throws Exception {

		if (!filemember.getPicture().isEmpty()) {
			for (MultipartFile data : filemember.getPicture()) {
				log.info("registerFileUp01");
				log.info("originalName: " + data.getOriginalFilename());
				log.info("size: " + data.getSize());
				log.info("contentType: " + data.getContentType());
				if (!data.isEmpty()) {
					String fileName = data.getOriginalFilename();
					data.transferTo(new File("C:/SpringBootProject/upload_files/" + fileName));
				}
			}
		}
		return "home";
	}

	@PostMapping(value = "/register06")
	public ResponseEntity<String> register06(@RequestBody List<Member> memberList) {
		log.info("register06");

		for (Member member : memberList) {
			log.info("userId = " + member.getUserId());
			log.info("password = " + member.getPassword());
		}
		ResponseEntity<String> entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		return entity;
	}

	@PostMapping(value = "/uploadAjax", produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception {
		String originalFilename = file.getOriginalFilename();
		log.info("originalName: " + originalFilename);
		log.info("size: " + file.getSize());
		log.info("contentType: " + file.getContentType());
		if (!file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			file.transferTo(new File("C:/SpringBootProject/upload_files/" + fileName));
		}
		ResponseEntity<String> entity = new ResponseEntity<String>("UPLOAD SUCCESS " + originalFilename, HttpStatus.OK);
		return entity;
	}

	@RequestMapping(value = "/registerSpringFormCheckboxes01", method = RequestMethod.GET)
	public String registerSpringFormCheckboxes01(Model model) {
		log.info("registerSpringFormCheckboxes01");

		Map<String, String> hobbyMap = new HashMap<String, String>();
		hobbyMap.put("01", "Sports");
		hobbyMap.put("02", "Music");
		hobbyMap.put("03", "Movie");

		model.addAttribute("hobbyMap", hobbyMap);
		model.addAttribute("member", new Member());

		return "registerSpringFormCheckboxes01"; // 뷰 파일명
	}

	@RequestMapping(value = "/registerSpringFormErrors", method = RequestMethod.GET)
	public String registerSpringFormErrors(Model model) {
		log.info("registerSpringFormErrors");

		Member member = new Member();
		member.setEmail("aaa@ccc.com");
		member.setUserName("홍길동");

		model.addAttribute("member", member);

		return "registerSpringFormErrors"; // 뷰 파일명
	}

	// 입력 처리
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@Validated Member member, BindingResult result) {
		log.info("register");

		// 에러 처리
		if (result.hasErrors()) {
			return "registerSpringFormErrors";
		}

		log.info("member.getUserId() = " + member.getUserId());
		log.info("member.getUserName() = " + member.getUserName());
		log.info("member.getEmail() = " + member.getEmail());

		return "errorsResult";
	}

	// 입력값 검증
	@RequestMapping(value = "/registerValidation", method = RequestMethod.POST)
	public String registerValidation(@Validated Member member, BindingResult result) {
		log.info("registerValidation");
		if (result.hasErrors()) {
			return "registerValidationForm"; // 뷰 파일명
		}
		log.info("member.getUserId() = " + member.getUserId());
		log.info("member.getUserName() = " + member.getUserName());
		log.info("member.getGender() = " + member.getGender());
		return "home";
	}

	// 입력값 검증을 입력화는 폼화면
	@RequestMapping(value = "/registerValidationForm01", method = RequestMethod.GET)
	public String registerForm01(Model model) {
		log.info("registerValidationForm01");
		model.addAttribute("member", new Member());
		return "registerValidationForm"; // 뷰 파일명
	}

	// 전체적인 입력값 검증
	// 입력값 검증을 할 도메인 클래스에 @Vaildated를 지정한다.
	@RequestMapping(value = "/registerValidation2", method = RequestMethod.POST)
	public String registerValidation2(@Validated Member member, BindingResult result) {
		log.info("registerValidation2");
		// 입력값 검증 에러가 발생한 경우 true를 반환한다.
		log.info("result.hasErrors() = " + result.hasErrors());
		// 입력값 검증 후 BindingResult가 제공하는 메서드를 이용하여 검사 결과를 확인한다.
		if (result.hasErrors()) {
			List<ObjectError> allErrors = result.getAllErrors();
			List<ObjectError> globalErrors = result.getGlobalErrors();
			List<FieldError> fieldErrors = result.getFieldErrors();
			log.info("allErrors.size() = " + allErrors.size());
			log.info("globalErrors.size() = " + globalErrors.size());
			log.info("fieldErrors.size() = " + fieldErrors.size());
			for (int i = 0; i < allErrors.size(); i++) {
				ObjectError objectError = allErrors.get(i);
				log.info("allError = " + objectError);
			}
			for (int i = 0; i < globalErrors.size(); i++) {
				ObjectError objectError = globalErrors.get(i);
				log.info("globalError = " + objectError);
			}

			for (int i = 0; i < fieldErrors.size(); i++) {
				FieldError fieldError = fieldErrors.get(i);

				log.info("fieldError = " + fieldError);
				log.info("fieldError.getDefaultMessage() = " + fieldError.getDefaultMessage());
			}
			return "registerValidation2Form"; // 뷰 파일명
		}

		log.info("member.getUserId() = " + member.getUserId());
		log.info("member.getGender() = " + member.getGender());
		return "home";
	}

	@RequestMapping(value = "/registerValidation2Form01", method = RequestMethod.GET)
	public String registerValidation2Form01(Model model) {
		log.info("registerValidation2Form01");
		model.addAttribute("member", new Member());
		return "registerValidation2Form"; // 뷰 파일명
	}

}
