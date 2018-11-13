package de.altenerding.biber.pinkie.business.nuLiga.mapper;

import de.altenerding.biber.pinkie.business.nuLiga.entity.GroupTableTeam;
import nu.liga.open.rs.v2014.dto.championships.GroupTableDTO;
import nu.liga.open.rs.v2014.dto.championships.GroupTableTeamDTO;

import java.util.ArrayList;
import java.util.List;

public class GroupTableTeamDTOMapper {

    public static GroupTableTeam from(GroupTableTeamDTO dto, String groupId) {
        GroupTableTeam groupTableTeam = new GroupTableTeam();
        groupTableTeam.setTableRank(dto.getTableRank());
        groupTableTeam.setTeamName(dto.getTeam());
        groupTableTeam.setMeetings(dto.getMeetings());
        groupTableTeam.setOwnPoints(dto.getOwnPoints().intValue());
        groupTableTeam.setOtherPoints(dto.getOtherPoints().intValue());
        groupTableTeam.setOwnMatches(dto.getOwnMatches());
        groupTableTeam.setOtherMatches(dto.getOtherMatches());
        groupTableTeam.setWinMeetings(dto.getOwnMeetings());
        groupTableTeam.setLossMeetings(dto.getOtherMeetings());
        groupTableTeam.setTieMeetings(dto.getTieMeetings());
        groupTableTeam.setClubNr(dto.getClubNr());
        groupTableTeam.setTeamId(dto.getTeamId());
        groupTableTeam.setGroupId(groupId);
        groupTableTeam.setTeamState(dto.getTeamState());
        return groupTableTeam;
    }

    public static List<GroupTableTeam> from(GroupTableDTO groupTableDTO) {
        List<GroupTableTeam> groupTableTeams = new ArrayList<>();
        for (GroupTableTeamDTO groupTableTeam : groupTableDTO.getList()) {
            groupTableTeams.add(from(groupTableTeam, groupTableDTO.getGroupId()));
        }

        return groupTableTeams;
    }
}
