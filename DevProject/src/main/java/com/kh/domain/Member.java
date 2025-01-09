package com.kh.domain;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// 롬복 애너테이션
@Data
public class Member {
	@NotBlank(message = "공백이나 빈칸일 수 없습니다.")
	private String userId;
	private String password;
	private int coin;
	private Date dateOfBirth;
	private List<String> hobbyList;
	private String email;
	private String userName;
}
