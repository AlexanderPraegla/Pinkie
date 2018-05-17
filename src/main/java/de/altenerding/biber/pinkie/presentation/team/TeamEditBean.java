package de.altenerding.biber.pinkie.presentation.team;

import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import de.altenerding.biber.pinkie.business.file.entity.FileCategory;
import de.altenerding.biber.pinkie.business.file.entity.Image;
import de.altenerding.biber.pinkie.business.members.bounday.MemberService;
import de.altenerding.biber.pinkie.business.members.entity.Access;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import de.altenerding.biber.pinkie.business.team.boundary.TeamService;
import de.altenerding.biber.pinkie.business.team.entity.Team;
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
public class TeamEditBean implements Serializable {

	private TeamService teamService;
	private FileService fileService;
	private MemberService memberService;
	private Logger logger;

	private long teamId;
	private Team team = new Team();
	private Part file;
	private List<Member> members;
	private String imageDescription;
	private String memberIndex = "";
	private String trainerIndex = "";

	public void initTeam() {
		logger.info("Loading team data for id={}", teamId);
		team = teamService.getTeamById(teamId);
		members = memberService.getMembers();
		List<Integer> selectedTeamMemberIds = new ArrayList<>();
		List<Integer> selectedTrainerIds = new ArrayList<>();
		for (Member member : team.getTeamMembers()) {
			selectedTeamMemberIds.add(members.indexOf(member));
		}

		for (Member member : team.getTrainers()) {
			selectedTrainerIds.add(members.indexOf(member));
		}

		//creating preselected values for dropdown menu
		trainerIndex = StringUtils.join(selectedTrainerIds, ",");
		memberIndex = StringUtils.join(selectedTeamMemberIds, ",");
	}

	@Access(role = Role.PRESS)
	public String updateTeam() throws Exception {
		String result;
		try {

			if (file != null) {
				Image image = fileService.uploadImage(file, FileCategory.IMAGES_TEAM_GROUP, imageDescription);
				team.setImage(image);
			}

			List<Member> teamMembers = new ArrayList<>();
			for (String index : StringUtils.split(memberIndex, ",")) {
				teamMembers.add(members.get(Integer.parseInt(index)));
			}
			team.setTeamMembers(teamMembers);

			List<Member> trainers = new ArrayList<>();
			for (String index : StringUtils.split(trainerIndex, ",")) {
				trainers.add(members.get(Integer.parseInt(index)));
			}

			if (trainers.size() > 0) {
				team.setTrainers(trainers);
			} else {
				team.setTrainers(null);
			}

			teamService.updateTeam(team);
			FacesMessages.info(team.getName(), "Team aktualisiert");
            result = "/secure/team/teamEditOverview.xhtml?faces-redirect=true&includeViewParams=true";
		} catch (Exception e) {
			FacesMessages.info(e.getMessage());
			logger.error("Error while uploading file", e);
			result = "/secure/team/teamEdit.xhtml?faces-redirect=true&includeViewParams=true&teamId=" + teamId;
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

	@Inject
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
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

	public long getTeamId() {
		return teamId;
	}

	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}

	public List<Member> getMembers() {
		if (members == null) {
			members = memberService.getMembers();
		}
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
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

	public String getImageDescription() {
		return imageDescription;
	}

	public void setImageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
	}
}
