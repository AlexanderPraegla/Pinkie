package de.altenerding.biber.pinkie.members.bounday;

import de.altenerding.biber.pinkie.members.entity.Member;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Map;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	private MemberService memberService;
	private Logger logger;

	private Member member;
	private String email;
	private String password;
	private boolean loggedIn = false;


	public String login() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params =
				fc.getExternalContext().getRequestParameterMap();
		logger.info("Number of params={}", params.size());
//		FacesMessages.info("TestMesssage");

		loggedIn = true;
		return "index.xhtml";
	}

	public String logout() {
		loggedIn = false;
		return "index.xhtml";
	}

	public Member getMember() {
		return member;
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
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
