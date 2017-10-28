package de.altenerding.biber.pinkie.presentation.report;

import de.altenerding.biber.pinkie.business.season.boundary.SeasonService;
import de.altenerding.biber.pinkie.business.season.entity.Season;
import net.bootsfaces.utils.FacesMessages;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@ManagedBean
@RequestScoped
public class SeasonBean {

	private Logger logger;
	private SeasonService seasonService;
	private List<Season> seasons;
	private Season currentSeason;

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

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Inject
	public void setReportService(SeasonService seasonService) {
		this.seasonService = seasonService;
	}

	public void setCurrentSeason(Season currentSeason) {
		this.currentSeason = currentSeason;
	}
}
