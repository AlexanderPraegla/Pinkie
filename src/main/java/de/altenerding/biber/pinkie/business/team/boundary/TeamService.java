package de.altenerding.biber.pinkie.business.team.boundary;

import de.altenerding.biber.pinkie.business.team.control.TeamProcessor;
import de.altenerding.biber.pinkie.business.team.control.TeamProvider;
import de.altenerding.biber.pinkie.business.team.entity.Team;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TeamService {

	private TeamProvider teamProvider;
	private TeamProcessor teamProcessor;

	public List<Team> getTeams() {
		return teamProvider.getTeams();
	}

	public Team getTeamById(long id) {
		return teamProvider.getTeamById(id);
	}

	public void updateTeam(Team team) {
		teamProcessor.updateTeam(team);
	}

	public Team createTeam(Team team) {
		return teamProcessor.createTeam(team);
	}

	@Inject
	public void setTeamProvider(TeamProvider teamProvider) {
		this.teamProvider = teamProvider;
	}

	@Inject
	public void setTeamProcessor(TeamProcessor teamProcessor) {
		this.teamProcessor = teamProcessor;
	}
}
