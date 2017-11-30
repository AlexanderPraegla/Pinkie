package de.altenerding.biber.pinkie.presentation.team;

import de.altenerding.biber.pinkie.business.members.entity.Access;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import de.altenerding.biber.pinkie.business.nuLiga.boundary.NuLigaDataService;
import de.altenerding.biber.pinkie.business.nuLiga.entity.StandingEntry;
import de.altenerding.biber.pinkie.business.nuLiga.entity.TeamScheduleEntry;
import de.altenerding.biber.pinkie.business.report.boundary.ReportService;
import de.altenerding.biber.pinkie.business.report.entity.Report;
import de.altenerding.biber.pinkie.business.team.boundary.TeamService;
import de.altenerding.biber.pinkie.business.team.entity.Team;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class TeamBean implements Serializable {

	private TeamService teamService;
	private ReportService reportService;
	private NuLigaDataService nuLigaDataService;
	private Logger logger;

	private long teamId;
	private Team team;

	private List<Report> teamReports;
	private List<Team> teams;
	private List<StandingEntry> teamStandings;
	private List<TeamScheduleEntry> teamSchedule;

	public void initTeam() {
		logger.info("Loading team data for id={}", teamId);
		team = teamService.getTeamById(teamId);
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

	public List<StandingEntry> getTeamStandings() {
		if (teamStandings == null) {
			teamStandings = nuLigaDataService.getTeamStandings(teamId);
		}
		return teamStandings;
	}

	public void setTeamStandings(List<StandingEntry> teamStandings) {
		this.teamStandings = teamStandings;
	}

	public List<TeamScheduleEntry> getTeamSchedule() {
		if (teamSchedule == null) {
			teamSchedule = nuLigaDataService.getTeamSchedule(teamId);
		}
		return teamSchedule;
	}

	public void setTeamSchedule(List<TeamScheduleEntry> teamSchedule) {
		this.teamSchedule = teamSchedule;
	}
}
