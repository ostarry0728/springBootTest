package com.kh.domain;
import lombok.Builder;
import lombok.Data;

// 롬복 애너테이션
@Data
@Builder
public class Address {
	private String postCode;
	private String location;

}
