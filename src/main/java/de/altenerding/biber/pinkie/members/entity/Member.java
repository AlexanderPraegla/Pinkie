package de.altenerding.biber.pinkie.members.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
		@NamedQuery(name = "member.findAll", query = "SELECT u FROM Member u"),
		@NamedQuery(name = "member.findById", query = "SELECT u FROM Member u where u.id = :id"),
		@NamedQuery(name = "member.findByEmail", query = "SELECT u FROM Member u where u.email = :email")
})
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private String nickName;
	@Column(unique = true)
	private String email;
	@Column
	private String password;
	@Column
	private String street;
	@Column
	private String mobileNumber;
	@Column
	private String phoneNumber;
	@Column
	private String zipcode;
	@Column
	private String city;
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean isAdmin;
	@Column
	private String homepage;
	@Column
	@Temporal(value = TemporalType.DATE)
	private Date createdOn;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean admin) {
		isAdmin = admin;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date creationDate) {
		this.createdOn = creationDate;
	}

}
