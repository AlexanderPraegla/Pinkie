package de.altenerding.biber.pinkie.business.file.entity;


import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@DiscriminatorValue(value = "FILE")
@Access(AccessType.FIELD)
@Table(name = "file_mapping")
public class FileMapping extends Mapping {

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private File file;

	public File getFile() {
		return file;
	}

	public Video getVideo() {
		if (file instanceof Video) {
			return (Video) file;
		}
		return null;
	}

	public Image getImage() {
		if (file instanceof Image) {
			return (Image) file;
		}

		return null;
	}

	public Document getDocument() {
		if (file instanceof Document) {
			return (Document) file;
		}

		return null;
	}

	public void setFile(File File) {
		this.file = File;
	}
}

