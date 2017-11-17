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
	@OneToOne
	private File file;
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

	public Image getImage() {
		if (file instanceof Image) {
			return ((Image) file);
		}
		return null;
	}

	public String getImageDescription() {
		if (file instanceof Image) {
			return ((Image) file).getDescription();
		}
		return "";
	}

	public String getVideoDescription() {
		if (file instanceof Video) {
			return ((Video) file).getDescription();
		}
		return "";
	}

	public String getDocumentDisplayedName() {
		if (file instanceof Document) {
			return ((Document) file).getDisplayedName();
		}
		return "";
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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
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

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}
