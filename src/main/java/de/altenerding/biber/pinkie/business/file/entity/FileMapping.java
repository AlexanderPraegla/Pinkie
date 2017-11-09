package de.altenerding.biber.pinkie.business.file.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
		@NamedQuery(name = "FileMapping.getByPageKey", query = "SELECT f FROM FileMapping f " +
				"WHERE f.page = :page AND f.key = :key AND f.archivedOn IS NULL"),
		@NamedQuery(name = "FileMapping.updateArchivedOn", query = "UPDATE FileMapping f SET f.archivedOn = current_timestamp " +
				"WHERE f.page = :page AND f.key = :key AND f.archivedOn IS NULL")
})
public class FileMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(columnDefinition = "varchar")
	private String page;
	@Column(columnDefinition = "varchar")
	private String key;
	@Column(columnDefinition = "varchar")
	private String filePath;
	@Column(columnDefinition = "varchar")
	private String description;
	@Column(name = "created_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	@Column(name = "archived_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date archivedOn;

	@PrePersist
	public void onPrePersist() {
		if (createdOn == null) {
			createdOn = new Date();
		}
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String value) {
		this.filePath = value;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public void setDocumentFilePath(String fileName) {
		filePath = FileDirectory.DOCUMTENTS.getName() + "/" + fileName;
	}

	public void setImageFilePath(String fileName) {
		filePath = FileDirectory.IMAGES.getName() + "/" + fileName;
	}

	public void setVideoFilePath(String fileName) {
		filePath = FileDirectory.VIDEOS.getName() + "/" + fileName;
	}

	public String getFileDownloadPath() {
		return "/file/" + filePath;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
