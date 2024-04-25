package org.ict.testjpa2.member.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.testjpa2.member.model.dto.MemberDto;

import java.util.GregorianCalendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="Reply")
@Entity
public class MemberEntity {
    @Id
    @Column(name = "USERID", nullable = false)
    private String userId;

    @Column(name = "USERPWD")
    private String userPwd;

    @Column(name = "USERNAME", nullable = false)
    private String userName;

    @Column(name = "GENDER", nullable = false)
    private String gender;

    @Column(name = "AGE", nullable = false)
    private Integer age;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "ENROLL_DATE")
    private java.util.Date enrollDate;

    @Column(name = "LASTMODIFIED")
    private java.util.Date lastModified;

    @Column(name = "SIGNTYPE", nullable = false)
    private String signType;

    @Column(name = "ADMIN_YN", nullable = false, columnDefinition = "N")
    private String adminYN;

    @Column(name = "LOGIN_OK",nullable = false , columnDefinition = "Y")
    private String loginOK;

    @Column(name = "PHOTH_FILENAME")
    private String photoFileName;

    @PrePersist
    public void prePersist() {
        enrollDate = new GregorianCalendar().getGregorianChange();
        lastModified = new GregorianCalendar().getGregorianChange();
    }

    public MemberDto toDto() {
        return MemberDto.builder()
                .userId(this.userId)
                .userPwd(this.userPwd)
                .userName(this.userName)
                .gender(this.gender)
                .age(this.age)
                .phone(this.phone)
                .email(this.email)
                .enrollDate(this.enrollDate)
                .lastModified(this.lastModified)
                .signType(this.signType)
                .adminYN(this.adminYN)
                .loginOK(this.loginOK)
                .photoFileName(this.photoFileName)
                .build();
    }
}
