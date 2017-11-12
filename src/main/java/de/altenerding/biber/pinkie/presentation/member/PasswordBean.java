package de.altenerding.biber.pinkie.presentation.member;

import de.altenerding.biber.pinkie.business.login.boundary.AuthenticateService;
import de.altenerding.biber.pinkie.business.members.bounday.MemberService;
import de.altenerding.biber.pinkie.business.members.entity.Access;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import de.altenerding.biber.pinkie.business.notification.NotificationService;
import net.bootsfaces.utils.FacesMessages;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class PasswordBean implements Serializable {

	private AuthenticateService authenticateService;
	private NotificationService notificationService;
	private MemberService memberService;
	private Logger logger;
	private long memberId;
	private Member member;
	private String password;
	private String passwordRetype;
	private String passwordOld;

	public void initMember() {
		member = memberService.getMemberById(memberId);
	}

	@Access(role = Role.ADMIN)
	public String resetPassword() {
		if (!StringUtils.equals(password, passwordRetype)) {
			FacesMessages.error("Die eingegebenen Passwörter stimmen nicht überein!");
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);
			return "/secure/admin/editMemberPassword.xhtml?faces-redirect=true&includeViewParams=true&memberId=" + memberId;
		}

		Member member = memberService.getMemberById(memberId);
		String alias = member.getEmail();
		logger.info("Resetting password for alias={}", alias);
		authenticateService.setOnetimePassword(alias, password);

		//Dummy method. Later there has to be an email sender to send the password
		notificationService.sendResettedPassword(member.getPrivateEmail(), password);

		FacesMessages.info(member.getFullName(), "Passwort neu gesetzt");
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return "/secure/admin/listMembers.xhtml?faces-redirect=true";
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Inject
	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@Inject
	public void setAuthenticateService(AuthenticateService authenticateService) {
		this.authenticateService = authenticateService;
	}

	@Inject
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordOld() {
		return passwordOld;
	}

	public void setPasswordOld(String passwordOld) {
		this.passwordOld = passwordOld;
	}

	public String getPasswordRetype() {
		return passwordRetype;
	}

	public void setPasswordRetype(String passwordRetype) {
		this.passwordRetype = passwordRetype;
	}

	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
}
