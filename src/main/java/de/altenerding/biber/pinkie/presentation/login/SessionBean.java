package de.altenerding.biber.pinkie.presentation.login;

import de.altenerding.biber.pinkie.business.login.boundary.LoginService;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import net.bootsfaces.utils.FacesMessages;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class SessionBean implements Serializable {

	private LoginService loginService;
	private Logger logger;

	private Member member = new Member();
	private String email;
	private String password;
	private boolean loggedIn = false;


	public String login() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		member = loginService.login(email, password);
		if (member == null) {
			FacesMessages.error("Login fehlgeschlagen");
			return "login.xhtml?faces-redirect=true";
		} else {
			logger.info("Login successfull for member email={}", member.getEmail());
		}

		loggedIn = true;
		return "profile.xhtml?faces-redirect=true&includeViewParams=true&memberId=" + member.getId();
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

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Inject
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
}
