package de.altenerding.biber.pinkie.business.nuLiga.boundary;

import de.altenerding.biber.pinkie.business.nuLiga.control.NuLigaProvider;
import de.altenerding.biber.pinkie.business.nuLiga.entity.StandingEntry;
import de.altenerding.biber.pinkie.business.nuLiga.entity.TeamScheduleEntry;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class NuLigaService {

	private NuLigaProvider nuLigaProvider;

	public List<StandingEntry> getTeamStandings(long teamId) {
		return nuLigaProvider.getTeamStandings(teamId);
	}
	public List<TeamScheduleEntry> getTeamSchedule(long teamId) {
		return nuLigaProvider.getTeamSchedule(teamId);
	}

	@Inject
	public void setNuLigaProvider(NuLigaProvider nuLigaProvider) {
		this.nuLigaProvider = nuLigaProvider;
	}
}
