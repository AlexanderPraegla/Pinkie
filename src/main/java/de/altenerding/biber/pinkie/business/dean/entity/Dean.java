package de.altenerding.biber.pinkie.business.dean.entity;

import de.altenerding.biber.pinkie.business.file.entity.Image;
import de.altenerding.biber.pinkie.business.members.entity.Member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
	private String function;
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
	@Column(columnDefinition = "varchar")
	private String description;

	@PrePersist
	protected void onPersist() {
		if (createdOn == null) {
			createdOn = new Date();
		}
	}

	public boolean hasProfileImage() {
		return image != null || member.getImage() != null;
	}

	public String getFullImagePath() {
		if (image != null) {
            return image.getFileUrl();
		} else {
            return member.getImage().getFileUrl();
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

	public String getFunction() {
		return function;
	}

	public void setFunction(String description) {
		this.function = description;
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
        if (image != null) {
            return image;
        } else {
            return member.getImage();
        }
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String desciription) {
		this.description = desciription;
	}
}
