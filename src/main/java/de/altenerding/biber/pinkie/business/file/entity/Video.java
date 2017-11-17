package de.altenerding.biber.pinkie.business.file.entity;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "VIDEO")
@Access(AccessType.FIELD)
@Table(name = "Video")
public class Video extends File {

	@Column(columnDefinition = "varchar")
	private String description;

	public Video() {
	}

	public Video(FileCategory fileCategory, String fileName, String description) {
		super(fileCategory, fileName);
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String displayedName) {
		this.description = displayedName;
	}
}
