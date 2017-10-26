package de.altenerding.biber.pinkie.business.nuLiga.control;

import de.altenerding.biber.pinkie.business.nuLiga.entity.StandingEntry;
import de.altenerding.biber.pinkie.business.nuLiga.entity.TeamScheduleEntry;
import de.altenerding.biber.pinkie.business.team.control.TeamProvider;
import de.altenerding.biber.pinkie.business.team.entity.Team;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NuLigaProcessor {

	private Logger logger;
	private TeamProvider teamProvider;
	@PersistenceContext
	private EntityManager em;

	public void loadNuLigaTeamData() throws Exception {
		emptyTeamData();
		List<Team> teams = teamProvider.getTeams();

		for (Team team : teams) {
			Document document = Jsoup.connect(team.getUrlStanding()).get();
			loadTeamStandings(team, document);
			document = Jsoup.connect(team.getUrlTeamSchedule()).get();
			loadTeamSchedule(team, document);
		}
	}

	private void emptyTeamData() {
		//Delete old data
		em.createNamedQuery("TeamScheduleEntry.deleteAll").executeUpdate();
		em.createNamedQuery("StandingEntry.deleteAll").executeUpdate();
		//reset sequence to prevent an overflow
		em.createNativeQuery("ALTER SEQUENCE standing_id_seq RESTART WITH 1").executeUpdate();
		em.createNativeQuery("ALTER SEQUENCE schedule_team_id_seq RESTART WITH 1").executeUpdate();
	}

	private void loadTeamSchedule(Team team, Document document) {
		logger.info("Loading season schedule from nuLiga for team={} with id={}", team.getName(), team.getId());
		List<Element> tables = document.select("table");
		Element standingTable = tables.get(1);
		Elements rows = standingTable.select("tr");

		List<TeamScheduleEntry> seasonScheduleEntries = new ArrayList<>();

		for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
			Element row = rows.get(i);
			Elements cols = row.select("td");

			TeamScheduleEntry entry = new TeamScheduleEntry();
			entry.setTeam(team);

			int columnCounter = 0;
			if (cols.size() == 11) {
				String day = cols.get(columnCounter++).text(); //Column 1
				if (day.replace("\u00A0", "").isEmpty()) {
					entry.setDay(seasonScheduleEntries.get(i - 2).getDay());
				} else {
					entry.setDay(day);
				}

				String date = cols.get(columnCounter++).text(); //Column 2
				if (date.replace("\u00A0", "").isEmpty()) {
					entry.setDate(seasonScheduleEntries.get(i - 2).getDate());
				} else {
					entry.setDate(date);
				}

				String time = cols.get(columnCounter++).text().replace("\u00A0", "").trim(); //Column 3
				entry.setTime(time);
			} else {
				entry.setInactive(true);
				String inactiveReason = cols.get(columnCounter++).text(); //Column 1
				entry.setInactiveReason(inactiveReason);
				columnCounter++; //Skip column 2
			}
			@SuppressWarnings("unused")
			/*
				ignore Hallennr
				column index is different depending on case above
			 */
			String place = cols.get(columnCounter++).text();
			String matchId = cols.get(columnCounter++).text();
			entry.setMatchId(Long.parseLong(matchId));
			String homeTeam = cols.get(columnCounter++).text().replace("\u00A0", "").trim();
			entry.setHomeTeam(homeTeam);
			String guestTeam = cols.get(columnCounter++).text().replace("\u00A0", "").trim();
			entry.setGuestTeam(guestTeam);
			String result = cols.get(columnCounter++).text().replace("\u00A0", "").trim();
			entry.setResult(result);
			@SuppressWarnings({"unused", "UnusedAssignment"})
			String col9 = cols.get(columnCounter++).text(); //ignore emtpy cell

			seasonScheduleEntries.add(entry);

			em.persist(entry);
			em.flush();
		}
	}

	private void loadTeamStandings(Team team, Document document) throws IOException {
		logger.info("Updating standings data for team={} with id={}", team.getName(), team.getId());
		List<Element> tables = document.select("table");
		Element standingTable = tables.get(0);
		Elements rows = standingTable.select("tr");

		for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
			Element row = rows.get(i);
			Elements cols = row.select("td");

			StandingEntry entry = new StandingEntry();
			entry.setTeam(team);
			entry.setStand(Integer.parseInt(cols.get(1).text()));
			String teamName = cols.get(2).text();
			entry.setTeamName(teamName);

			if (teamName.contains("Altenerding")) {
				Element element = cols.get(2);
				String teamScheduleUrl = element.select("a").attr("abs:href");
				team.setUrlTeamSchedule(teamScheduleUrl);
			}

			if (cols.size() == 5) {
				entry.setInactive(true);
				entry.setInactiveReason(cols.get(4).text());
			} else {

				entry.setNumberOfMatches(Integer.parseInt(cols.get(3).text()));
				entry.setNumberOfWinnings(Integer.parseInt(cols.get(4).text()));
				entry.setNumberOfTies(Integer.parseInt(cols.get(5).text()));
				entry.setNumberOfLoss(Integer.parseInt(cols.get(6).text()));
				entry.setGoals(cols.get(7).text());
				entry.setGoalDifference(cols.get(8).text());
				entry.setPoints(cols.get(9).text());
			}
			em.persist(entry);
			em.flush();
		}
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Inject
	public void setTeamProvider(TeamProvider teamProvider) {
		this.teamProvider = teamProvider;
	}
}
