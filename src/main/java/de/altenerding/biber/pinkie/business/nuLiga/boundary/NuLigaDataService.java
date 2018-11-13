package de.altenerding.biber.pinkie.business.nuLiga.boundary;

import de.altenerding.biber.pinkie.business.nuLiga.control.NuLigaApiRequester;
import de.altenerding.biber.pinkie.business.nuLiga.control.NuLigaDataProvider;
import de.altenerding.biber.pinkie.business.nuLiga.entity.ClubMeeting;
import de.altenerding.biber.pinkie.business.nuLiga.entity.GroupTableTeam;
import nu.liga.open.rs.v2014.dto.championships.TeamAbbrDTO;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class NuLigaDataService {

	@Inject
	private NuLigaDataProvider nuLigaDataProvider;
	@Inject
	private NuLigaApiRequester nuLigaApiRequester;
	@Inject
	private Logger logger;

	public List<TeamAbbrDTO> getTeamsOfCurrentSeason() {
		return nuLigaApiRequester.getTeamsOfCurrentSeason();
	}

	public List<GroupTableTeam> getGroupTeamTable(String groupId) {
		return nuLigaDataProvider.getGroupTeamTable(groupId);
	}

	public List<ClubMeeting> getTeamMeetings(String groupId) {
		return nuLigaDataProvider.getTeamMeetings(groupId);
	}
	public List<ClubMeeting> getNextUpcomingMeetings(int maxResults) {
		return nuLigaDataProvider.getNextUpcomingMeetings(maxResults);
	}

	public List<ClubMeeting> getNextUpcomingMatchDay() {
		return nuLigaDataProvider.getNextUpcomingMatchDay();
	}

	public List<ClubMeeting> getAllUpcomingMeetings() {
		return nuLigaDataProvider.getAllUpcomingMatches();
	}

	public List<ClubMeeting> getRecentResults(int maxResult) {
		logger.info("Loading recent nuLiga results");
		return nuLigaDataProvider.getRecentResults(maxResult);
	}
}
