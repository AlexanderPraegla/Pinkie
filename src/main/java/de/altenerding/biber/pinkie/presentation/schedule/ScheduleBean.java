package de.altenerding.biber.pinkie.presentation.schedule;

import de.altenerding.biber.pinkie.business.nuLiga.boundary.NuLigaDataService;
import de.altenerding.biber.pinkie.business.nuLiga.entity.TeamScheduleEntry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@ManagedBean
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
