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
	private String passwordNew;

	public void initMember() {
		member = memberService.getMemberById(memberId);
	}

	@Access(role = Role.ADMIN)
	public String resetPassword() {
		if (validateRetypePassword()) {
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

	@Access(role = Role.MEMBER)
	public String changePassword() {
		String result;

		if (validateRetypePassword()) {
			result = "/secure/profile/changePassword.xhtml?" +
					"faces-redirect=true&includeViewParams=true&memberId=" + memberId;
		} else {
			try {
				Member member = memberService.getMemberById(memberId);
				String alias = member.getEmail();
				logger.info("Changing password for alias={}", alias);
				authenticateService.changePassword(alias, passwordOld, passwordNew);

				FacesMessages.info(member.getFullName(), "Passwort geändert");

				result = "/secure/profile/profile.xhtml?faces-redirect=true&includeViewParams=true&memberId=" + memberId;
			} catch (Exception e) {
				logger.error("Error while changing password", e);
				result = "/secure/profile/changePassword.xhtml?" +
						"faces-redirect=true&includeViewParams=true&memberId=" + memberId;
			}

		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return result;
	}

	private boolean validateRetypePassword() {
		if (!StringUtils.equals(passwordNew, passwordRetype)) {
			FacesMessages.error("Die eingegebenen Passwörter stimmen nicht überein!");
			return false;
		}
		return false;
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

	public void setPasswordNew(String passwordNew) {
		this.passwordNew = passwordNew;
	}

	public String getPasswordNew() {
		return passwordNew;
	}
}
