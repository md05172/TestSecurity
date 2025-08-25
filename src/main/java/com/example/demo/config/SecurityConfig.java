package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Spring에게 이 클래스는 설정 파일이며, 내부의 @Bean 메서드들을 Bean으로 등록하라고 알림
@EnableWebSecurity // Spring Security를 스프링 애플리케이션에서 활성화하고, 보안 설정을 사용자 정의할 수 있게 해줌
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// 특정경로를 모두 열어줄지 막을지에 대한 로직
		http
				.authorizeHttpRequests((auth) -> auth
						.requestMatchers("/", "/login", "/loginProc", "/join", "/joinProc").permitAll() // permitAll: 누구나 접근 허용
						.requestMatchers("/admin").hasRole("ADMIN")	// hasRole: 특정 권한이 있어야 접근 허용
						.requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
//						.requestMatchers().authenticated()	// aythenticated: 로그인한 사용자만 접근 가능 
//						.requestMatchers().denyAll()	// denyAll: 모든 접근을 막음
						.anyRequest().authenticated()	// 위에서 처리하지 않는 경로에대한 설정
						);
		
		http
				.formLogin((auth) -> auth.loginPage("/login")	// 사용자가 로그인이 필요한 상황에 자동으로 이 경로로 리다이렉트 됨
						.loginProcessingUrl("/loginProc")	// 로그인 폼이 처리되는 URL 로그인 폼 액션이 /loginProc이어야하고
															// Spring Security가 이 경로로 요청이 오면 인증을 수행한다.
						.permitAll());
		
		http
				.csrf((auth) -> auth.disable());
		
		return http.build();
	}
}
