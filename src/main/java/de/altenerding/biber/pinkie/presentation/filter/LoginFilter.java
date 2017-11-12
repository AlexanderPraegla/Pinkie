package de.altenerding.biber.pinkie.presentation.filter;

import de.altenerding.biber.pinkie.business.login.boundary.AuthenticateService;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import de.altenerding.biber.pinkie.presentation.session.UserSessionBean;

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
	@Inject
	private UserSessionBean userSessionBean;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if (authenticateService.authenticateRole(Role.MEMBER)) {
			//Check if user has an onetime password and force him to change it before he can use any logged in site
			try {
				if (authenticateService.hasMemberOnetimePasswort()) {
					long memberId = userSessionBean.getMember().getId();
					request.getServletContext()
							.getRequestDispatcher("/secure/profile/changePassword.xhtml?faces-redirect=true&memberId=" + memberId)
							.forward(req, res);
					return;
				}
			} catch (Exception e) {
				throw new ServletException(e.getMessage(), e.getCause());
			}

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
