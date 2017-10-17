package de.altenerding.biber.pinkie.team.boundary;

import de.altenerding.biber.pinkie.team.entity.Team;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ManagedBean
@ViewScoped
public class TeamBean implements Serializable {

	private TeamService teamService;

	private Team team;

	public List<Team> getTeams() {
		return teamService.getTeams();
	}

	public Team getTeamById() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params =
				fc.getExternalContext().getRequestParameterMap();
		long id = Long.parseLong(params.get("teamId"));
		Team team = teamService.getTeamById(id);
		this.team = team;
		return team;
	}

	@Inject
	public void setTeamService(TeamService teamService) {
		this.teamService = teamService;
	}

	public Team getTeam() {
		return team;
	}
}
