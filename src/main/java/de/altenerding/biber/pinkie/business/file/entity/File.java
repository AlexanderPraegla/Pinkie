package de.altenerding.biber.pinkie.business.file.entity;

import de.altenerding.biber.pinkie.business.global.entity.BaseStringIdEntity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
		return "/files/" + id;
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
