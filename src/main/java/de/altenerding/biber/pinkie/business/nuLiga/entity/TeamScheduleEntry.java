package de.altenerding.biber.pinkie.business.nuLiga.entity;

import de.altenerding.biber.pinkie.business.team.entity.Team;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@NamedQueries({
		@NamedQuery(name = "TeamScheduleEntry.getAllByTeamId", query = "SELECT e FROM TeamScheduleEntry e " +
				"where e.team.id = :teamId order by e.id asc"),
		@NamedQuery(name = "TeamScheduleEntry.deleteAll", query = "DELETE from TeamScheduleEntry"),
		@NamedQuery(name = "TeamScheduleEntry.upcomingGames", query = "SELECT e FROM TeamScheduleEntry e " +
				"WHERE not e.inactive " +
				"AND e.result = ''" +
				"ORDER BY e.matchDate ASC"),
		@NamedQuery(name = "TeamScheduleEntry.nextUpcomingGames", query = "SELECT e FROM TeamScheduleEntry e " +
				"WHERE not e.inactive " +
				"AND e.result = ''" +
				"AND e.matchDate BETWEEN :startDate AND :endDate " +
				"ORDER BY e.matchDate ASC"),
		@NamedQuery(name = "TeamScheduleEntry.recentResults", query = "SELECT e FROM TeamScheduleEntry e " +
				"WHERE e.result <> '' " +
				"AND e.matchDate BETWEEN :startDate AND :endDate " +
				"ORDER BY e.matchDate desc")
})
@Table(name = "schedule_team")
public class TeamScheduleEntry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@OneToOne(fetch = FetchType.LAZY)
	private Team team;
	@Column
	private String day;
	@Column(name = "match_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date matchDate;
	@Column
	private long matchId;
	@Column(name="home_team", columnDefinition = "varchar")
	private String homeTeam;
	@Column(name = "guest_team", columnDefinition = "varchar")
	private String guestTeam;
	@Column
	private String result;
	@Column(columnDefinition = "boolean default false")
	private boolean inactive;
	@Column(name="inactive_reason", columnDefinition = "varchar")
	private String inactiveReason;
	@Column(name = "created_on", nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date createdOn;

	@PrePersist
	protected void onPersist() {
		if (createdOn == null) {
			createdOn = new Date();
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Date getMatchDate() {
		return matchDate;
	}


	public String getFormattedMatchDate() {
		if (matchDate != null) {
			return new SimpleDateFormat("dd.MM.yyyy").format(matchDate);
		}

		return inactiveReason;
	}

	public void setMatchDate(Date date) {
		this.matchDate = date;
	}

	public String getFormattedMatchTime() {
		if (matchDate != null) {
			return new SimpleDateFormat("HH:mm").format(matchDate);
		}

		return "";
	}

	public long getMatchId() {
		return matchId;
	}

	public void setMatchId(long matchId) {
		this.matchId = matchId;
	}

	public String getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	public String getGuestTeam() {
		return guestTeam;
	}

	public void setGuestTeam(String guestTeam) {
		this.guestTeam = guestTeam;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public boolean isInactive() {
		return inactive;
	}

	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}

	public String getInactiveReason() {
		return inactiveReason;
	}

	public void setInactiveReason(String inactiveReason) {
		this.inactiveReason = inactiveReason;
	}
}
