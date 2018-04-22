package de.altenerding.biber.pinkie.presentation.login;

import de.altenerding.biber.pinkie.business.login.boundary.AuthenticateService;
import de.altenerding.biber.pinkie.business.members.bounday.MemberService;
import de.altenerding.biber.pinkie.business.members.entity.Access;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import de.altenerding.biber.pinkie.presentation.session.UserSessionBean;
import net.bootsfaces.utils.FacesMessages;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Named
@RequestScoped
public class LoginBean {

	private AuthenticateService authenticateService;
	private UserSessionBean userSessionBean;
	private Logger logger;
	private String page;
	private String currentUrl;

    private String alias;
	private String password;
	private MemberService memberService;

	@PostConstruct
	public void init() {
		logger.info("page: {}", page);
	}

	public String login() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		String result;
		try {
            if (authenticateService.validate(alias, password)) {
                Member member = memberService.getMemberByAlias(alias);
				userSessionBean.setMember(member);
				logger.info("Login successful for member alias={}", member.getEmail());

				if (authenticateService.hasMemberOnetimePasswort(member)) {
					return "changePassword";
				}

                //check if member has missing notification settings -> redirect to display the missing ones
				result = "success";
			} else {
                logger.error("Login NOT successful for alias={}", alias);
				FacesMessages.error("Login fehlgeschlagen");
				return "error";
			}
		} catch (Exception e) {
			logger.info("Error while validating login", e);
			FacesMessages.error("Es ist ein Fehler beim Login aufgetreten");
            return "error";
		}

		if (page == null) {
			return result;
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
			return "";
		}
	}

	@Access(role = Role.MEMBER)
	public String logout() {
		userSessionBean.setMember(null);
		return "logout";
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPassword() {
		return password;
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Inject
	public void setAuthenticateService(AuthenticateService authenticateService) {
		this.authenticateService = authenticateService;
	}

	@Inject
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	@Inject
	public void setUserSessionBean(UserSessionBean userSessionBean) {
		this.userSessionBean = userSessionBean;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getCurrentUrl() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String requestURI = request.getRequestURI();
		String queryString = request.getQueryString();

		currentUrl = StringUtils.isEmpty(queryString) ? requestURI : requestURI + "?" + queryString;
		return currentUrl;
	}
}
