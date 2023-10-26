package com.sta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.sta.domain.UserRole;
import com.sta.service.PrincipalOauth2UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	private final PrincipalOauth2UserService principalOauth2UserService;
	
	  @Bean
	  public WebSecurityCustomizer configure() {
	      return (web) -> web.ignoring()
	              .requestMatchers("/static/**");
	  }
	  
	  @Bean
	  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		  http
	          .authorizeHttpRequests(authorizeRequests ->
	                  authorizeRequests
	                          .requestMatchers("/security-login/login", "/security-login/", "/security-login/join").permitAll()
	                          .requestMatchers("/security-login/admin/**").hasAnyAuthority(UserRole.ADMIN.name())
	                          .anyRequest().authenticated()
	          )
	          .formLogin(formLogin ->
	                  formLogin
	                          .usernameParameter("userid")
	                          .passwordParameter("password")
	                          .loginPage("/security-login/login")
	                          .defaultSuccessUrl("/security-login")
	                          .failureUrl("/security-login/login")
	                          .permitAll()
	          )
	          .logout(logout ->
	                  logout
	                          .logoutUrl("/security-login/logout")
	                          .logoutSuccessUrl("/security-login/login")
	                          .invalidateHttpSession(true)
	                          .deleteCookies("JSESSIONID")
	          )
	          .csrf().disable()
	          .oauth2Login(oauth2Login -> {
	              oauth2Login
	                      .loginPage("/security-login/login")
	                      .defaultSuccessUrl("/security-login")
	                      .userInfoEndpoint()
	                      .userService(principalOauth2UserService);
	          });
	       
	
		  return http.build();
		          
	  }
	 
	    
}
