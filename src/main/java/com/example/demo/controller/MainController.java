package com.example.demo.controller;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String mainP(Model model) {
		
		// 세션에서 현재 사용자 아이디 가져오기
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		
		// 세션에서 현재 사용자 role 가져오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 현재 로그인한 사용자에 대한 인증 정보를 가져온다.
										// SecurityContextHolder 는 Spring Security의 보안 컨텍스트(현재 로그인한 사용자 정보)를 가지고 있는 곳.
		// 로그인한 사용자가 가진 모든 권한(role)을 리스트로 가져온다.
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		
		// 권한 목록을 반복하기 위한 반복(Iterator) 하나씩 꺼내기 위한 준비과정
		// ? extends GrantedAuthority - GrantedAuthority 또는 GrantedAuthority를 상속받은 클래스 
		Iterator<? extends GrantedAuthority> iter = authorities.iterator();
		
		// 목록중에 가장 첫번째 권한을 하나만 가져오는데 만약 여러개라면 그 중 하나만 가져온다.
		GrantedAuthority auth = iter.next();
		// 꺼낸 권한 객체에서 실제 이름을 문자열로 가져온다.
		String role = auth.getAuthority();
		System.out.println("권한: " + role);
		
		model.addAttribute("id", id);
		model.addAttribute("role", role);
		
		return "main";
	}
}

/*
	특정 권한이 있는지 체크
	boolean isAdmin = authentication.getAuthorities()
    	.stream()
    	.anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

	if (isAdmin) {
    	System.out.println("관리자 권한 있음!");
	}
	
	만약 여러 권한을 리스트로 저장하고 싶다면? 
	List<String> roleList = authentication.getAuthorities()
    	.stream()
    	.map(GrantedAuthority::getAuthority)
    	.collect(Collectors.toList()); Set으로 할땐 toSet();
    
    ※ .map(GrantedAuthority::getAuthority) == .map(authority -> authority.getAuthority())	
    	
    단순 권한 출력
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	authentication.getAuthorities()
    .stream()
    .map(GrantedAuthority::getAuthority)
    .forEach(role -> System.out.println("권한: " + role));
*/

