package de.altenerding.biber.pinkie.business.report.boundary;

import de.altenerding.biber.pinkie.business.report.control.SeasonProvider;
import de.altenerding.biber.pinkie.business.report.entity.Season;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class SeasonService {

	private SeasonProvider seasonProvider;

	public List<Season> getSeasons() {
		return seasonProvider.getSeasons();

	}

	@Inject
	public void setSeasonProvider(SeasonProvider seasonProvider) {
		this.seasonProvider = seasonProvider;
	}
}
