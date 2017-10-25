package de.altenerding.biber.pinkie.business.report.entity;

import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.team.entity.Team;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
		@NamedQuery(name = "Report.findAll", query = "SELECT g from Report g order by g.createdOn desc"),
		@NamedQuery(name = "Report.findById", query = "SELECT g from Report g where g.id = :id")
})
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(columnDefinition = "varchar")
	private String title;
	@Column(columnDefinition = "varchar")
	private String summary;
	@Column(columnDefinition = "varchar")
	private String text;
	@Column(columnDefinition = "varchar")
	private ReportType type;
	@JoinColumn
	@OneToOne(fetch = FetchType.LAZY)
	private Member author;
	@JoinColumn
	@OneToOne(fetch = FetchType.LAZY)
	private Season season;
	@JoinColumn
	@OneToOne(fetch = FetchType.LAZY)
	private Team team;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String subTitle) {
		this.summary = subTitle;
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

	public ReportType getType() {
		return type;
	}

	public void setType(ReportType type) {
		this.type = type;
	}
}
