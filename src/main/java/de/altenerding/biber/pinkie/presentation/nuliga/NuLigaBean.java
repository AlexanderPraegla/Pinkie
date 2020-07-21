package de.altenerding.biber.pinkie.presentation.nuliga;

import de.altenerding.biber.pinkie.business.nuLiga.boundary.NuLigaDataService;
import de.altenerding.biber.pinkie.business.nuLiga.control.NuLigaDataTimer;
import de.altenerding.biber.pinkie.business.team.entity.Team;
import net.bootsfaces.utils.FacesMessages;
import nu.liga.open.rs.v2014.dto.championships.TeamAbbrDTO;
import org.apache.logging.log4j.Logger;
import org.omnifaces.util.Faces;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class NuLigaBean implements Serializable {

    @Inject
    private NuLigaDataService nuLigaDataService;
    @Inject
    private NuLigaDataTimer nuLigaDataTimer;
    @Inject
    private Logger logger;

    private List<TeamAbbrDTO> teamAbbrDTO;

    public void triggerNuLigaTimer() {
        nuLigaDataTimer.loadNuLigaData();
    }

    public List<TeamAbbrDTO> getTeamAbbrDTO() {
        if (teamAbbrDTO == null) {
            teamAbbrDTO = nuLigaDataService.getTeamsOfCurrentSeason();
        }
        return teamAbbrDTO;
    }

    public void downloadIcalForTeam(Team team) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("Saison_")
                    .append(team.getSeason().getName().replace("/", "_"))
                    .append("_")
                    .append(team.getName().trim().replace(" ", "_"))
                    .append(".ics");

            String fileName = builder.toString();
            Faces.sendFile(fileName, true, outputStream -> {
                nuLigaDataService.createIcalFileMeetingsForTeam(team, outputStream);
            });
        } catch (IOException e) {
            FacesMessages.error("Fehler beim erstellen der Kalendar Datei");
            logger.error("Error while creating ical file for teamId={}", team.getId());
        }
    }
}
