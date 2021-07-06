package hr.petkovic.diplomskibe.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	DataSource dataSource;

	public SecurityConfiguration(DataSource ds) {
		dataSource = ds;
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new MyPasswordEncoder();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth
		.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(getPasswordEncoder())
		.usersByUsernameQuery("SELECT username, password, enabled FROM users where username=?")
		.authoritiesByUsernameQuery("SELECT username, \'ROLE_USER\' FROM users where username=?");
		
	}

	@Override
	public void configure (HttpSecurity http) throws Exception {
		http.cors()
		.and().csrf().disable()
		.authorizeRequests()
		.antMatchers("/login","/api/**").permitAll()
		.and().formLogin();
	}
	private class MyPasswordEncoder implements PasswordEncoder {

		@Override
		public String encode(CharSequence rawPassword) {
			return rawPassword.toString();
		}

		@Override
		public boolean matches(CharSequence rawPassword, String encodedPassword) {
			return rawPassword.toString().matches(encodedPassword);
		}

	}
}
