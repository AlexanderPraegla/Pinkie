package de.altenerding.biber.pinkie.presentation.schedule;

import de.altenerding.biber.pinkie.business.nuLiga.boundary.NuLigaDataService;
import de.altenerding.biber.pinkie.business.nuLiga.entity.TeamScheduleEntry;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class ScheduleBean {

	private NuLigaDataService nuLigaDataService;
	private List<TeamScheduleEntry> teamScheduleEntries;
	private List<TeamScheduleEntry> recentResults;

	public void initSchedule() {
		teamScheduleEntries = nuLigaDataService.getUpcomingGames();
	}

	public List<TeamScheduleEntry> getRecentResults() {
		if (recentResults == null) {
			recentResults = nuLigaDataService.getRecentResults();
		}
		return recentResults;
	}

	@Inject
	public void setNuLigaDataService(NuLigaDataService nuLigaDataService) {
		this.nuLigaDataService = nuLigaDataService;
	}

	public List<TeamScheduleEntry> getTeamScheduleEntries() {
		return teamScheduleEntries;
	}
}
