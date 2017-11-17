package de.altenerding.biber.pinkie.business.file.entity;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "IMAGE")
@Access(AccessType.FIELD)
@Table(name = "Image")
public class Image extends File {

	@Column(columnDefinition = "varchar")
	private String description;

	public Image() {
	}

	public Image(FileCategory fileCategory, String fileName, String description) {
		super(fileCategory, fileName);
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

