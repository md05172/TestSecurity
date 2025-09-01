package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Spring에게 이 클래스는 설정 파일이며, 내부의 @Bean 메서드들을 Bean으로 등록하라고 알림
@EnableWebSecurity // Spring Security를 스프링 애플리케이션에서 활성화하고, 보안 설정을 사용자 정의할 수 있게 해줌
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public RoleHierarchy roleHierarchy() {

	    return RoleHierarchyImpl.fromHierarchy("""
	            ROLE_C > ROLE_B
	            ROLE_B > ROLE_A
	            """);
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// 특정경로를 모두 열어줄지 막을지에 대한 로직
//		http
//				.authorizeHttpRequests((auth) -> auth
//						.requestMatchers("/", "/login", "/loginProc", "/join", "/joinProc").permitAll() // permitAll: 누구나 접근 허용
//						.requestMatchers("/admin").hasRole("ADMIN")	// hasRole: 특정 권한이 있어야 접근 허용
//						.requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
////						.requestMatchers().authenticated()	// aythenticated: 로그인한 사용자만 접근 가능 
////						.requestMatchers().denyAll()	// denyAll: 모든 접근을 막음
//						.anyRequest().authenticated()	// 위에서 처리하지 않는 경로에대한 설정
//						);
		
		// 계층 권한: Role Hierarchy 적용
		http
        .authorizeHttpRequests((auth) -> auth
                .requestMatchers("/login").permitAll()
                .requestMatchers("/").hasAnyRole("A")
                .requestMatchers("/manager").hasAnyRole("B")
                .requestMatchers("/admin").hasAnyRole("C")
                .anyRequest().authenticated()
        );
		
		
		http
				.formLogin((auth) -> auth.loginPage("/login")	// 사용자가 로그인이 필요한 상황에 자동으로 이 경로로 리다이렉트 됨
						.loginProcessingUrl("/loginProc")	// 로그인 폼이 처리되는 URL 로그인 폼 액션이 /loginProc이어야하고
															// Spring Security가 이 경로로 요청이 오면 인증을 수행한다.
						.permitAll());
//				.httpBasic(Customizer.withDefaults());
		
//		http
//				.csrf((auth) -> auth.disable());
		
		http
				.sessionManagement((auth) -> auth
				.maximumSessions(1)
				.maxSessionsPreventsLogin(true));
		
		http
				.sessionManagement((auth) -> auth
//						.sessionFixation().none()	// 로그인 시 세션 정보 변경 안함
//						.sessionFixation().newSession()	// 로그인 시 세션 새로 생성	
						.sessionFixation().changeSessionId());	// 로그인 시 동일한 세션에 대한 id 변경
						
		return http.build();
	}
	
//	InMemory방식 유저 정보를 저장하고 로그인을 하고싶을때
	@Bean
    public UserDetailsService userDetailsService() {

        UserDetails user1 = User.builder()
                .username("user1")
                .password(bCryptPasswordEncoder().encode("1234"))
                .roles("C")
                .build();

        UserDetails user2 = User.builder()
                .username("user2")
                .password(bCryptPasswordEncoder().encode("1234"))
                .roles("B")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
}
