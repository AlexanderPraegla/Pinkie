package de.altenerding.biber.pinkie.business.season.boundary;

import de.altenerding.biber.pinkie.business.season.control.SeasonProvider;
import de.altenerding.biber.pinkie.business.season.entity.Season;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class SeasonService {

	private SeasonProvider seasonProvider;

	public List<Season> getSeasons() {
		return seasonProvider.getSeasons();

	}

	public Season getCurrentSeason() {
		return seasonProvider.getCurrentSeason();
	}

	@Inject
	public void setSeasonProvider(SeasonProvider seasonProvider) {
		this.seasonProvider = seasonProvider;
	}
}
