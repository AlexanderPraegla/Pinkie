package de.altenerding.biber.pinkie.user.entity;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "user.findAll", query = "SELECT u FROM PinkieUser u"),
        @NamedQuery(name = "user.findById", query = "SELECT u FROM PinkieUser u where u.id = :id")
})
public class PinkieUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	@Column
    private String firstName;
	@Column
    private String lastName;

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
}
