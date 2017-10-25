package de.altenerding.biber.pinkie.presentation.team;

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
	private Logger logger;

	@ManagedProperty(value = "#{param.teamId}")
	private long teamId;
	private Team team;

	public void initTeam() {
		logger.info("Loading team data for id={}", teamId);
		team = teamService.getTeamById(teamId);
	}

	public List<Team> getTeams() {
		logger.info("Loading all teams from database");
		return teamService.getTeams();
	}

	@Inject
	public void setTeamService(TeamService teamService) {
		this.teamService = teamService;
	}

	public Team getTeam() {
		return team;
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public long getTeamId() {
		return teamId;
	}

	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}

}
