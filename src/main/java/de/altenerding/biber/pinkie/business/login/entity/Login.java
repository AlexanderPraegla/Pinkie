package de.altenerding.biber.pinkie.business.login.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
		@NamedQuery(name = "Login.getByAlias", query = "SELECT l FROM Login l where l.alias = :alias")
})
public class Login {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(columnDefinition = "varchar unique")
	private String alias;
	@Column(columnDefinition = "varchar")
	private String password;
	@Column(name = "is_onetime_password")
	private boolean isOnetimePassword;
	@Column(columnDefinition = "varchar")
	private String salt;
	@Column(columnDefinition = "INTEGER default 0")
	private int loginCount;
	@Column(columnDefinition = "varchar")
	private String inactiveReason;
	@Column(name = "created_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@PrePersist
	public void onPersist() {
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

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}

	public String getInactiveReason() {
		return inactiveReason;
	}

	public void setInactiveReason(String inactiveReason) {
		this.inactiveReason = inactiveReason;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public boolean isOnetimePassword() {
		return isOnetimePassword;
	}

	public void setOnetimePassword(boolean onetimePassword) {
		isOnetimePassword = onetimePassword;
	}
}
