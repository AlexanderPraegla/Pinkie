package de.altenerding.biber.pinkie.business.file.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@IdClass(FileMappingId.class)
@NamedQueries({
		@NamedQuery(name = "FileMapping.getByPageKey", query = "SELECT f FROM FileMapping f WHERE f.page = :page AND f.key = :key")
})
public class FileMapping {

	@Id
	@Column(columnDefinition = "varchar")
	private String page;
	@Id
	@Column(columnDefinition = "varchar", unique = true)
	private String key;
	@Column(columnDefinition = "varchar")
	private String value;
	@Column(name = "created_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@PrePersist
	public void onPrePersist() {
		if (createdOn == null) {
			createdOn = new Date();
		}
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
}
