package de.altenerding.biber.pinkie.business.config.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity to save custom configurations in key / value pairs
 */
@Entity
public class Configuration {

	@Id
	private String property;
	@Column(columnDefinition = "varchar")
	private String value;

	public Configuration() {
	}

	public Configuration(String property, String value) {
		this.property = property;
		this.value = value;
	}

	public Configuration(ConfigProperty configProperty, String value) {
		this.property = configProperty.getProperty();
		this.value = value;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
