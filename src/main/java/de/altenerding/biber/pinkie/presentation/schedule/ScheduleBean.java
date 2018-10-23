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

	private static final int MAX_RESULT_RECENT_RESULTS_MAINPAGE = 5;
	private static final int MAX_RESULT_UPCOMING_MATCHES_MAINPAGE = 3;
	private NuLigaDataService nuLigaDataService;
	private List<TeamScheduleEntry> teamScheduleEntries;
	private List<TeamScheduleEntry> recentResults;
	private List<TeamScheduleEntry> upcomingMatches;

	public void initAllUpcomningMatches() {
		teamScheduleEntries = nuLigaDataService.getAllUpcomingMatches();
	}
	public void initNextUpcomningMatchDay() {
		teamScheduleEntries = nuLigaDataService.getNextUpcomingMatchDay();
	}

	public List<TeamScheduleEntry> getRecentResults() {
		if (recentResults == null) {
			recentResults = nuLigaDataService.getRecentResults(MAX_RESULT_RECENT_RESULTS_MAINPAGE);
		}
		return recentResults;
	}

	public List<TeamScheduleEntry> getUpcomingMatches() {
		if (upcomingMatches == null) {
			upcomingMatches = nuLigaDataService.getNextUpcomingMatches(MAX_RESULT_UPCOMING_MATCHES_MAINPAGE);
		}

		return upcomingMatches;
	}

	@Inject
	public void setNuLigaDataService(NuLigaDataService nuLigaDataService) {
		this.nuLigaDataService = nuLigaDataService;
	}

	public List<TeamScheduleEntry> getTeamScheduleEntries() {
		return teamScheduleEntries;
	}
}
