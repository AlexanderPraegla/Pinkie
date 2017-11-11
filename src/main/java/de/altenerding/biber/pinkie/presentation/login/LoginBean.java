package de.altenerding.biber.pinkie.presentation.login;

import de.altenerding.biber.pinkie.business.login.boundary.AuthenticateService;
import de.altenerding.biber.pinkie.business.members.bounday.MemberService;
import de.altenerding.biber.pinkie.business.members.entity.Access;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import de.altenerding.biber.pinkie.presentation.session.UserSessionBean;
import net.bootsfaces.utils.FacesMessages;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@RequestScoped
public class LoginBean {

	private AuthenticateService authenticateService;
	private UserSessionBean userSessionBean;
	private Logger logger;

	private String email;
	private String password;
	private MemberService memberService;

	public String login() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		if (authenticateService.validate(email, password)) {
			Member member = memberService.getMemberByEmail(email);
			userSessionBean.setMember(member);
			logger.info("Login successful for member alias={}", member.getEmail());
			return "/secure/profile/profile.xhtml?faces-redirect=true&includeViewParams=true&memberId=" + member.getId();
		} else {
			logger.error("Login NOT successful for alias={}", email);
			FacesMessages.error("Login fehlgeschlagen");
			return "/public/login/login.xhtml?faces-redirect=true";
		}

	}

	@Access(role = Role.MEMBER)
	public String logout() {
		userSessionBean.setMember(null);
		return "/index.xhtml?faces-redirect=true";
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
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
}
