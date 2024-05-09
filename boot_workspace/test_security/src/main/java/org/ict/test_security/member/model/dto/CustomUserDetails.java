package org.ict.test_security.member.model.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

// Spring Security 의 UserDetails 인터페이스를 상속받아서 구현함
@Component
public class CustomUserDetails implements UserDetails {
    private final MemberDto user;

    // 생성자로 의존성 주입
    public CustomUserDetails(MemberDto user) {
        this.user = user;
    }

    // 사용자의 권한 목록을 반환하는 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    // 사용자의 비밀번호 반환
    @Override
    public String getPassword() {
        return "";
    }

    // 사용자 이름 반환
    @Override
    public String getUsername() {
        return "";
    }

    // 계정이 만료되었는지를 반환
    @Override
    public boolean isAccountNonExpired() {
        return true; // 여기서는 만료되지 않았다고 가정함
    }

    // 계정이 잠겨있지 않은지를 반환
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    // 사용자의 크리덴셜(비밀번호 등)이 만료되지 않았는지를 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    // 사용자 계정이 사용가능 상태인지를 반환
    @Override
    public boolean isEnabled() {
        return false;
    }
}
