package de.altenerding.biber.pinkie.team.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
		@NamedQuery(name = "Team.findAll", query = "SELECT t FROM Team t ORDER BY t.orderId asc"),
		@NamedQuery(name = "Team.findById", query = "SELECT t FROM Team t where t.id = :id")
})
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(columnDefinition = "VARCHAR")
	private String name;
	@Column(columnDefinition = "VARCHAR")
	private String league;
	@Column(columnDefinition = "BIGINT DEFAULT -1")
	private long orderId;
	@Column(name = "created_on")
	@Temporal(value = TemporalType.DATE)
	private Date createdOn;

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

	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		this.league = league;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
}
