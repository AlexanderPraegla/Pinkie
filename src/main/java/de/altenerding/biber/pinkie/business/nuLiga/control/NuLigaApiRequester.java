package de.altenerding.biber.pinkie.business.nuLiga.control;

import de.altenerding.biber.pinkie.business.season.boundary.SeasonService;
import de.altenerding.biber.pinkie.business.season.entity.Season;
import nu.liga.open.rs.v2014.dto.championships.GroupTableDTO;
import nu.liga.open.rs.v2014.dto.championships.MeetingAbbrDTO;
import nu.liga.open.rs.v2014.dto.championships.MeetingsDTO;
import nu.liga.open.rs.v2014.dto.championships.TeamAbbrDTO;
import nu.liga.open.rs.v2014.dto.championships.TeamGroupTablesDTO;
import nu.liga.open.rs.v2014.dto.championships.TeamsDTO;
import org.apache.logging.log4j.Logger;
import retrofit2.Call;
import retrofit2.Retrofit;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class NuLigaApiRequester {

    private static final int MAX_RESULT = 500;

    @Inject
    private RetrofitRequester retrofitRequester;
    @Inject
    private SeasonService seasonService;
    @Inject
    private Retrofit retrofit;
    @Inject
    private Logger logger;

    public List<TeamAbbrDTO> getTeamsOfCurrentSeason() {
        NuLigaApi nuLigaApi = retrofit.create(NuLigaApi.class);
        Season currentSeason = seasonService.getCurrentSeason();
        Call<TeamsDTO> teamsDTOCall = nuLigaApi.getClubTeams(currentSeason.getSeasonNickName());
        TeamsDTO teamsDTO = retrofitRequester.executeSyncronousCall(teamsDTOCall);

        if (teamsDTO != null) {
            logger.info("Found {} teams for season={}", teamsDTO.getList().size(), currentSeason.getSeasonNickName());
            return teamsDTO.getList();
        } else {
            logger.warn("Found no teams for season={}", currentSeason.getSeasonNickName());
            return new ArrayList<>();
        }
    }

    public GroupTableDTO getTeamTable(String nuLigaTeamId) {
        NuLigaApi nuLigaApi = retrofit.create(NuLigaApi.class);
        Call<TeamGroupTablesDTO> call = nuLigaApi.getTeamGroupTables(nuLigaTeamId);
        TeamGroupTablesDTO teamGroupTablesDTO = retrofitRequester.executeSyncronousCall(call);

        if (teamGroupTablesDTO != null) {
            return teamGroupTablesDTO.getGroupTables().get(0);
        } else {
            return null;
        }
    }

    public List<MeetingAbbrDTO> getClubMeetingsOfCurrentSeason() {
        return getClubMeetingsOfSeason(seasonService.getCurrentSeason(), null, null);
    }

    public List<MeetingAbbrDTO> getClubMeetingsOfSeason(Season season, LocalDate fromDate, LocalDate toDate) {
        NuLigaApi nuLigaApi = retrofit.create(NuLigaApi.class);

        String fromDateString = "";
        if (fromDate != null) {
            fromDateString = fromDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        String toDateString = "";
        if (toDate != null) {
            toDateString = toDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        Call<MeetingsDTO> meetingsDTOCall = nuLigaApi.getClubMeetings(season.getSeasonNickName(), 0, MAX_RESULT, fromDateString, toDateString);
        MeetingsDTO meetingsDTO = retrofitRequester.executeSyncronousCall(meetingsDTOCall);
        List<MeetingAbbrDTO> result;
        if (meetingsDTO.getAvailableResults() <= MAX_RESULT) {
            result = meetingsDTO.getList();
        } else {
            meetingsDTOCall = nuLigaApi.getClubMeetings(season.getSeasonNickName(), 0, meetingsDTO.getAvailableResults(), fromDateString, toDateString);
            result = retrofitRequester.executeSyncronousCall(meetingsDTOCall).getList();
        }

        logger.info("Found {} meetings in nuLiga", result.size());
        return result;
    }
}
