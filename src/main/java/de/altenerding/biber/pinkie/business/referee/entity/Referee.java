package de.altenerding.biber.pinkie.business.referee.entity;

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
		@NamedQuery(name = "Referee.getCurrentReferees", query = "SELECT r from Referee r WHERE r.archivedOn IS NULL ORDER BY r.orderId ASC")
})
public class Referee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@OneToOne(fetch = FetchType.LAZY)
	private Member member;
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

	@SuppressWarnings("Duplicates")
	public Image getImage() {
        if (image != null) {
            return image;
		} else if (member != null) {
			return member.getImage();
		} else {
			return null;
		}
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
