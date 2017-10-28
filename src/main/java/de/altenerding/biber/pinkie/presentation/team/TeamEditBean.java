package de.altenerding.biber.pinkie.presentation.team;

import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import de.altenerding.biber.pinkie.business.file.entity.FileDirectory;
import de.altenerding.biber.pinkie.business.members.bounday.MemberService;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.team.boundary.TeamService;
import de.altenerding.biber.pinkie.business.team.entity.Team;
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
public class TeamEditBean implements Serializable {

	private TeamService teamService;
	private FileService fileService;
	private MemberService memberService;
	private Logger logger;

	@ManagedProperty(value = "#{param.teamId}")
	private long teamId;
	private Team team = new Team();
	private Part file;
	private List<Member> members;
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

	public String updateTeam() throws Exception {
		String result;
		try {
			Team team = teamService.getTeamById(teamId);
			team.setName(this.team.getName());
			team.setLeague(this.team.getLeague());
			team.setUrlStanding(this.team.getUrlStanding());
			team.setImageDescription(this.team.getImageDescription());
			team.setAdditionalInfo(this.team.getAdditionalInfo());

			if (file != null) {
				String fileName = fileService.uploadImage(file, FileDirectory.TEAM_IMAGE);
				team.setImageName(fileName);
			}

			List<Member> teamMembers = new ArrayList<>();
			for (String index : StringUtils.split(memberIndex, ",")) {
				teamMembers.add(members.get(Integer.parseInt(index)));
			}
			team.setTeamMembers(teamMembers);

			List<Member> trainer = new ArrayList<>();
			for (String index : StringUtils.split(trainerIndex, ",")) {
				trainer.add(members.get(Integer.parseInt(index)));
			}
			team.setTrainers(trainer);

			teamService.updateTeam(team);
			FacesMessages.info(team.getName(), "Team aktualisiert");
			result = "team.xhtml?faces-redirect=true&includeViewParams=true&teamId=" + teamId;
		} catch (Exception e) {
			FacesMessages.info(e.getMessage());
			logger.error("Error while uploading file", e);
			result = "teamEdit.xhtml?faces-redirect=true&includeViewParams=true&teamId=" + teamId;
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
}