package de.altenerding.biber.pinkie.business.sponsor;

import de.altenerding.biber.pinkie.business.file.entity.Image;

import javax.persistence.Column;
import javax.persistence.Entity;
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
		@NamedQuery(name = "Sponsor.premium", query = "SELECT s FROM Sponsor s WHERE s.premium = TRUE "),
		@NamedQuery(name = "Sponsor.notPremium", query = "SELECT s FROM Sponsor s WHERE s.premium = FALSE ")
})
public class Sponsor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(columnDefinition = "varchar")
	private String url;
	@Column(columnDefinition = "varchar")
	private String name;
	private boolean premium;
	@Column(columnDefinition = "varchar")
	private String text;
	@OneToOne
	private Image image;
	private int orderId;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
	private Date createdOn;
	@Column(name = "archived_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date archivedOn;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPremium() {
		return premium;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getOrderId() {
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

	public Date getArchivedOn() {
		return archivedOn;
	}

	public void setArchivedOn(Date archivedOn) {
		this.archivedOn = archivedOn;
	}
}
