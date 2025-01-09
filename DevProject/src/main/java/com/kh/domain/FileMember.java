package com.kh.domain;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

// 롬복 애너테이션
@Data
public class FileMember {
	private String userId;
	private String password;
	private List<MultipartFile> picture;
}
