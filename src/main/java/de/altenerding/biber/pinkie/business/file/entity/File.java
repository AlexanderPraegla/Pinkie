package de.altenerding.biber.pinkie.business.file.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "file_type", discriminatorType = DiscriminatorType.STRING)
@Access(AccessType.FIELD)
@Table(name = "files")
public abstract class File {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private FileCategory directory;
	@Column(columnDefinition = "varchar")
	private String fileName;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
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

    public String getFileUrl() {
        return "/files/" + getFullFilePath();
	}

	public String getFullFilePath() {
		return directory.getDirectoryPath() + fileName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

    FileCategory getDirectory() {
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		File file = (File) o;
		return id == file.id;
	}

	@Override
	public int hashCode() {

		return Objects.hash(super.hashCode(), id);
	}
}
