package de.altenerding.biber.pinkie.presentation.team;

import de.altenerding.biber.pinkie.business.nuLiga.boundary.NuLigaService;
import de.altenerding.biber.pinkie.business.nuLiga.entity.StandingEntry;
import de.altenerding.biber.pinkie.business.nuLiga.entity.TeamScheduleEntry;
import de.altenerding.biber.pinkie.business.report.boundary.ReportService;
import de.altenerding.biber.pinkie.business.report.entity.Report;
import de.altenerding.biber.pinkie.business.team.boundary.TeamService;
import de.altenerding.biber.pinkie.business.team.entity.Team;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@RequestScoped
public class TeamBean implements Serializable {

	private TeamService teamService;
	private ReportService reportService;
	private NuLigaService nuLigaService;
	private Logger logger;

	@ManagedProperty(value = "#{param.teamId}")
	private long teamId;
	private Team team;

	private String test;
	private List<Report> teamReports;
	private List<StandingEntry> teamStandings;
	private List<TeamScheduleEntry> teamSchedule;

	public void initTeam() {
		logger.info("Loading team data for id={}", teamId);
		team = teamService.getTeamById(teamId);
		teamReports = reportService.getReportsForTeam(team.getId(), team.getSeason().getId());
		teamStandings = nuLigaService.getTeamStandings(teamId);
		teamSchedule = nuLigaService.getTeamSchedule(teamId);
	}

	public List<Team> getTeams() {
		logger.info("Loading all teams from database");
		return teamService.getTeams();
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
	public void setNuLigaService(NuLigaService nuLigaService) {
		this.nuLigaService = nuLigaService;
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

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public void setTeamReports(List<Report> teamReports) {
		this.teamReports = teamReports;
	}

	public List<Report> getTeamReports() {
		return teamReports;
	}

	public List<StandingEntry> getTeamStandings() {
		return teamStandings;
	}

	public void setTeamStandings(List<StandingEntry> teamStandings) {
		this.teamStandings = teamStandings;
	}

	public List<TeamScheduleEntry> getTeamSchedule() {
		return teamSchedule;
	}

	public void setTeamSchedule(List<TeamScheduleEntry> teamSchedule) {
		this.teamSchedule = teamSchedule;
	}
}
