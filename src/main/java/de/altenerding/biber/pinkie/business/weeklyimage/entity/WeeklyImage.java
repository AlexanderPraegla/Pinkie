package de.altenerding.biber.pinkie.business.weeklyimage.entity;

import de.altenerding.biber.pinkie.business.file.entity.Image;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@NamedQueries({
		@NamedQuery(name = "WeeklyImage.findAllNotArchived", query = "SELECT w FROM WeeklyImage w " +
				"where w.archivedOn is null ORDER BY w.createdOn desc"),
		@NamedQuery(name = "WeeklyImage.archiveImage", query = "UPDATE WeeklyImage w SET w.archivedOn = current_timestamp " +
				"WHERE w.id = :id")
})
public class WeeklyImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "archived_on")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date archivedOn;
	@Column(name = "created_on")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date createdOn;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Image image;

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

	public Date getArchivedOn() {
		return archivedOn;
	}

	public void setArchivedOn(Date archivedOn) {
		this.archivedOn = archivedOn;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
