package de.altenerding.biber.pinkie.business.report.entity;

import de.altenerding.biber.pinkie.business.file.entity.Image;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.season.entity.Season;
import de.altenerding.biber.pinkie.business.team.entity.Team;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@NamedQueries({
		@NamedQuery(name = "Report.findAll", query = "SELECT g from Report g WHERE g.released = TRUE order by g.createdOn desc"),
		@NamedQuery(name = "Report.findById", query = "SELECT g from Report g where g.id = :id"),
		@NamedQuery(name = "Report.unreleasedReports", query = "SELECT g from Report g where g.released = FALSE order by g.createdOn desc"),
		@NamedQuery(name = "Report.findByTeamIdSeasonID", query = "SELECT g from Report g " +
				"where g.team.id = :teamId AND g.season.id = :seasonId AND g.released = TRUE ORDER BY g.createdOn desc")
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
	@Enumerated(EnumType.STRING)
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
	@OneToOne
	private Image image;
	private boolean released = false;
	@OneToOne(fetch = FetchType.LAZY)
	private Member releasedBy;
	@Column(name = "released_on", nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date releasedOn;

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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public boolean isReleased() {
		return released;
	}

	public void setReleased(boolean released) {
		this.released = released;
	}

	public Member getReleasedBy() {
		return releasedBy;
	}

	public void setReleasedBy(Member releasedBy) {
		this.releasedBy = releasedBy;
	}

	public Date getReleasedOn() {
		return releasedOn;
	}

	public void setReleasedOn(Date releasedOn) {
		this.releasedOn = releasedOn;
	}
}
