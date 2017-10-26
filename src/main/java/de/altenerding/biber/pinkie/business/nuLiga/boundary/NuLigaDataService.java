package de.altenerding.biber.pinkie.business.nuLiga.boundary;

import de.altenerding.biber.pinkie.business.nuLiga.control.NuLigaDataProvider;
import de.altenerding.biber.pinkie.business.nuLiga.entity.StandingEntry;
import de.altenerding.biber.pinkie.business.nuLiga.entity.TeamScheduleEntry;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class NuLigaDataService {

	private NuLigaDataProvider nuLigaDataProvider;

	public List<StandingEntry> getTeamStandings(long teamId) {
		return nuLigaDataProvider.getTeamStandings(teamId);
	}
	public List<TeamScheduleEntry> getTeamSchedule(long teamId) {
		return nuLigaDataProvider.getTeamSchedule(teamId);
	}

	public List<TeamScheduleEntry> getUpcomingGames() {
		return nuLigaDataProvider.getUpcomingGames();
	}

	@Inject
	public void setNuLigaDataProvider(NuLigaDataProvider nuLigaDataProvider) {
		this.nuLigaDataProvider = nuLigaDataProvider;
	}
}
