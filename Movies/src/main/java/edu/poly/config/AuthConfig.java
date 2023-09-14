package edu.poly.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AuthConfig {

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers(HttpMethod.GET, "/rest/**")
				.requestMatchers(HttpMethod.POST, "/rest/**")
				.requestMatchers(HttpMethod.PUT, "/rest/**")
				.requestMatchers(HttpMethod.DELETE, "/rest/**");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors(cors -> cors.disable()).csrf(csrf -> csrf.disable())
				.authorizeHttpRequests((authz) -> authz
						.requestMatchers("/movies/admin").hasRole("admin")
						.requestMatchers("/movies/admin/**").hasRole("admin")
						.requestMatchers("/account/savechange").authenticated()
						.requestMatchers("/user/information/**").authenticated()
						.requestMatchers("/movie/detail/**").authenticated()
						.requestMatchers("/vnpay_jsp/**").authenticated()
						.anyRequest().permitAll())
				.exceptionHandling((e) -> e
						.accessDeniedPage("/user/login")

				)

				.formLogin((login) -> login
						.loginPage("/user/login")
						.loginProcessingUrl("/auth/login")
						.defaultSuccessUrl("/home", false)
						.failureUrl("/user/login")
						.usernameParameter("username")
						.passwordParameter("password"))
				.rememberMe((remember) -> remember
						.rememberMeParameter("rememberme"))
				.logout((logout) -> logout
						.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
						.logoutSuccessUrl("/user/login"));
		return http.build();
	}
}
