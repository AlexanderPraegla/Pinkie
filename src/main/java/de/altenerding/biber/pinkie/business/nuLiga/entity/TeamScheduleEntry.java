package de.altenerding.biber.pinkie.business.nuLiga.entity;

import de.altenerding.biber.pinkie.business.team.entity.Team;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
		@NamedQuery(name = "TeamScheduleEntry.getAllByTeamId", query = "SELECT e FROM TeamScheduleEntry e " +
				"where e.team.id = :teamId order by e.id asc"),
		@NamedQuery(name = "TeamScheduleEntry.deleteAll", query = "DELETE from TeamScheduleEntry")
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
	private String date;
	@Column
	private String time;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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
