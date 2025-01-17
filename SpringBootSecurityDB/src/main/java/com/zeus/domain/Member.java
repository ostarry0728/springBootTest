package com.zeus.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Member {
	private static final long serialVersionUID = 582999266267533096L;
	private int userNo;
	private String userId;
	private String userPw;
	private String userName;
	private boolean enabled;
	private Date regDate;
	private Date updDate;
	private List<MemberAuth> authList;
}
