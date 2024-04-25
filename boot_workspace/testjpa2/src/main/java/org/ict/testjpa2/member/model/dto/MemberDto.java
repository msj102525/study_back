package org.ict.testjpa2.member.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.testjpa2.member.jpa.entity.MemberEntity;
import org.springframework.stereotype.Component;

@Data   // @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class MemberDto {
    private String userId;
    private String userPwd;
    private String userName;
    private String gender;
    private Integer age;
    private String phone;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.util.Date enrollDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.util.Date lastModified;
    private String signType;
    private String adminYN;
    private String loginOK;
    private String photoFileName;

    public MemberEntity toEntity() {
        return MemberEntity.builder()
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
