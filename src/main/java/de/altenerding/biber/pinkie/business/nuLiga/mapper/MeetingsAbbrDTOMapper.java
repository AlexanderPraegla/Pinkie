package de.altenerding.biber.pinkie.business.nuLiga.mapper;

import de.altenerding.biber.pinkie.business.nuLiga.entity.ClubMeeting;
import nu.liga.open.rs.v2014.dto.championships.MeetingAbbrDTO;

import java.util.ArrayList;
import java.util.List;

public class MeetingsAbbrDTOMapper {

    public static ClubMeeting from(MeetingAbbrDTO meetingAbbrDTO) {
        ClubMeeting clubMeeting = new ClubMeeting();
        clubMeeting.setMeetingId(meetingAbbrDTO.getMeetingId());
        clubMeeting.setMeetingNumber(meetingAbbrDTO.getMeetingNumber());
        clubMeeting.setPdfUrl(meetingAbbrDTO.getPdfUrl());
        clubMeeting.setScheduled(meetingAbbrDTO.getScheduled());
        clubMeeting.setCompleted(meetingAbbrDTO.getIsCompleted());
        clubMeeting.setLeagueNickname(meetingAbbrDTO.getLeagueNickname());
        clubMeeting.setGroupName(meetingAbbrDTO.getGroupName());
        clubMeeting.setGroupId(meetingAbbrDTO.getGroupId());
        clubMeeting.setTeamHome(meetingAbbrDTO.getTeamHome());
        clubMeeting.setTeamGuest(meetingAbbrDTO.getTeamGuest());
        clubMeeting.setTeamHomeClubNr(meetingAbbrDTO.getTeamHomeClubNr());
        clubMeeting.setTeamGuestClubNr(meetingAbbrDTO.getTeamGuestClubNr());
        clubMeeting.setMatchesHome(meetingAbbrDTO.getMatchesHome());
        clubMeeting.setMatchesGuest(meetingAbbrDTO.getMatchesGuest());
        clubMeeting.setTeamHomeId(meetingAbbrDTO.getTeamHomeId());
        clubMeeting.setTeamGuestId(meetingAbbrDTO.getTeamGuestId());
        clubMeeting.setCourtHallId(meetingAbbrDTO.getCourtHallId());
        clubMeeting.setCourtHallNumbers(meetingAbbrDTO.getCourtHallNumbers());

        return clubMeeting;
    }

    public static List<ClubMeeting> from(List<MeetingAbbrDTO> meetingAbbrDTOS) {
        List<ClubMeeting> clubMeetings = new ArrayList<>();
        for (MeetingAbbrDTO meetingAbbrDTO : meetingAbbrDTOS) {
            clubMeetings.add(from(meetingAbbrDTO));
        }

        return clubMeetings;
    }
}
