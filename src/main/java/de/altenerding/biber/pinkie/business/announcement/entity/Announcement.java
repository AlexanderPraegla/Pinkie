package de.altenerding.biber.pinkie.business.announcement.entity;

import de.altenerding.biber.pinkie.business.file.entity.Document;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
		@NamedQuery(name = "Announcement.getLatestAnnouncements", query = "SELECT a FROM Announcement a WHERE a.archivedOn IS NULL ORDER BY a.createdOn DESC"),
		@NamedQuery(name = "Announcement.findById", query = "SELECT a FROM Announcement a where a.id = :id")
})
public class Announcement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(columnDefinition = "VARCHAR")
	private String text;
	@Column(name = "created_on")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date createdOn;
	@OneToOne
	private Document document;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getArchivedOn() {
		return archivedOn;
	}

	public void setArchivedOn(Date archivedOn) {
		this.archivedOn = archivedOn;
	}

	public Document getDocument() {
		if (document == null) {
			return new Document();
		}
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
}
