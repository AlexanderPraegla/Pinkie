package de.altenerding.biber.pinkie.business.file.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue(value = "IMAGE")
@Access(AccessType.FIELD)
@Table(name = "Image")
public class Image extends File {

	@Column(columnDefinition = "varchar")
	private String description;
    @Column(columnDefinition = "varchar")
    private String thumbnail;

	public Image() {
	}

    public Image(FileCategory fileCategory, String fileName, String description, String thumbnail) {
        super(fileCategory, fileName);
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public String getThumbnailUrl() {
        return "/files/" + getDirectory().getDirectoryPath() + thumbnail;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}

