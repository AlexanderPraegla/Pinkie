package de.altenerding.biber.pinkie.business.team.boundary;

import de.altenerding.biber.pinkie.business.team.control.TeamProvider;
import de.altenerding.biber.pinkie.business.team.entity.Team;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TeamService {

	private TeamProvider teamProvider;

	public List<Team> getTeams() {
		return teamProvider.getTeams();
	}

	public Team getTeamById(long id) {
		return teamProvider.getTeamById(id);
	}

	@Inject
	public void setTeamProvider(TeamProvider teamProvider) {
		this.teamProvider = teamProvider;
	}
}
