package de.altenerding.biber.pinkie.business.file.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue(value = "TEXT")
@Access(AccessType.FIELD)
@Table(name = "text_mapping")
public class TextMapping extends Mapping {
    @Column(columnDefinition = "varchar")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
