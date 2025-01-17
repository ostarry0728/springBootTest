package com.zeus.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.zeus.common.security.CustomAccessDeniedHandler;
import com.zeus.common.security.CustomLoginSuccessHandler;
import com.zeus.common.security.CustomNoOpPasswordEncoder;
import com.zeus.common.security.CustomUserDetailsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

	@Autowired
	DataSource dataSource;

	@Bean
	SecurityFilterChain filterCahin(HttpSecurity http) throws Exception {
		log.info("SecurityConfig");
		// 1. csrf 토큰을 비활성화
		http.csrf().disable();

		// 2. 모든사이드에 인증이 되면 모두 인가가 된 상태
		// /board/list 인증 ok.
//		http.authorizeRequests().requestMatchers("/board/list").permitAll();
		// /board/register 인증, 인가(MEMBER)
//		http.authorizeRequests().requestMatchers("/board/register").hasRole("MEMBER");

//		http.authorizeRequests().requestMatchers("/notice/list").permitAll();

//		http.authorizeRequests().requestMatchers("/notice/register").hasRole("ADMIN");

		// 3. id, password는 기존것을 사용하지 않고 우리가 설계한 아이디와 패스워드, 인가 정책을 세워서 제시.
		// 4. 아이디나, 패스워드가 잘못되었을때 화면에 인증실패 화면이 출력
//		http.exceptionHandling().accessDeniedPage("/accessError");

		// 5. 로그인 기본폼 사용을 지정
		// 개발자가 정의한 로그인 페이지의 URI를 지정한다.
		// 로그인 성공 후 처리를 담당하는 처리자로 지정한다.
		http.formLogin().loginPage("/login").successHandler(createAuthenticationSuccessHandler());

		// 로그아웃 처리를 위한 URI를 지정하고, 로그아웃한 후에 세션을 무효화 한다.
		http.logout().logoutUrl("/logout").invalidateHttpSession(true).deleteCookies("rememberme", "JSESSION_ID");
		http.exceptionHandling().accessDeniedHandler(createAccessDeniedHandler());

		http.rememberMe().key("zeus").tokenRepository(createJDBCRepository()).tokenValiditySeconds(60 * 60 * 24);
		return http.build();
	}

	private PersistentTokenRepository createJDBCRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		return repo;
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 지정된 아이디와 패스워드로 로그인이 가능하도록 설정한다.
		auth.userDetailsService(createUserDetailsService()).passwordEncoder(createPasswordEncoder());
	}

	// 스프링 시큐리티의 UserDetailsService를 구현한 클래스를 빈으로 등록한다.
	@Bean
	public UserDetailsService createUserDetailsService() {
		return new CustomUserDetailsService();
	}

	// 사용자가 정의한 비번 암호화 처리기를 빈으로 등록한다.
	@Bean
	public PasswordEncoder createPasswordEncoder() {
		return new CustomNoOpPasswordEncoder();
	}

	@Bean
	public AccessDeniedHandler createAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	// CustomLoginSuccessHandler를 빈으로 등록한다.
	@Bean
	public AuthenticationSuccessHandler createAuthenticationSuccessHandler() {
		return new CustomLoginSuccessHandler();
	}

}
