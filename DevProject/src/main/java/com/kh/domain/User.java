package com.kh.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private int id;
	private String userName;
	private String password;
	private String email;
	public static Object builder() {
		// TODO Auto-generated method stub
		return null;
	}
}
