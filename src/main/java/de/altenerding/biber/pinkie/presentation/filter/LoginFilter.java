package de.altenerding.biber.pinkie.presentation.filter;

import de.altenerding.biber.pinkie.business.login.boundary.AuthenticateService;
import de.altenerding.biber.pinkie.business.login.entity.Login;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import de.altenerding.biber.pinkie.presentation.session.UserSessionBean;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@WebFilter(urlPatterns = "/secure/*", filterName = "LoginFilter")
public class LoginFilter implements Filter {

    @Inject
    private AuthenticateService authenticateService;
    @Inject
    private UserSessionBean userSessionBean;

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        if (authenticateService.authenticateLoggedInUserRole(Role.MEMBER)) {
            //Check if user has an onetime password and force him to change it before he can use any logged in site
            try {
                Member member = userSessionBean.getMember();
                Login login = authenticateService.getLoginByAlias(member.getAlias());

                if (login == null) {
                    userSessionBean.logout();
                    String contextPath = request.getContextPath();
                    String requestURI = request.getRequestURI();
                    String queryString = request.getQueryString();


                    String url = contextPath + "/public/login/login.xhtml?page=";
                    url += StringUtils.isEmpty(queryString) ? URLEncoder.encode(requestURI, "UTF-8") : URLEncoder.encode(requestURI + "?" + queryString, "UTF-8");

                    response.sendRedirect(url);
                    return;
                }

                // Check if member has onetime password set and force him to change it
                if (login.isOnetimePassword()) {
                    request.getServletContext()
                            .getRequestDispatcher("/secure/profile/changePassword.xhtml?faces-redirect=true")
                            .forward(req, res);
                    return;
                }
            } catch (Exception e) {
                throw new ServletException(e.getMessage(), e.getCause());
            }
            chain.doFilter(request, response);
        } else {
            String contextPath = request.getContextPath();
            String requestURI = request.getRequestURI();
            String queryString = request.getQueryString();


            String url = contextPath + "/public/login/login.xhtml?page=";
            url += StringUtils.isEmpty(queryString) ? URLEncoder.encode(requestURI, "UTF-8") : URLEncoder.encode(requestURI + "?" + queryString, "UTF-8");

            response.sendRedirect(url);
        }
    }

    @Override
    public void destroy() {

    }
}
