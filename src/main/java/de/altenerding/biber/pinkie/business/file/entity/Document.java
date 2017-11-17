package de.altenerding.biber.pinkie.business.file.entity;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "DOCUMENT")
@Access(AccessType.FIELD)
@Table(name = "Document")
public class Document extends File {

	@Column(columnDefinition = "varchar")
	private String displayedName;

	public Document() {
	}

	public Document(FileCategory fileCategory, String fileName, String displayedName) {
		super(fileCategory, fileName);
		this.displayedName = displayedName;
	}

	public String getDisplayedName() {
		return displayedName;
	}

	public void setDisplayedName(String displayedName) {
		this.displayedName = displayedName;
	}
}
