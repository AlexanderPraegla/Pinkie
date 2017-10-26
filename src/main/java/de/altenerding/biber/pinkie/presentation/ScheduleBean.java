package de.altenerding.biber.pinkie.presentation;

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

	@Inject
	public void setNuLigaDataService(NuLigaDataService nuLigaDataService) {
		this.nuLigaDataService = nuLigaDataService;
	}

	public List<TeamScheduleEntry> getTeamScheduleEntries() {
		return teamScheduleEntries;
	}
}
