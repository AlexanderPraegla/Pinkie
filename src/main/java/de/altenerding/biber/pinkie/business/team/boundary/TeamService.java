package de.altenerding.biber.pinkie.business.team.boundary;

import de.altenerding.biber.pinkie.business.members.entity.Member;
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

	public List<Team> getCurrentTeams() {
		return teamProvider.getCurrentTeams();
	}

	public Team getTeamById(long id) {
		return teamProvider.getTeamById(id);
	}

	public void updateTeam(Team team) {
		teamProcessor.updateTeam(team);
	}

	public void updateTeams(List<Team> teams) {
		teamProcessor.updateTeams(teams);
	}

	public Team createTeam(Team team) {
		return teamProcessor.createTeam(team);
	}

	public void createTeams(List<Team> newTeams) {
		for (Team team : newTeams) {
			createTeam(team);
		}
	}

	public void archiveTeam(Team team) {
		teamProcessor.archiveTeam(team);
	}

	public void archiveTeams(List<Team> teams) {
		for (Team team : teams) {
			archiveTeam(team);
		}
	}

	public List<Member> getAllTrainers() {
		return teamProvider.getAllTrainers();
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
