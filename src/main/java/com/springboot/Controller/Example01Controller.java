package com.springboot.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Example01Controller {
	@GetMapping("/exam01")
	public String requestMethod(Model model) {
		return "viewPage01";
	}
	@GetMapping("/home/main")
	public String requestMethod5(Model model) {
		model.addAttribute("data", "homePage.html");
		return "homePage";
	}
	
	@GetMapping("/member/main")
	public String requestMethod4(Model model) {
		model.addAttribute("data", "memberPage.html");
		return "memberPage";
	}
		
	@GetMapping("/manager/main")
	public String requestMethod3(Model model) {
		model.addAttribute("data", "managerPage.html");
		return "managerPage";
	}
	
	@GetMapping("/admin/main")
	public String requestMethod2(Model model) {
		model.addAttribute("data", "adminPage.html");
		return "adminPage";
	}
}
