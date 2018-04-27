package de.altenerding.biber.pinkie.presentation.member;

import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import de.altenerding.biber.pinkie.business.file.entity.FileCategory;
import de.altenerding.biber.pinkie.business.file.entity.Image;
import de.altenerding.biber.pinkie.business.login.boundary.AuthenticateService;
import de.altenerding.biber.pinkie.business.members.bounday.MemberService;
import de.altenerding.biber.pinkie.business.members.entity.Access;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import de.altenerding.biber.pinkie.presentation.session.UserSessionBean;
import net.bootsfaces.utils.FacesMessages;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class MemberBean implements Serializable {

	private long memberId;
	private MemberService memberService;
	private AuthenticateService authenticateService;
	private Logger logger;
	private List<Member> members;
	private Member member = new Member();
	private Part file;
	private FileService fileService;
	private String filterText;
    @Inject
    private UserSessionBean userSession;

	public void initMemberById() {
		member = memberService.getMemberById(memberId);
	}

    public void initDisplayProfileMember() {
        if (memberId > 0) {
            member = memberService.getMemberById(memberId);
        } else {
            member = userSession.getMember();
        }
    }

    public void initLoggedInMember() {
        member = userSession.getMember();
    }

	@Access(role = Role.ADMIN)
	public String createMember() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		try {
			logger.info("Creating new member with name={}", member.getFullName());
			if (file != null) {
				Image image = fileService.uploadImage(file, FileCategory.IMAGES_MEMBER_PROFILE);
				member.setImage(image);
			}

			Member member = memberService.createMember(this.member);

			FacesMessages.info(member.getFullName(), "Erfolgreich angelegt");
			return "/secure/admin/listMembers.xhtml?faces-redirect=true";
		} catch (Exception e) {
			logger.error("Error while creating member", e);
			FacesMessages.error("Fehler beim Erstellen eines Mitglieds");
			return "/secure/admin/createMember.xhtml?faces-redirect=true";
		}
	}

	@Access(role = Role.ADMIN)
	public String updateMember() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		try {
			logger.info("Updating member with name={}", member.getFullName());

			if (file != null) {
				Image image = fileService.uploadImage(file, FileCategory.IMAGES_MEMBER_PROFILE);
				member.setImage(image);
			}

			memberService.updateMember(member);


			FacesMessages.info(member.getFullName(), "Erfolgreich aktualisiert");
			return "/secure/admin/listMembers.xhtml?faces-redirect=true";
		} catch (Exception e) {
			logger.error("Error while updating member", e);
			FacesMessages.error("Fehler beim Aktualisieren eines Mitglieds");
			return "/secure/admin/editMember.xhtml?faces-redirect=true&memberId=" + member.getId();
		}
	}

	@Access(role = Role.MEMBER)
	public String updateProfile() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		try {
			logger.info("Updating profile for name={}", member.getFullName());

			if (file != null) {
				Image image = fileService.uploadImage(file, FileCategory.IMAGES_MEMBER_PROFILE);
				member.setImage(image);
			}

			memberService.updateMember(member);


			FacesMessages.info(member.getFullName(), "Profil aktualisiert");
			return "/secure/profile/profile.xhtml?faces-redirect=true";
		} catch (Exception e) {

			logger.error("Error while updating profile", e);
			FacesMessages.error("Fehler beim Aktualisieren des Profils");
			return "/secure/admin/createMember.xhtml?faces-redirect=true";
		}
	}

	/**
	 * Called by the logged in member
	 *
	 * @return nav link
	 */
	@Access(role = Role.MEMBER)
	public String deleteMember() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		try {
			memberService.deleteMember(member);

			FacesMessages.info(member.getFullName(), "Mitglied gelöscht");

			return "/secure/admin/listMembers.xhtml?faces-redirect=true";
		} catch (Exception e) {
			logger.error("Error while deleting member", e);
			FacesMessages.error(member.getFullName(), "Fehler beim löschen");
			return "/secure/admin/deleteMember.xhtml?faces-redirect=true&memberId=" + member.getId();
		}
	}

	@Access(role = Role.ADMIN)
	public List<Member> getMembers() {
		if (members == null) {
			members = memberService.getMembers();
		}
		return members;
	}

	/**
	 * Method called async from frontend to filter list of members
	 */
	public void filterMembers() {
		List<Member> filteredMembers = new ArrayList<>();

		if (members == null) {
			members = memberService.getMembers();
		}

		if (StringUtils.isEmpty(filterText)) {
			members = memberService.getMembers();
			return;
		}

		for (Member member : members) {
			if (StringUtils.containsIgnoreCase(member.getFullName(), filterText)) {
				filteredMembers.add(member);
			}
		}

		members = filteredMembers;
	}

	public Member getMember() {
		return member;
	}

	@Inject
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Inject
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	public long getMemberId() {
		return memberId;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	@Inject
	public void setAuthenticateService(AuthenticateService authenticateService) {
		this.authenticateService = authenticateService;
	}

	public void setFilterText(String filterText) {
		this.filterText = filterText;
	}

	public String getFilterText() {
		return filterText;
	}
}
