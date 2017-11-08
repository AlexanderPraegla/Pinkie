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

	public void initSchedule() {
		teamScheduleEntries = nuLigaDataService.getUpcomingGames();
	}

	/*
	Maybe this should be in an applicationScoped bean, which refreshes the data every 5 minutes
	and the bean is just getting the list. This would avoid db calls for every page load
	 */
	public List<TeamScheduleEntry> getRecentResults() {
		return nuLigaDataService.getRecentResults();
	}

	@Inject
	public void setNuLigaDataService(NuLigaDataService nuLigaDataService) {
		this.nuLigaDataService = nuLigaDataService;
	}

	public List<TeamScheduleEntry> getTeamScheduleEntries() {
		return teamScheduleEntries;
	}
}
