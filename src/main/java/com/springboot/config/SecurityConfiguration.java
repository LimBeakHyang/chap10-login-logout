package com.springboot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
	
	@Bean
	SecurityFilterChain examMethod01(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(
			authorize -> authorize
			.requestMatchers("/member/**").hasAnyRole("USER", "ADMIN")
			//.requestMatchers("/manager/**").hasRole("MANAGER")
			.requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
			.requestMatchers("/admin/**").hasRole("ADMIN")
			.anyRequest().permitAll()
		)
		.formLogin(Customizer.withDefaults());
		return http.build();
	}

}
