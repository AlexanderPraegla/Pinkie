package de.altenerding.biber.pinkie.business.nuLiga;

import de.altenerding.biber.pinkie.business.team.entity.Team;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
		@NamedQuery(name = "StandingEntry.findAllByTeamId", query = "SELECT e FROM StandingEntry e " +
				"where e.team.id = :teamId order by e.stand asc")
})
@Table(name = "standing")
public class StandingEntry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@OneToOne
	private Team team;
	@Column
	private int stand;
	@Column(columnDefinition = "varchar")
	private String teamName;
	@Column
	private int numberOfMatches;
	@Column
	private int numberOfWinnings;
	@Column
	private int numberOfTies;
	@Column
	private int numberOfLoss;
	@Column(columnDefinition = "varchar")
	private String goals;
	@Column(columnDefinition = "varchar")
	private String goalDifference;
	@Column(columnDefinition = "varchar")
	private String points;
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

	public int getStand() {
		return stand;
	}

	public void setStand(int stand) {
		this.stand = stand;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public int getNumberOfMatches() {
		return numberOfMatches;
	}

	public void setNumberOfMatches(int numberOfMatches) {
		this.numberOfMatches = numberOfMatches;
	}

	public int getNumberOfWinnings() {
		return numberOfWinnings;
	}

	public void setNumberOfWinnings(int numberOfWinnings) {
		this.numberOfWinnings = numberOfWinnings;
	}

	public int getNumberOfTies() {
		return numberOfTies;
	}

	public void setNumberOfTies(int numberOfTies) {
		this.numberOfTies = numberOfTies;
	}

	public int getNumberOfLoss() {
		return numberOfLoss;
	}

	public void setNumberOfLoss(int numberOfLoss) {
		this.numberOfLoss = numberOfLoss;
	}

	public String getGoals() {
		return goals;
	}

	public void setGoals(String goals) {
		this.goals = goals;
	}

	public String getGoalDifference() {
		return goalDifference;
	}

	public void setGoalDifference(String goalDifference) {
		this.goalDifference = goalDifference;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public boolean isInactive() {
		return inactive;
	}

	public void setInactive(boolean active) {
		this.inactive = active;
	}

	public String getInactiveReason() {
		return inactiveReason;
	}

	public void setInactiveReason(String inactiveReason) {
		this.inactiveReason = inactiveReason;
	}
}
