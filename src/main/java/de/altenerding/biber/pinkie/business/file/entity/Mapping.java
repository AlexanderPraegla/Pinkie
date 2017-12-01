package de.altenerding.biber.pinkie.business.file.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "mapping_type", discriminatorType = DiscriminatorType.STRING)
@Access(AccessType.FIELD)
@Table(name = "mappings")
@NamedQueries({
         @NamedQuery(name = "Mapping.getByPage", query = "SELECT m FROM Mapping m where m.page = :page AND m.archivedOn IS NULL")
})
public abstract class Mapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String page;
    private String key;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    private Date createdOn;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "archived_on")
    private Date archivedOn;

    @PrePersist
    public void prePersist() {
        if (createdOn == null) {
            createdOn = new Date();
        }
    }

    public FileMapping getFileMapping() {
		if (this instanceof FileMapping) {
			return (FileMapping) this;
		}
		return null;
	}

    public TextMapping getTextMapping() {
		if (this instanceof TextMapping) {
			return (TextMapping) this;
		}
		return null;
	}

	public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
