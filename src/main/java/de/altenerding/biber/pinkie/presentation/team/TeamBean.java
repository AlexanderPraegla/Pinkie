package de.altenerding.biber.pinkie.presentation.team;

import de.altenerding.biber.pinkie.business.members.entity.Access;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import de.altenerding.biber.pinkie.business.nuLiga.boundary.NuLigaDataService;
import de.altenerding.biber.pinkie.business.nuLiga.entity.ClubMeeting;
import de.altenerding.biber.pinkie.business.nuLiga.entity.GroupTableTeam;
import de.altenerding.biber.pinkie.business.report.boundary.ReportService;
import de.altenerding.biber.pinkie.business.report.entity.Report;
import de.altenerding.biber.pinkie.business.season.entity.Season;
import de.altenerding.biber.pinkie.business.team.boundary.TeamService;
import de.altenerding.biber.pinkie.business.team.entity.Team;
import org.apache.logging.log4j.Logger;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class TeamBean implements Serializable {

	private TeamService teamService;
	private ReportService reportService;
	private NuLigaDataService nuLigaDataService;
	private Logger logger;

	private long teamId;
	private Team team;

	private List<Report> teamReports;
	private List<Team> teams;
	private List<GroupTableTeam> teamRank;
	private List<ClubMeeting> teamMeetings;

	public void initTeam() {
		logger.info("Loading team data for id={}", teamId);
		team = teamService.getTeamById(teamId);
	}

	public List<Team> getTeamBySeason(Season season) {
		if (season != null) {
			return teamService.getTeamsBySeason(season);
		} else {
			return teamService.getCurrentTeams();
		}
	}

	public List<Team> getTeams() {
		if (teams == null) {
			logger.info("Loading all teams from database");
			teams = teamService.getCurrentTeams();
		}
		return teams;
	}

	@Access(role = Role.ADMIN)
	public String updateTeamsOrder() {
		logger.info("Updating team order");
		teamService.updateTeams(teams);
		return "/secure/team/teamEditOverview.xhtml?faces-redirect=true";
	}

	@Access(role = Role.ADMIN)
	public String archiveTeam(Team team) {
		teamService.archiveTeam(team);
		return "/secure/team/teamEditOverview.xhtml?faces-redirect=true";
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
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	@Inject
	public void setNuLigaDataService(NuLigaDataService nuLigaDataService) {
		this.nuLigaDataService = nuLigaDataService;
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

	public void setTeamReports(List<Report> teamReports) {
		this.teamReports = teamReports;
	}

	public List<Report> getTeamReports() {
		if (teamReports == null && team != null) {
			teamReports = reportService.getReportsForTeam(team.getId(), team.getSeason().getId());
		}
		return teamReports;
	}

	public List<GroupTableTeam> getTeamRank() {
		if (teamRank == null) {
			teamRank = nuLigaDataService.getGroupTeamTable(team.getNuLigaGroupId());
		}
		return teamRank;
	}

	public void setTeamRank(List<GroupTableTeam> teamRank) {
		this.teamRank = teamRank;
	}

	public List<ClubMeeting> getTeamMeetings() {
		if (teamMeetings == null) {
			teamMeetings = nuLigaDataService.getTeamMeetings(team.getNuLigaGroupId());
		}
		return teamMeetings;
	}

	public void setTeamMeetings(List<ClubMeeting> teamMeetings) {
		this.teamMeetings = teamMeetings;
	}
}
