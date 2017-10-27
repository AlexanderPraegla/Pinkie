package de.altenerding.biber.pinkie.business.weeklyimage.entity;

import de.altenerding.biber.pinkie.business.file.entity.FileDirectory;

import javax.persistence.*;
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
	@Column(columnDefinition = "VARCHAR")
	private String fileName;
	@Column(columnDefinition = "VARCHAR")
	private String description;
	@Column(name = "archived_on")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date archivedOn;
	@Column(name = "created_on")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date createdOn;

	@PrePersist
	protected void onPersist() {
		if (createdOn == null) {
			createdOn = new Date();
		}
	}

	public String getWeeklyImageFullPath() {
		return "/file/" + FileDirectory.WEEKLY_IMAGE.getName()+ "/" + fileName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
}
