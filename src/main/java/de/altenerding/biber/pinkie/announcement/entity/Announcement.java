package de.altenerding.biber.pinkie.announcement.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
		@NamedQuery(name = "Announcement.findAll", query = "SELECT a FROM Announcement a ORDER BY a.id DESC"),
		@NamedQuery(name = "Announcement.findById", query = "SELECT a FROM Announcement a where a.id = :id")
})
public class Announcement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(columnDefinition = "VARCHAR")
	private String text;
	@Column
	@Temporal(value = TemporalType.DATE)
	private Date createdOn;

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
}
