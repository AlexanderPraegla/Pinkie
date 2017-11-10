package de.altenerding.biber.pinkie.presentation.member;

import de.altenerding.biber.pinkie.business.login.boundary.AuthenticateService;
import de.altenerding.biber.pinkie.business.members.entity.Access;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import net.bootsfaces.utils.FacesMessages;
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
	private MemberBean memberBean;
	private Logger logger;
	private String password;

	@Access(role = Role.ADMIN)
	public String resetPassword() {
		memberBean.initMember();
		Member member = memberBean.getMember();
		String alias = member.getEmail();
		logger.info("Resetting password for alias={}", alias);
		authenticateService.resetPassword(alias, password);

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
	public void setAuthenticateService(AuthenticateService authenticateService) {
		this.authenticateService = authenticateService;
	}

	@Inject
	public void setMemberBean(MemberBean memberBean) {
		this.memberBean = memberBean;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
