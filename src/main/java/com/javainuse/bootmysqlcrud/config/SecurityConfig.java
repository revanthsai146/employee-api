package com.javainuse.bootmysqlcrud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.javainuse.bootmysqlcrud.service.UserDetailsServiceImpl;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// @Bean
	// SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	// 	http.httpBasic(Customizer.withDefaults());
	// 	http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
	// 	return http.build();
	// }

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/register", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**")  // Allow Swagger UI and API docs without authentication
                .permitAll()  // Allow access to these paths without authentication
                .anyRequest().authenticated()  // All other requests require authentication
            )
            .httpBasic(Customizer.withDefaults());  // Use basic authentication

        return http.build();
    }

	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}


}
