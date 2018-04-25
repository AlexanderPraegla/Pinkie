package de.altenerding.biber.pinkie.business.members.entity;

import de.altenerding.biber.pinkie.business.file.entity.Image;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
		@NamedQuery(name = "member.findAll", query = "SELECT u FROM Member u ORDER BY u.firstName ASC"),
		@NamedQuery(name = "member.findById", query = "SELECT u FROM Member u where u.id = :id"),
        @NamedQuery(name = "member.findByEmail", query = "SELECT u FROM Member u where u.email = :email"),
        @NamedQuery(name = "member.findByAlias", query = "SELECT u FROM Member u where u.alias = :alias")
})
@Cacheable(false)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String firstName;
	private String lastName;
    @Column(unique = true)
    private String nickName;
    @Column(unique = true)
    private String alias;
	private String email;
	private String privateEmail;
	private String street;
	private String mobileNumber;
	private String phoneNumber;
	private String zipcode;
	private String city;
	@Enumerated(EnumType.STRING)
	private Role role = Role.MEMBER;
	@OneToOne
	private Image image;
	@Column(columnDefinition = "varchar")
	private String previousClubs;
	private String shirtNumber;
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
	@Temporal(TemporalType.DATE)
	private Date birthday;
	@Column(name = "created_on")
	@Temporal(TemporalType.DATE)
	private Date createdOn;

	@PrePersist
	protected void onPersist() {
		if (createdOn == null) {
			createdOn = new Date();
		}

		if (role == null) {
			role = Role.MEMBER;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Member member = (Member) o;

		return id == member.id;
	}

	public boolean hasPrivateEmail() {
        return StringUtils.isNotEmpty(privateEmail);
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOd) {
		this.createdOn = createdOd;
	}

	public String getFullName() {
		return firstName + " " + lastName;
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getShirtNumber() {
		return shirtNumber;
	}

	public void setShirtNumber(String shirtNumber) {
		this.shirtNumber = shirtNumber;
	}

	public String getPrivateEmail() {
		return privateEmail;
	}

	public void setPrivateEmail(String privateEmail) {
		this.privateEmail = privateEmail;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public boolean hasProfileImage() {
		return image != null;
	}

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
