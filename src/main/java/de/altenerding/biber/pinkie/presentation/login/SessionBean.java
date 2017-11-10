package de.altenerding.biber.pinkie.presentation.login;

import de.altenerding.biber.pinkie.business.login.boundary.AuthenticateService;
import de.altenerding.biber.pinkie.business.members.bounday.MemberService;
import de.altenerding.biber.pinkie.business.members.entity.Access;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.members.entity.Role;
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

	private AuthenticateService authenticateService;
	private Logger logger;

	private Member member = null;
	private String email;
	private String password;
	private MemberService memberService;


	public String login() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		if (authenticateService.validate(email, password)) {
			member = memberService.getMemberByEmail(email);
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
		member = null;
		return "/index.xhtml?faces-redirect=true";
	}

	public boolean isUserInRole(Role role) {
		return authenticateService.authenticateRole(role);
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

	@SuppressWarnings("CdiUnproxyableBeanTypesInspection")
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
}
