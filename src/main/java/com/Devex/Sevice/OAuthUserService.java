package com.Devex.Sevice;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Devex.Config.CustomOAuth2User;

@Service
@Transactional
public class OAuthUserService extends DefaultOAuth2UserService  {
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User user =  super.loadUser(userRequest);
		System.out.println("CustomOAuth2UserService invoked");
		return new CustomOAuth2User(user);
	}
}
