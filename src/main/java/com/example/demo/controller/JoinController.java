package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.JoinDTO;
import com.example.demo.service.JoinService;

@Controller
public class JoinController {

	private JoinService joinService;

	public JoinController(JoinService joinService) {
		this.joinService = joinService;
	}

	@GetMapping("/join")
	public String joinP() {

		return "join";
	}

	@PostMapping("/joinProc")
	public String joinProcess(JoinDTO joinDTO) {

		System.out.println(joinDTO);
		
		joinService.joinProcess(joinDTO);

		return "redirect:/login";
	}
}
