package org.myweb.first.member.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member  {

	private String userId;
	private String userPwd;
	private String userName;
	private String gender;
	private int age;
	private String phone;
	private String email;
	private java.sql.Date enrollDate;
	private java.sql.Date lastModified;
	private String signType;
	private String adminYN;
	private String loginOk;
	private String photoFileName;
	

}

