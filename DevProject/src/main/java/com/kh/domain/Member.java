package com.kh.domain;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

// 롬복 애너테이션
@Data
public class Member {
	@NotBlank(message = "공백이나 빈칸일 수 없습니다.")
	private String userId;
	@NotBlank(message = "공백이나 빈칸일 수 없습니다.")
	private String password;
	private String email;
	@NotBlank(message = "이름입력은 필수입니다.(최대 3자)")
	@Size(max = 3)
	private String userName;
	private List<String> hobbyList;
	private int coin;
	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;
	private String gender;
}
