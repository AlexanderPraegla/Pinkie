package de.altenerding.biber.pinkie.business.dean.entity;

import de.altenerding.biber.pinkie.business.file.entity.Image;
import de.altenerding.biber.pinkie.business.members.entity.Member;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
		@NamedQuery(name = "Dean.getCurrentDeans", query = "SELECT d from Dean d WHERE d.archivedOn IS NULL ORDER BY d.orderId ASC")
})
public class Dean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@OneToOne(fetch = FetchType.LAZY)
	private Member member;
	@Column(columnDefinition = "varchar")
	private String description;
	@Column
	private int orderId;
	@OneToOne
	private Image image;
	@Column(name = "archived_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date archivedOn;
	@Column(name = "created_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@PrePersist
	protected void onPersist() {
		if (createdOn == null) {
			createdOn = new Date();
		}
	}

	public String getFullImagePath() {
		if (image != null) {
			return image.getDownloadUrl();
		} else {
			return member.getImage().getDownloadUrl();
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getArchivedOn() {
		return archivedOn;
	}

	public void setArchivedOn(Date archivedOn) {
		this.archivedOn = archivedOn;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
