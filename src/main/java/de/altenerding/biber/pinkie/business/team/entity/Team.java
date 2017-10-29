package de.altenerding.biber.pinkie.business.team.entity;

import de.altenerding.biber.pinkie.business.file.entity.FileDirectory;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.season.entity.Season;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
		@NamedQuery(name = "Team.getCurrentTeams", query = "SELECT t FROM Team t " +
				"WHERE t.archivedOn IS NULL" +
				" AND t.season.id = :seasonId " +
				"ORDER BY t.orderId ASC"),
		@NamedQuery(name = "Team.findById", query = "SELECT t FROM Team t where t.id = :id"),
		@NamedQuery(name = "Team.archiveTeam", query = "UPDATE Team t SET t.archivedOn = current_timestamp WHERE t.id = :id")
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
	@Column(columnDefinition = "VARCHAR")
	private String imageName;
	@Column(columnDefinition = "VARCHAR")
	private String imageDescription;
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

	public String getImageName() {
		return imageName;
	}

	public String getFullImagePath() {
		if (StringUtils.isNotBlank(imageName)) {
			return "/file/" + FileDirectory.TEAM_IMAGE.getName() + "/" + imageName;
		}
		return null;
	}

	public boolean hasTeamImage() {
		return StringUtils.isNotBlank(imageName);
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageDescription() {
		return imageDescription;
	}

	public void setImageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
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
}
