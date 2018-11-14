package de.altenerding.biber.pinkie.business.season.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@NamedQueries({
		@NamedQuery(name = "Season.findAll", query = "SELECT s from Season s order by s.createdOn desc")
})
public class Season {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(columnDefinition = "varchar", unique = true)
	private String name;
	private String seasonNickName;
	@Column(name = "created_on")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date createdOn;

	@PrePersist
	protected void onPersist() {
		if (createdOn == null) {
			createdOn = new Date();
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getSeasonNickName() {
		return seasonNickName;
	}

	public void setSeasonNickName(String seasonNickName) {
		this.seasonNickName = seasonNickName;
	}
}
