package de.altenerding.biber.pinkie.presentation.filter;

import de.altenerding.biber.pinkie.business.login.boundary.AuthenticateService;
import de.altenerding.biber.pinkie.business.members.entity.Role;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/secure/report/*", "/secure/team/*", "/secure/mainpage/*"}, filterName = "PressFilter")
public class PressFilter implements Filter{

	@Inject
	private AuthenticateService authenticateService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if (authenticateService.authenticateRole(Role.PRESS)) {
			chain.doFilter(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
	}

	@Override
	public void destroy() {

	}
}
