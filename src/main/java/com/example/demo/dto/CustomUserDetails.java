package com.example.demo.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.UserEntity;

public class CustomUserDetails implements UserDetails {

	private UserEntity userEntity;

	public CustomUserDetails(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	@Override // 사용자의 특정 권한을 리턴
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> collection = new ArrayList<>();

		collection.add(new GrantedAuthority() {

			@Override
			public String getAuthority() {

				return userEntity.getRole();
			}

		});

		return collection;
	}

	@Override
	public String getPassword() {
		return userEntity.getPassword();
	}

	@Override
	public String getUsername() {
		return userEntity.getUsername();
	}

	@Override // 계정이 여전히 유효한지 (만료 여부)
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override // 계정이 잠겨있는지 여부
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override // 로그인 자격증명 만료 여부
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override // 계정 활성화 여부
	public boolean isEnabled() {
		return true;
	}
}
