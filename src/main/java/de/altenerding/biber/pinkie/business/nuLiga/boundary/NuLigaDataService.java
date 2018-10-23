package de.altenerding.biber.pinkie.business.nuLiga.boundary;

import de.altenerding.biber.pinkie.business.nuLiga.control.NuLigaDataProcessor;
import de.altenerding.biber.pinkie.business.nuLiga.control.NuLigaDataProvider;
import de.altenerding.biber.pinkie.business.nuLiga.entity.StandingEntry;
import de.altenerding.biber.pinkie.business.nuLiga.entity.TeamScheduleEntry;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class NuLigaDataService {

	private NuLigaDataProvider nuLigaDataProvider;
	private NuLigaDataProcessor nuLigaDataProcessor;
	private Logger logger;

	public List<StandingEntry> getTeamStandings(long teamId) {
		return nuLigaDataProvider.getTeamStandings(teamId);
	}

	public List<TeamScheduleEntry> getTeamSchedule(long teamId) {
		return nuLigaDataProvider.getTeamSchedule(teamId);
	}

	public List<TeamScheduleEntry> getNextUpcomingMatches(int maxResults) {
		return nuLigaDataProvider.getNextUpcomingMatches(maxResults);
	}
	public List<TeamScheduleEntry> getNextUpcomingMatchDay() {
		return nuLigaDataProvider.getNextUpcomingMatchDay();
	}

	public List<TeamScheduleEntry> getAllUpcomingMatches() {
		return nuLigaDataProvider.getAllUpcomingMatches();
	}

	public List<TeamScheduleEntry> getRecentResults(int maxResult) {
		logger.info("Loading recent nu liga results");
		return nuLigaDataProvider.getRecentResults(maxResult);
	}

	@Inject
	public void setNuLigaDataProvider(NuLigaDataProvider nuLigaDataProvider) {
		this.nuLigaDataProvider = nuLigaDataProvider;
	}

	@Inject
	public void setNuLigaDataProcessor(NuLigaDataProcessor nuLigaDataProcessor) {
		this.nuLigaDataProcessor = nuLigaDataProcessor;
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
