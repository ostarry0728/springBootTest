package com.kh.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kh.domain.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RESTCONTROLLER {

	@GetMapping("blog")
	public User httpGet() {
		User user = User.builder().id(1).userName("zeus").password("123456").email("zeus@gmail.com").build();
		return user;
	}

	@PostMapping("blog")
	public List<User> httpPost(@RequestBody User user) {
		List<User> list = new ArrayList<List>();
		return "post 요청처리" + user.toString();
	}

	@PutMapping("blog")
	public String httpPut() {
		return "put 요청처리";
	}

	@DeleteMapping("blog")
	public String httpDelete() {
		return "delete 요청처리";
	}

}
