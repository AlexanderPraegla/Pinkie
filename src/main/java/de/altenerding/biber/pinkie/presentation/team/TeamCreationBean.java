package de.altenerding.biber.pinkie.presentation.team;

import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import de.altenerding.biber.pinkie.business.file.entity.FileDirectory;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.team.boundary.TeamService;
import de.altenerding.biber.pinkie.business.team.entity.Team;
import de.altenerding.biber.pinkie.presentation.member.MemberBean;
import net.bootsfaces.utils.FacesMessages;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.Part;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@RequestScoped
public class TeamCreationBean implements Serializable {

	private TeamService teamService;
	private FileService fileService;
	@ManagedProperty(value = "#{memberBean}")
	private MemberBean memberBean;
	private Logger logger;

	private Team team = new Team();
	private Part file;
	private String memberIndex = "";
	private String trainerIndex = "";

	public String createTeam() throws Exception {
		String result;
		try {

			if (file != null) {
				String fileName = fileService.uploadImage(file, FileDirectory.TEAM_IMAGE);
				team.setImageName(fileName);
			}

			List<Member> teamMembers = new ArrayList<>();
			List<Member> members = memberBean.getMembers();
			for (String index : StringUtils.split(memberIndex, ",")) {
				teamMembers.add(members.get(Integer.parseInt(index)));
			}
			team.setTeamMembers(teamMembers);

			List<Member> trainer = new ArrayList<>();
			for (String index : StringUtils.split(trainerIndex, ",")) {
				trainer.add(members.get(Integer.parseInt(index)));
			}
			team.setTrainers(trainer);

			Team team = teamService.createTeam(this.team);
			FacesMessages.info(this.team.getName(), "Mannschaft erstellt");
			result = "team.xhtml?faces-redirect=true&includeViewParams=true&teamId=" + team.getId();
		} catch (Exception e) {
			FacesMessages.info(e.getMessage());
			logger.error("Error while uploading file", e);
			result = "teamCreate.xhtml?faces-redirect=true&includeViewParams=true";
		}

		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return result;
	}

	@Inject
	public void setTeamService(TeamService teamService) {
		this.teamService = teamService;
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Inject
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public Team getTeam() {
		return team;
	}

	public String getMemberIndex() {
		return memberIndex;
	}

	public void setMemberIndex(String memberIndex) {
		this.memberIndex = memberIndex;
	}

	public String getTrainerIndex() {
		return trainerIndex;
	}

	public void setTrainerIndex(String trainerIndex) {
		this.trainerIndex = trainerIndex;
	}

	public void setMemberBean(MemberBean memberBean) {
		this.memberBean = memberBean;
	}
}
