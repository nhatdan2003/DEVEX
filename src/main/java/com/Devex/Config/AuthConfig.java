package com.Devex.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.Devex.Sevice.OAuthUserService;
import com.Devex.Sevice.UserSecurityService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
public class AuthConfig {
	@Bean
    public UserDetailsService userDetailsService() {
        return new UserSecurityService();
	}
	
	@Bean
	public DefaultOAuth2UserService oauthUserService() {
        return new OAuthUserService();
	}

	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@SuppressWarnings("removal")
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/css/**", "/admin/**", "/img/**").permitAll()
            .requestMatchers("/ad/**").hasAuthority("ADMIN")
            .requestMatchers("/manager/**").hasAnyAuthority("MANAGER", "ADMIN")
            .requestMatchers("/seller/**","/profile/**", "/cart/**").hasAnyAuthority("SELLER")
            .requestMatchers("/profile", "/profile/**", "/cart/**","/order/**").hasAnyAuthority("CUSTOMER")
            .anyRequest().permitAll()
            .and()
            .formLogin(
                    form -> form
                            .loginPage("/signin")
                            .loginProcessingUrl("/login")  	//[/login]
                            .successHandler(authenticationSuccessHandler())
                            .failureUrl("/signin?error=Login fail")
//            ).rememberMe(remember -> remember
//                    .rememberMeParameter("remember")	//// [remember-me]
            ).oauth2Login(oauth2 -> oauth2
                    .loginPage("/signin")
                    .userInfoEndpoint()
						.userService(oauthUserService())
					.and()	
                    .successHandler(oauth2SuccessHandler())
                    .failureUrl("/auth/login/error")
            ).logout(
                    logout -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessHandler(logoutSuccessHandler()) // Xử lý sau khi đăng xuất thành công
                    .permitAll()
            ).exceptionHandling()
			.accessDeniedPage("/404");


		
		return http.build();
    }

    
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }

    
    
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }
    
    @Bean
    public AuthenticationSuccessHandler oauth2SuccessHandler() {
        return new CustomOAuth2SuccessHandler();
    }
    
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }
}
