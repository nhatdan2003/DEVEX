package com.Devex.Config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.Devex.Entity.UserRole;
import com.Devex.Sevice.OAuthUserService;
import com.Devex.Sevice.UserRoleService;
import com.Devex.Sevice.UserSecurityService;

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
            .requestMatchers("/profile/update", "/message/**").authenticated()
            .requestMatchers("/ad/**").hasAuthority("ADMIN")
            .requestMatchers("/manager/**").hasAnyAuthority("MANAGER", "ADMIN")
            .requestMatchers("/seller/**").hasAnyAuthority("SELLER")
            .requestMatchers("/profile/**", "/message/**", "/cart/**","/order/**").hasAnyAuthority("CUSTOMER")
            .anyRequest().permitAll()
            .and()
            .formLogin(
                    form -> form
                            .loginPage("/signin")
                            .loginProcessingUrl("/login")  	//[/login]
                            .successHandler(authenticationSuccessHandler())
                            .failureUrl("/signin?error=Login fail")
//         ).rememberMe(remember -> remember
//                            .rememberMeParameter("remember") // Tham số trong form để bật Remember Me
//                            .tokenValiditySeconds(86400) // Số giây hợp lệ của token (ví dụ: 86400 giây = 1 ngày)
//                            .key("yourSecretKey") // Khóa bí mật để tạo token (thay thế bằng khóa thực tế)
//                            .userDetailsService(new UserSecurityService()) // Cung cấp UserDetailsService
//                    
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
    
  
    @Bean
    public FilterRegistrationBean<CustomAuthorizationFilter> customAuthorizationFilterBean(UserRoleService userRoleService) {
        FilterRegistrationBean<CustomAuthorizationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomAuthorizationFilter(userRoleService));
        registrationBean.addUrlPatterns("/*"); // Áp dụng filter cho tất cả các URL
        return registrationBean;
    }
    
}
