package de.altenerding.biber.pinkie.presentation.schedule;

import de.altenerding.biber.pinkie.business.nuLiga.boundary.NuLigaDataService;
import de.altenerding.biber.pinkie.business.nuLiga.entity.ClubMeeting;

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
	private List<ClubMeeting> teamScheduleEntries;
	private List<ClubMeeting> recentResults;
	private List<ClubMeeting> upcomingMatches;

	public void initAllUpcomningMatches() {
		teamScheduleEntries = nuLigaDataService.getAllUpcomingMeetings();
	}

	public void initNextUpcomningMatchDay() {
		teamScheduleEntries = nuLigaDataService.getNextUpcomingMatchDay();
	}

	public List<ClubMeeting> getRecentResults() {
		if (recentResults == null) {
			recentResults = nuLigaDataService.getRecentResults(MAX_RESULT_RECENT_RESULTS_MAINPAGE);
		}
		return recentResults;
	}

	public List<ClubMeeting> getUpcomingMatches() {
		if (upcomingMatches == null) {
			upcomingMatches = nuLigaDataService.getNextUpcomingMeetings(MAX_RESULT_UPCOMING_MATCHES_MAINPAGE);
		}

		return upcomingMatches;
	}

	@Inject
	public void setNuLigaDataService(NuLigaDataService nuLigaDataService) {
		this.nuLigaDataService = nuLigaDataService;
	}

	public List<ClubMeeting> getTeamScheduleEntries() {
		return teamScheduleEntries;
	}
}
