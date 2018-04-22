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
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Named
@RequestScoped
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

	public Member getMember() {
		return member;
	}

    public void initEditMember() {
		member = memberService.getMemberById(memberId);
	}

    public void initDisplayProfileMember() {
        member = memberService.getMemberById(memberId);
    }

    public void initLoggedInMember() {
        member = userSession.getMember();
    }

	@Access(role = Role.ADMIN)
	public String createMember() throws Exception {
		logger.info("Creating new member with name={}", member.getFullName());
        String alias = member.getFirstName() + "." + member.getLastName();
		if (file != null) {
			Image image = fileService.uploadImage(file, FileCategory.IMAGES_MEMBER_PROFILE);
			member.setImage(image);
		}
        member.setAlias(alias);
		Member member = memberService.createMember(this.member);

		//Generate random password at first
		String randomString = UUID.randomUUID().toString();
        authenticateService.createLogin(member.getAlias(), randomString);

		return "/secure/admin/editMember.xhtml?faces-redirect=true&includeViewParams=true&memberId=" + member.getId();
	}

	@Access(role = Role.ADMIN)
	public String updateMember() throws Exception {
		logger.info("Updating member with name={}", member.getFullName());

		if (file != null) {
			Image image = fileService.uploadImage(file, FileCategory.IMAGES_MEMBER_PROFILE);
			member.setImage(image);
		}

		memberService.updateMember(member);

		return "/secure/admin/listMembers.xhtml?faces-redirect=true";
	}

	@Access(role = Role.MEMBER)
	public String updateProfile() throws Exception {
		logger.info("Updating profile for name={}", member.getFullName());

		if (file != null) {
			Image image = fileService.uploadImage(file, FileCategory.IMAGES_MEMBER_PROFILE);
			member.setImage(image);
		}

		memberService.updateMember(member);

		return "/secure/profile/profile.xhtml?faces-redirect=true&memberId=" + memberId;
	}

	@Access(role = Role.ADMIN)
	public List<Member> getMembers() {
		if (members == null) {
			members = memberService.getMembers();
		}
		return members;
	}


	public void filterMembers() {
		List<Member> filteredMembers = new ArrayList<>();

		if (members == null) {
			members = memberService.getMembers();
		}

		if (StringUtils.isEmpty(filterText)) {
			return;
		}

		for (Member member : members) {
			if (StringUtils.containsIgnoreCase(member.getFullName(), filterText)) {
				filteredMembers.add(member);
			}
		}

		members = filteredMembers;
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
