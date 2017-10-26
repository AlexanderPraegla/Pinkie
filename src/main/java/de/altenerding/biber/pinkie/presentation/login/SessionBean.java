package de.altenerding.biber.pinkie.presentation.login;

import de.altenerding.biber.pinkie.business.members.bounday.MemberService;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class SessionBean implements Serializable {

	private MemberService memberService;
	private Logger logger;

	private Member member;
	private String email;
	private String password;
	private boolean loggedIn = false;


	public String login() {
		member = memberService.getMemberById(1);
		logger.info("Successful login for user with id={}", member.getId());
		loggedIn = true;
		return "profile.xhtml?faces-redirect=true&includeViewParams=true&memberId=" + member.getId();
	}

	public void logout() {
		loggedIn = false;
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
