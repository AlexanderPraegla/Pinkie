package de.altenerding.biber.pinkie.presentation.season;

import de.altenerding.biber.pinkie.business.members.entity.Access;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import de.altenerding.biber.pinkie.business.season.boundary.SeasonService;
import de.altenerding.biber.pinkie.business.season.entity.Season;
import de.altenerding.biber.pinkie.business.team.boundary.TeamService;
import de.altenerding.biber.pinkie.business.team.entity.Team;
import net.bootsfaces.utils.FacesMessages;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class SeasonBean {

	private Logger logger;
	private SeasonService seasonService;
	private TeamService teamService;
	private List<Season> seasons;
	private Season currentSeason;
	private String seasonName;
	private String teamIndex = "";
	private List<Team> currentTeams;

	@PostConstruct
	public void init() {
		if (seasons == null) {
			try {
				seasons = seasonService.getSeasons();
			} catch (Exception e) {
				logger.error("Error while loading all seasons", e);
				FacesMessages.error("Es ist ein Fehler beim laden der Saisons aufgetreten: " + e.getMessage());
			}
		}
	}

	public List<Season> getSeasons() {
		return seasons;
	}


	public Season getCurrentSeason() {
		if (currentSeason == null) {
			currentSeason = seasonService.getCurrentSeason();
		}
		return currentSeason;
	}

	@Access(role = Role.ADMIN)
	public String createSeason() {
		if (StringUtils.isBlank(seasonName)) {
			FacesMessages.error("Die Saison darf nicht leer sein");
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);
			return "error";
		}

		Season season = seasonService.createSeason(seasonName);
		List<Team> newTeams = new ArrayList<>();
		for (String index : StringUtils.split(teamIndex, ",")) {
			Team team = currentTeams.get(Integer.parseInt(index));
			team.setId(0);
			team.setSeason(season);
			newTeams.add(team);
		}

		teamService.archiveTeams(currentTeams);
		teamService.createTeams(newTeams);
		FacesMessages.info(String.format("Die Saison %s wurde erfolgreich angelegt", seasonName));
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return "success";
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Inject
	public void setReportService(SeasonService seasonService) {
		this.seasonService = seasonService;
	}

	@Inject
	public void setTeamService(TeamService teamService) {
		this.teamService = teamService;
	}

	public List<Team> getCurrentTeams() {
		if (currentTeams == null) {
			currentTeams = teamService.getCurrentTeams();
		}
		return currentTeams;
	}

	public void setCurrentTeams(List<Team> currentTeams) {
		this.currentTeams = currentTeams;
	}

	public void setCurrentSeason(Season currentSeason) {
		this.currentSeason = currentSeason;
	}

	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}

	public String getSeasonName() {
		return seasonName;
	}

	public String getTeamIndex() {
		return teamIndex;
	}

	public void setTeamIndex(String teamIndex) {
		this.teamIndex = teamIndex;
	}
}
