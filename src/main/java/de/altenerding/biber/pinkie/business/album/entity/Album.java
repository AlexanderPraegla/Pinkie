package de.altenerding.biber.pinkie.business.album.entity;

import de.altenerding.biber.pinkie.business.file.entity.Image;
import de.altenerding.biber.pinkie.business.global.entity.BaseLongIdEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
		@NamedQuery(name = "Album.all", query = "SELECT a FROM Album a WHERE a.archivedOn is null ORDER BY a.createdOn DESC")
})
public class Album extends BaseLongIdEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(columnDefinition = "varchar")
	private String description;
	@Column(columnDefinition = "varchar")
	private String folder;
	@OneToMany(fetch = FetchType.LAZY)
	private List<Image> images;
	@OneToOne(fetch = FetchType.LAZY)
	private Image coverImage;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
	private Date createdOn;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "archived_on")
	private Date archivedOn;


	@PrePersist
	protected void onPersist() {
		if (createdOn == null) {
			createdOn = new Date();
		}

		//If no cover image is provided the first one of the list will be used
		if (coverImage == null && images != null) {
			coverImage = images.get(0);
		}
	}

	public Album() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Image getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(Image coverImage) {
		this.coverImage = coverImage;
	}

	public Date getArchivedOn() {
		return archivedOn;
	}

	public void setArchivedOn(Date archivedOn) {
		this.archivedOn = archivedOn;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}
}

