package de.altenerding.biber.pinkie.presentation.nuliga;

import de.altenerding.biber.pinkie.business.nuLiga.boundary.NuLigaDataService;
import nu.liga.open.rs.v2014.dto.championships.TeamAbbrDTO;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class NuLigaBean implements Serializable {

    @Inject
    private NuLigaDataService nuLigaDataService;

    private List<TeamAbbrDTO> teamAbbrDTO;

    public List<TeamAbbrDTO> getTeamAbbrDTO() {
        if (teamAbbrDTO == null) {
            teamAbbrDTO = nuLigaDataService.getTeamsOfCurrentSeason();
        }
        return teamAbbrDTO;
    }
}
