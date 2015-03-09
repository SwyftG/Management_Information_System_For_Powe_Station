package org.eps.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class EpsAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	private String targetUrl;
	
	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		try {
			response.sendRedirect(request.getContextPath() + targetUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
