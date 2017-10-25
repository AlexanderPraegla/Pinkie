package de.altenerding.biber.pinkie.business.team.entity;

import de.altenerding.biber.pinkie.business.config.ImageFolder;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.report.entity.Season;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
		@NamedQuery(name = "Team.findAll", query = "SELECT t FROM Team t ORDER BY t.orderId asc"),
		@NamedQuery(name = "Team.findById", query = "SELECT t FROM Team t where t.id = :id")
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
	@Column(columnDefinition = "BIGINT DEFAULT -1")
	private long orderId;
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

	public void setOrderId(int orderId) {
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
		return ImageFolder.TEAM_IMAGE.getName() + imageName;
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
}
