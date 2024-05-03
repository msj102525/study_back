package org.ict.testjpa2.member.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.testjpa2.member.model.dto.MemberDto;

import java.sql.Date;
import java.util.GregorianCalendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="MEMBER")
@Entity    //jpa 가 관리함, repository 와 연결됨
public class MemberEntity {
    @Id  //JPA가 객체를 관리할 때 식별할 기본키 지정
    //@GeneratedValue(strategy = GenerationType.IDENTITY)  //primary key 지정에 사용함
    @Column(name="USERID", nullable = false, unique = true)
    private String userId;
    @Column(name="USERPWD", nullable = false)
    private String userPwd;
    @Column(name="USERNAME", nullable = false)
    private String userName;
    @Column(name="GENDER", nullable = false)
    private String gender;
    @Column(name="AGE", nullable = false)
    private int age;
    @Column(name="PHONE", nullable = false, unique = true)
    private String phone;
    @Column(name="EMAIL", nullable = false, unique = true)
    private String email;
    @Column(name="ENROLL_DATE", nullable = false)
    private java.util.Date enrollDate;
    @Column(name="LASTMODIFIED", nullable = false)
    private java.util.Date lastModified;
    @Column(name="SIGNTYPE", columnDefinition = "direct")
    private String signType;
    @Column(name="ADMIN_YN", columnDefinition = "N")
    private String adminYN;
    @Column(name="LOGIN_OK", columnDefinition = "Y")
    private String loginOk;
    @Column(name="PHOTO_FILENAME")
    private String photoFileName;

    @PrePersist  //jpa 로 넘어가지 전에 작동
    public void prePersist() {
        enrollDate = new Date(System.currentTimeMillis());    //현재 날짜 시간 적용
        lastModified = new Date(System.currentTimeMillis());  //현재 날짜 시간 적용
    }

    //entity -> dto 로 변환
    public MemberDto toDto(){
        return MemberDto.builder()
                .userId(userId)
                .userPwd(userPwd)
                .userName(userName)
                .gender(gender)
                .age(age)
                .phone(phone)
                .email(email)
                .enrollDate(enrollDate)
                .lastModified(lastModified)
                .signType(signType)
                .adminYN(adminYN)
                .loginOk(loginOk)
                .photoFileName(photoFileName)
                .build();
    }
}
