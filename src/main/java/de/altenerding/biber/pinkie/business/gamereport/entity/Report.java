package de.altenerding.biber.pinkie.business.gamereport.entity;

import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.team.entity.Team;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
		@NamedQuery(name = "GameReport.findAll", query = "SELECT g from Report g"),
		@NamedQuery(name = "GameReport.findById", query = "SELECT g from Report g where g.id = :id")
})
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(columnDefinition = "varchar")
	private String title;
	@Column(columnDefinition = "varchar")
	private String subTitle;
	@Column(columnDefinition = "varchar")
	private String text;
	@Column(columnDefinition = "varchar")
	private String type; //TODO Hier würde ich gerne ein Enum verwenden
	@JoinColumn
	@OneToOne(fetch = FetchType.LAZY)
	private Member author;
	@JoinColumn
	@OneToOne(fetch = FetchType.EAGER)
	private Season season;
	@JoinColumn
	@OneToOne(fetch = FetchType.LAZY)
	private Team team;
	@Column(name = "created_on")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date createdOn;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Member getAuthor() {
		return author;
	}

	public void setAuthor(Member author) {
		this.author = author;
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
