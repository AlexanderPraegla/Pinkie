package de.altenerding.biber.pinkie.business.team.entity;

import de.altenerding.biber.pinkie.business.file.entity.Image;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.season.entity.Season;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
		@NamedQuery(name = "Team.getCurrentTeams", query = "SELECT t FROM Team t " +
				"WHERE t.archivedOn IS NULL" +
				" AND t.season.id = :seasonId " +
				"ORDER BY t.orderId ASC"),
		@NamedQuery(name = "Team.findById", query = "SELECT t FROM Team t where t.id = :id"),
		@NamedQuery(name = "Team.archiveTeam", query = "UPDATE Team t SET t.archivedOn = current_timestamp WHERE t.id = :id"),
		@NamedQuery(name = "Team.allTrainer", query = "SELECT DISTINCT t.trainers FROM Team t where t.archivedOn IS NULL")
})
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(columnDefinition = "VARCHAR")
	private String name;
	@Column(columnDefinition = "VARCHAR")
	private String league;
	@JoinColumn
	@OneToOne(fetch = FetchType.LAZY)
	private Season season;
	@Column
	private long orderId = -1;
	@Column(name = "created_on")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date createdOn;
	@OneToOne(fetch = FetchType.LAZY)
	private Image image;
	@Column(columnDefinition = "VARCHAR")
	private String additionalInfo;
	@OneToMany(fetch = FetchType.LAZY)
	private List<Member> teamMembers;
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "team_trainer",
			joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id"))
	private List<Member> trainers;
	@Column(name = "url_standing", columnDefinition = "varchar")
	private String urlStanding;
	@Transient
	private String urlTeamSchedule; //Is set during parsing of nuLiga data. No reason to save this url
	@Column(name = "archived_on")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date archivedOn;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		this.league = league;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public List<Member> getTeamMembers() {
		return teamMembers;
	}

	public void setTeamMembers(List<Member> teamMembers) {
		this.teamMembers = teamMembers;
	}

	public List<Member> getTrainers() {
		return trainers;
	}

	public void setTrainers(List<Member> trainers) {
		this.trainers = trainers;
	}

	public String getUrlStanding() {
		return urlStanding;
	}

	public void setUrlStanding(String urlStanding) {
		this.urlStanding = urlStanding;
	}

	public Date getArchivedOn() {
		return archivedOn;
	}

	public void setArchivedOn(Date archivedOn) {
		this.archivedOn = archivedOn;
	}

	public String getUrlTeamSchedule() {
		return urlTeamSchedule;
	}

	public void setUrlTeamSchedule(String urlTeamSchedule) {
		this.urlTeamSchedule = urlTeamSchedule;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Team team = (Team) o;

		return id == team.id;
	}

	@Override
	public int hashCode() {
		return (int) (id ^ (id >>> 32));
	}
}
