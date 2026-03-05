package com.springboot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService users() {
		UserDetails user = User.builder()
		.username("guest")
		.password(passwordEncoder().encode("g1234"))
		.roles("USER")
		.build();
		
		UserDetails manager = User.builder()
		.username("manager")
		.password(passwordEncoder().encode("m1234"))
		.roles("MANAGER")
		.build();
		
		UserDetails admin = User.builder()
		.username("admin")
		.password(passwordEncoder().encode("a1234"))
		.roles("ADMIN")
		.build();
		return new InMemoryUserDetailsManager(user, manager, admin);
	}
	
	/* @Bean
	public SecurityFilterChain examMethod01(HttpSecurity http) throws Exception{
		http
		// GPT 내용추가 지우기(/admin/** ).securityMatcher("/member/**", "/manager/**") 
			.authorizeHttpRequests(
			authorize -> authorize
			.requestMatchers("/member/**").hasAnyRole("USER", "ADMIN")
			.requestMatchers("/manager/**").hasRole("MANAGER")
			//'manager'도 전부 로그인 가능하게 하려면 .hasRole -> .hasAnyRole
			//.requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
			.requestMatchers("/admin/**").hasRole("ADMIN")
			.anyRequest().permitAll()
		)
		    .formLogin(Customizer.withDefaults());  // GPT 내용삭제
		return http.build();
	} */
	
	
	@Bean
	public SecurityFilterChain examMethod04(HttpSecurity http) throws Exception{
		http
		// GPT 내용추가 .securityMatcher("/**")  
		.csrf(AbstractHttpConfigurer::disable)   //CSRF 비활성화
		.authorizeHttpRequests(
				authorizeRequests -> authorizeRequests
				.requestMatchers("/admin").hasRole("ADMIN")
				.anyRequest().permitAll()	
				)
		
		
		// 로그인 설정
		.formLogin(
				formLogin -> formLogin
				.loginPage("/exam05") // 사용자 정의 로그인 페이지
				.loginProcessingUrl("/exam05")
				.defaultSuccessUrl("/admin") // 로그인 성공 후 이동 페이지
				.usernameParameter("username")
				.passwordParameter("password")
				.failureUrl("/loginfailed")  // 로그인 실패 후 이동 페이지
				)
		// 로그아웃 설정
		.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/exam05")
				);
		return http.build();
	}

}
