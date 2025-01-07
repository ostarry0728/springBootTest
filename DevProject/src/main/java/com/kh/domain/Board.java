package com.kh.domain;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

// 롬복 애너테이션
@Data
@Builder
public class Board {
	private Integer boardNo;
	private String title;
	private String content;
	private String writer;
	private Date regDate;
}
