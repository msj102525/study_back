package org.ict.first.member.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member /*implements java.io.Serializable*/ {
	//private static final long serialVersionUID = -4952932019676617041L;

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

