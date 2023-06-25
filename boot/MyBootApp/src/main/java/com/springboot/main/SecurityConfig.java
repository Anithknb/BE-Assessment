package com.springboot.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springboot.main.service.UserService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	
	private AuthenticationProvider getProvider() {
		DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
		dao.setPasswordEncoder(getEncoder());
		dao.setUserDetailsService(userService);
		return dao;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//			.withUser("msdhoni@incedo.com").password(getEncoder().encode("csk")).authorities("ADMIN")
//			.and()
//			.withUser("vkohli@incedo.com").password(getEncoder().encode("rcb")).authorities("USER");
		auth.authenticationProvider(getProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/inwardregister/add/{productId}/{godownId}/{supplierId}").hasAuthority("MANAGER")
			.antMatchers(HttpMethod.PUT, "/inwardregister/update/{id}/{productId}/{godownId}/{supplierId}").hasAuthority("MANAGER")
			.antMatchers(HttpMethod.DELETE, "/inwardregister/delete/{id}").hasAuthority("MANAGER")
			.antMatchers(HttpMethod.POST, "/customer/add").permitAll()
			.antMatchers(HttpMethod.POST, "/product/add").hasAnyAuthority("MANAGER", "USER")
			.antMatchers(HttpMethod.PUT, "/product/update/{id}").hasAnyAuthority("MANAGER", "USER")
			.antMatchers(HttpMethod.DELETE, "/product/delete/{id}").hasAnyAuthority("MANAGER", "USER")
			.antMatchers(HttpMethod.POST, "/supplier/add").authenticated()
			.antMatchers(HttpMethod.PUT, "/supplier/update/{id}").authenticated()
			.antMatchers(HttpMethod.DELETE, "/supplier/delete/{id}").authenticated()
			.antMatchers(HttpMethod.POST, "/admin/add").permitAll()
			.antMatchers(HttpMethod.POST, "/manager/add").hasAuthority("ADMIN")
			.antMatchers(HttpMethod.PUT, "/manager/update/{id}").hasAuthority("ADMIN")
			.antMatchers(HttpMethod.DELETE, "/manager/delete/{id}").hasAuthority("ADMIN")
			.antMatchers(HttpMethod.POST, "/employee/add/{managerId}").hasAuthority("ADMIN")
			.antMatchers(HttpMethod.PUT, "/employee/update/{id}/{managerId}").hasAuthority("ADMIN")
			.antMatchers(HttpMethod.DELETE, "/employee/delete/{id}").hasAuthority("ADMIN")
			.antMatchers(HttpMethod.GET, "/godown/report").hasAuthority("ADMIN")
			.antMatchers(HttpMethod.GET, "/outwardregister/report").hasAuthority("ADMIN")
			.anyRequest().permitAll()
			.and()
			.httpBasic()
			.and()
			.csrf().disable();
	}
	
	@Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

}