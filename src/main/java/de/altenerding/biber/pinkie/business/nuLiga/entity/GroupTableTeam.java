package de.altenerding.biber.pinkie.business.nuLiga.entity;

import nu.liga.open.rs.v2014.dto.championships.GroupTableTeamState;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "group_table_team")
@NamedQueries({
        @NamedQuery(name = "GroupTableTeam.findAllByGroupId", query = "SELECT gtt FROM GroupTableTeam gtt " +
                "where gtt.groupId = :groupId order by gtt.tableRank asc"),
        @NamedQuery(name = "GroupTableTeam.deleteAll", query = "DELETE from GroupTableTeam")
})
public class GroupTableTeam {

    @Id
    private String teamId;
    private String groupId;
    private int tableRank;
    private String teamName;
    private int meetings;
    private int ownPoints;
    private int otherPoints;
    private int ownMatches;
    private int otherMatches;
    private int winMeetings;
    private int lossMeetings;
    private int tieMeetings;
    private String clubNr;
    @Enumerated(value = EnumType.STRING)
    private GroupTableTeamState teamState;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @PrePersist
    protected void onPersist() {
        createdOn = new Date();
    }

    public int getTableRank() {
        return tableRank;
    }

    public void setTableRank(int tableRank) {
        this.tableRank = tableRank;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getMeetings() {
        return meetings;
    }

    public void setMeetings(int meetings) {
        this.meetings = meetings;
    }

    public int getOwnPoints() {
        return ownPoints;
    }

    public void setOwnPoints(int ownPoints) {
        this.ownPoints = ownPoints;
    }

    public int getOtherPoints() {
        return otherPoints;
    }

    public void setOtherPoints(int otherPoints) {
        this.otherPoints = otherPoints;
    }

    public int getOwnMatches() {
        return ownMatches;
    }

    public void setOwnMatches(int ownMatches) {
        this.ownMatches = ownMatches;
    }

    public int getOtherMatches() {
        return otherMatches;
    }

    public void setOtherMatches(int otherMatches) {
        this.otherMatches = otherMatches;
    }

    public int getWinMeetings() {
        return winMeetings;
    }

    public void setWinMeetings(int winMeetings) {
        this.winMeetings = winMeetings;
    }

    public int getLossMeetings() {
        return lossMeetings;
    }

    public void setLossMeetings(int lossMeetings) {
        this.lossMeetings = lossMeetings;
    }

    public int getTieMeetings() {
        return tieMeetings;
    }

    public void setTieMeetings(int tieMeetings) {
        this.tieMeetings = tieMeetings;
    }

    public String getClubNr() {
        return clubNr;
    }

    public void setClubNr(String clubNr) {
        this.clubNr = clubNr;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public GroupTableTeamState getTeamState() {
        return teamState;
    }

    public void setTeamState(GroupTableTeamState teamState) {
        this.teamState = teamState;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
