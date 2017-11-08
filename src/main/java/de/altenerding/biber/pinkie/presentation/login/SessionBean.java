package de.altenerding.biber.pinkie.presentation.login;

import de.altenerding.biber.pinkie.business.login.boundary.LoginService;
import de.altenerding.biber.pinkie.business.members.bounday.MemberService;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import net.bootsfaces.utils.FacesMessages;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class SessionBean implements Serializable {

	private LoginService loginService;
	private Logger logger;

	private Member member = new Member();
	private String email;
	private String password;
	private boolean loggedIn = false;
	private MemberService memberService;


	public String login() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		if (loginService.login(email, password)) {
			member = memberService.getMemberByEmail(email);
			logger.info("Login successful for member alias={}", member.getEmail());
			loggedIn = true;
			return "profile.xhtml?faces-redirect=true&includeViewParams=true&memberId=" + member.getId();
		} else {
			logger.error("Login NOT successful for alias={}", email);
			FacesMessages.error("Login fehlgeschlagen");
			return "login.xhtml?faces-redirect=true";
		}

	}

	public String logout() {
		loggedIn = false;
		member = null;
		return "index.xhtml?faces-redirect=true";
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
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

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public boolean getIsLoggedIn() {
		return loggedIn;
	}

	@SuppressWarnings("CdiUnproxyableBeanTypesInspection")
	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Inject
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	@Inject
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
}
