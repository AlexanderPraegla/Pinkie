package de.altenerding.biber.pinkie.business.members.entity;

import de.altenerding.biber.pinkie.business.file.entity.FileDirectory;

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
	@Column(name = "created_on")
	@Temporal(value = TemporalType.DATE)
	private Date createdOn;
	@Column(name = "profile_image", columnDefinition = "varchar")
	private String profileImage;
	@Column(columnDefinition = "varchar")
	private String previousClubs;
	@Column(columnDefinition = "varchar")
	private String position;
	@Column(columnDefinition = "varchar")
	private String teamFunction;
	@Column(columnDefinition = "varchar")
	private String height;
	@Column(columnDefinition = "varchar")
	private String wadlUmfang;
	@Column(columnDefinition = "varchar")
	private String throwingHand;
	@Column(columnDefinition = "varchar")
	private String additionalInformation;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Member member = (Member) o;

		return id == member.id;
	}

	@Override
	public int hashCode() {
		return (int) (id ^ (id >>> 32));
	}

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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date creationDate) {
		this.createdOn = creationDate;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profilePicture) {
		this.profileImage = profilePicture;
	}

	public String getFullProfileImagePath() {
		return "/file/" + FileDirectory.PROFILE_IMAGE.getName() + "/" + profileImage;
	}

	public String getPreviousClubs() {
		return previousClubs;
	}

	public void setPreviousClubs(String previousClubs) {
		this.previousClubs = previousClubs;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTeamFunction() {
		return teamFunction;
	}

	public void setTeamFunction(String teamFunction) {
		this.teamFunction = teamFunction;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWadlUmfang() {
		return wadlUmfang;
	}

	public void setWadlUmfang(String wadlUmfang) {
		this.wadlUmfang = wadlUmfang;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public String getThrowingHand() {
		return throwingHand;
	}

	public void setThrowingHand(String throwingHand) {
		this.throwingHand = throwingHand;
	}
}
