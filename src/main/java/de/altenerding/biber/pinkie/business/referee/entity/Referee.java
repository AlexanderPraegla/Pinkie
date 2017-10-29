package de.altenerding.biber.pinkie.business.referee.entity;

import de.altenerding.biber.pinkie.business.file.entity.FileDirectory;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
		@NamedQuery(name = "Referee.getCurrentDeans", query = "SELECT r from Referee r WHERE r.archivedOn IS NULL ORDER BY r.orderId ASC")
})
public class Referee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@OneToOne(fetch = FetchType.LAZY)
	private Member member;
	@Column
	private int orderId;
	@Column(columnDefinition = "varchar")
	private String refereeImage;
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
		if (StringUtils.isNotBlank(refereeImage)) {
			return "/file/" + FileDirectory.PROFILE_IMAGE.getName() + "/" + refereeImage;
		} else if (StringUtils.isNotBlank(member.getProfileImage())) {
			return member.getFullProfileImagePath();
		}
		return null;
	}

	public boolean hasProfileImage() {
		return StringUtils.isNotBlank(refereeImage) || StringUtils.isNotBlank(member.getProfileImage());
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

	public String getRefereeImage() {
		return refereeImage;
	}

	public void setRefereeImage(String refereeImage) {
		this.refereeImage = refereeImage;
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
}
