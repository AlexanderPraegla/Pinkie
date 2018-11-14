package de.altenerding.biber.pinkie.business.nuLiga.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
@Table(name = "club_meeting")
@NamedQueries({
        @NamedQuery(name = "ClubMeeting.getAllByGroupId", query = "SELECT e FROM ClubMeeting e " +
                "where e.groupId = :groupId order by e.scheduled asc"),
        @NamedQuery(name = "ClubMeeting.deleteAll", query = "DELETE from ClubMeeting"),
        @NamedQuery(name = "ClubMeeting.upcomingGames", query = "SELECT e FROM ClubMeeting e " +
                "WHERE e.completed = false " +
                "ORDER BY e.scheduled ASC"),
        @NamedQuery(name = "ClubMeeting.nextUpcomingGames", query = "SELECT e FROM ClubMeeting e " +
                "WHERE e.completed = false " +
                "AND e.scheduled BETWEEN :startDate AND :endDate " +
                "ORDER BY e.scheduled ASC"),
        @NamedQuery(name = "ClubMeeting.recentResults", query = "SELECT e FROM ClubMeeting e " +
                "WHERE e.completed = true " +
                "AND e.scheduled BETWEEN :startDate AND :endDate " +
                "ORDER BY e.scheduled desc")
})
public class ClubMeeting {

    @Id
    private String meetingId;
    private int meetingNumber;
    @Column(columnDefinition = "varchar")
    private String pdfUrl;
    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduled;
    private boolean completed;
    private String leagueNickname;
    private String groupId;
    private String groupName;
    private String teamHome;
    private String teamGuest;
    private String teamHomeClubNr;
    private String teamGuestClubNr;
    private int matchesHome;
    private int matchesGuest;
    private String teamHomeId;
    private String teamGuestId;
    private String courtHallId;
    private String courtHallNumbers;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @PrePersist
    protected void onPersist() {
        createdOn = new Date();
    }

    public String getFormattedMatchDate() {
        return new SimpleDateFormat("dd.MM.yyyy").format(scheduled);
    }

    public String getFormattedMatchTime() {
        return new SimpleDateFormat("HH:mm").format(scheduled);
    }

    public String getDayOfMeeting() {
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("E", Locale.GERMANY); // the day of the week abbreviated
        return simpleDateformat.format(scheduled);
    }

    public String getDayOfMeetingLong() {
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE", Locale.GERMANY); // the day of the week spelled out completely
        return simpleDateformat.format(scheduled);
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public int getMeetingNumber() {
        return meetingNumber;
    }

    public void setMeetingNumber(int meetingNumber) {
        this.meetingNumber = meetingNumber;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public Date getScheduled() {
        return scheduled;
    }

    public void setScheduled(Date scheduled) {
        this.scheduled = scheduled;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getLeagueNickname() {
        return leagueNickname;
    }

    public void setLeagueNickname(String leagueNickname) {
        this.leagueNickname = leagueNickname;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTeamHome() {
        return teamHome;
    }

    public void setTeamHome(String teamHome) {
        this.teamHome = teamHome;
    }

    public String getTeamGuest() {
        return teamGuest;
    }

    public void setTeamGuest(String teamGuest) {
        this.teamGuest = teamGuest;
    }

    public String getTeamHomeClubNr() {
        return teamHomeClubNr;
    }

    public void setTeamHomeClubNr(String teamHomeClubNr) {
        this.teamHomeClubNr = teamHomeClubNr;
    }

    public String getTeamGuestClubNr() {
        return teamGuestClubNr;
    }

    public void setTeamGuestClubNr(String teamGuestClubNr) {
        this.teamGuestClubNr = teamGuestClubNr;
    }

    public int getMatchesHome() {
        return matchesHome;
    }

    public void setMatchesHome(int matchesHome) {
        this.matchesHome = matchesHome;
    }

    public int getMatchesGuest() {
        return matchesGuest;
    }

    public void setMatchesGuest(int matchesGuest) {
        this.matchesGuest = matchesGuest;
    }

    public String getTeamHomeId() {
        return teamHomeId;
    }

    public void setTeamHomeId(String teamHomeId) {
        this.teamHomeId = teamHomeId;
    }

    public String getTeamGuestId() {
        return teamGuestId;
    }

    public void setTeamGuestId(String teamGuestId) {
        this.teamGuestId = teamGuestId;
    }

    public String getCourtHallId() {
        return courtHallId;
    }

    public void setCourtHallId(String courtHallId) {
        this.courtHallId = courtHallId;
    }

    public String getCourtHallNumbers() {
        return courtHallNumbers;
    }

    public void setCourtHallNumbers(String courtHallNumbers) {
        this.courtHallNumbers = courtHallNumbers;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
