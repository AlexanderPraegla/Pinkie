package de.altenerding.biber.pinkie.business.file.entity;

import de.altenerding.biber.pinkie.business.global.entity.BaseStringIdEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "file_type", discriminatorType = DiscriminatorType.STRING)
@Access(AccessType.FIELD)
@Table(name = "files")
public abstract class File extends BaseStringIdEntity {

	@Id
	private String id = UUID.randomUUID().toString();
	private FileCategory directory;
	@Column(columnDefinition = "varchar")
	private String fileName;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	protected File() {
	}

	public File(FileCategory fileCategory, String fileName) {
		this.directory = fileCategory;
		this.fileName = fileName;
	}

	@PrePersist
	public void prePersist() {
		if (createdOn == null) {
			createdOn = new Date();
		}
	}

	public String getDownloadUrl() {
		return "/files/" + id; //TODO Download servlet umstellen, dass es mit der id arbeitet und die daten aus der db lädt
	}

	public String getFullFilePath() {
		return directory.getDirectoryPath() + fileName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public FileCategory getDirectory() {
		return directory;
	}

	public void setDirectory(FileCategory directory) {
		this.directory = directory;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
}
