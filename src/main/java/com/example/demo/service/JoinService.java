package com.example.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.JoinDTO;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;

@Service
public class JoinService {

	private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public void joinProcess(JoinDTO joinDTO) {

		// DB에 이미 동일한 username을 가진 회원이 존재하는지?
		boolean isUser = userRepository.existsByUsername(joinDTO.getUsername());
		
		if(isUser) return;
		
		// DTO를 Entity로 변환
		UserEntity data = new UserEntity();
		
		data.setUsername(joinDTO.getUsername());
		// 비밀번호는 암호화 해야하기 때문에 미리 SecurityConfig.class에서 Bean등록한 BCryptPasswordEncoder메소드를 사용한다.
		data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
		data.setRole("ROLE_ADMIN");
		
		// DB에 저장
		userRepository.save(data);
	}

}
