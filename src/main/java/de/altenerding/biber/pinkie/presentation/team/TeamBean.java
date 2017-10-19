package de.altenerding.biber.pinkie.presentation.team;

import de.altenerding.biber.pinkie.business.team.boundary.TeamService;
import de.altenerding.biber.pinkie.business.team.entity.Team;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@RequestScoped
public class TeamBean implements Serializable {

	private TeamService teamService;
	private Logger logger;

	private Team team;

	public List<Team> getTeams() {
		logger.info("Loading all teams from database");
		return teamService.getTeams();
	}

	public String getTeamById(long id) throws Exception {
		logger.info("Getting team for id={}", id);
		if (id > 0) {
			this.team = teamService.getTeamById(id);
			return "team.xhtml";
		} else {
			logger.error("team id is smaller than 0");
			throw new Exception("team id is smaller than 0");
		}
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
}
