package me.zohar.lottery.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;

	@Autowired
	private AuthenticationSuccessHandler successHandler;

	@Autowired
	private AuthenticationFailHandler failHandler;

	@Autowired
	private LogoutHandler logoutHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/register").permitAll()
		.antMatchers("/masterControl/getInviteRegisterSetting").permitAll()
		.antMatchers("/userAccount/register").permitAll()
		.antMatchers("/userAccount/getUserAccountInfo").permitAll()
		.antMatchers("/game/findAllOpenGame").permitAll()
		.antMatchers("/recharge/muspayCallback").permitAll()
		.antMatchers("/pay-success").permitAll()
		.antMatchers("/betting/findTop50WinningRank").permitAll()
		.antMatchers("/systemNotice/findTop5PublishedSystemNotice").permitAll()
		.antMatchers("/lotteryInformation/**").permitAll()
		.antMatchers("/information-details").permitAll()
		.antMatchers("/lottery-information").permitAll()
		.anyRequest().authenticated()
		.and().formLogin().loginPage("/").loginProcessingUrl("/login")
		.successHandler(successHandler)
		.failureHandler(failHandler).permitAll()
		.and().logout().logoutUrl("/logout").logoutSuccessHandler(logoutHandler).permitAll();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/images/**", "/js/**", "/plugins/**");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.authenticationProvider(customAuthenticationProvider);
	}

}
