package com.springboot.Controller;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Example02Controller {
	@GetMapping("/exam02")
	public String requestMethod(Model model) {
		return "redirect:/member/user";
	}
	
	@GetMapping("/member/user")
	public String requestMethod2(Authentication authentication, Model model) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String username = userDetails.getUsername(); // 사용자 이름(계정) 가져오기
			String password = userDetails.getPassword(); // 사용자 비밀번호 가져오기
			model.addAttribute("data1", "/member/user");
			model.addAttribute("data2", "username");
			model.addAttribute("data3", password);
			// 사용자 역할 권한 가져오기
			Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
			for (GrantedAuthority item : authorities) {
				model.addAttribute("data4", item+ " ");
			}
			return "viewPage02";
		
	}
}
