package de.altenerding.biber.pinkie.presentation.filter;

import de.altenerding.biber.pinkie.business.login.boundary.AuthenticateService;
import de.altenerding.biber.pinkie.business.members.entity.Role;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/secure/*", filterName = "LoginFilter")
public class LoginFilter implements Filter{

	@Inject
	private AuthenticateService authenticateService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if (authenticateService.authenticateRole(Role.MEMBER)) {
			chain.doFilter(request, response);
		} else {
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/public/login/login.xhtml");
		}
	}

	@Override
	public void destroy() {

	}
}
