package de.altenerding.biber.pinkie.business.nuLiga.control;

import de.altenerding.biber.pinkie.business.nuLiga.entity.ClubMeeting;
import de.altenerding.biber.pinkie.business.nuLiga.entity.GroupTableTeam;
import de.altenerding.biber.pinkie.business.nuLiga.mapper.GroupTableTeamDTOMapper;
import de.altenerding.biber.pinkie.business.nuLiga.mapper.MeetingsAbbrDTOMapper;
import de.altenerding.biber.pinkie.business.team.control.TeamProvider;
import de.altenerding.biber.pinkie.business.team.entity.Team;
import nu.liga.open.rs.v2014.dto.championships.GroupTableDTO;
import nu.liga.open.rs.v2014.dto.championships.MeetingAbbrDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class NuLigaDataProcessor {

    private Logger logger;
    private TeamProvider teamProvider;
    @PersistenceContext
    private EntityManager em;
    @Inject
    private NuLigaApiRequester nuLigaApiRequester;

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    void loadNuLigaTeamData() throws Exception {
        emptyTeamData();
        List<Team> teams = teamProvider.getCurrentTeams();

        for (Team team : teams) {

            if (StringUtils.isBlank(team.getNuLigaTeamId())) {
                logger.warn("No nuLiga team id provided for {} with id={}", team.getName(), team.getId());
            } else {
                logger.info("Loading nuLiga table for {} with id={}", team.getName(), team.getId());
                GroupTableDTO groupTableDTO = nuLigaApiRequester.getTeamTable(team.getNuLigaTeamId());
                List<GroupTableTeam> from = GroupTableTeamDTOMapper.from(groupTableDTO);
                logger.info("Found {} entries for ranking table of team with id={}", from.size(), team.getId());
                for (GroupTableTeam groupTableTeam : from) {
                    em.persist(groupTableTeam);
                    em.flush();
                }
            }

        }
        logger.info("Persisted successfully ranking entries");

        List<MeetingAbbrDTO> clubMeetingsOfCurrentSeason = nuLigaApiRequester.getClubMeetingsOfCurrentSeason();
        List<ClubMeeting> from = MeetingsAbbrDTOMapper.from(clubMeetingsOfCurrentSeason);
        for (ClubMeeting clubMeeting : from) {
            em.persist(clubMeeting);
            em.flush();
        }
        logger.info("Persisted successfully {} club meetings", from.size());
    }

    private void emptyTeamData() {
        logger.info("Deleting old nuliga data");
        int deletedMeetings = em.createNativeQuery("DELETE FROM club_meeting").executeUpdate();
        logger.info("Deleted {} from table club_meeting", deletedMeetings);
        int deletedGroupTableEntry = em.createNativeQuery("DELETE FROM group_table_team").executeUpdate();
        logger.info("Deleted {} from table group_table_team", deletedGroupTableEntry);
        logger.info("Sucessfully deleted nuliga data");
    }

    public Logger getLogger() {
        return logger;
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
