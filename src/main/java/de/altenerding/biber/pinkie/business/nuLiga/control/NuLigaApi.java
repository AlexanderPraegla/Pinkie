package de.altenerding.biber.pinkie.business.nuLiga.control;

import nu.liga.open.rs.v2014.dto.ClubDTO;
import nu.liga.open.rs.v2014.dto.championships.MeetingsDTO;
import nu.liga.open.rs.v2014.dto.championships.TeamGroupTablesDTO;
import nu.liga.open.rs.v2014.dto.championships.TeamsDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NuLigaApi {

    @GET("2014/federations/BHV/clubs/10640")
    Call<ClubDTO> getClubOverview();

    @GET("2014/federations/BHV/seasons/{seasonNickname}/clubs/10640/teams")
    Call<TeamsDTO> getClubTeams(@Path("seasonNickname") String seasonNickname);

    @GET("2014/federations/BHV/seasons/{seasonNickname}/clubs/10640/meetings")
    Call<MeetingsDTO> getClubMeetings(@Path("seasonNickname") String seasonNickname,
                                      @Query("firstResult") int firstResult,
                                      @Query("maxResults") int maxResults,
                                      @Query("fromDate") String fromDate,
                                      @Query("toDate") String toDate);

    @GET("2014/federations/BHV/clubs/10640/teams/{teamId}/table")
    Call<TeamGroupTablesDTO> getTeamGroupTables(@Path("teamId") String nuLigaTeamId);
}
