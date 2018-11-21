package de.altenerding.biber.pinkie.business.config.entity;

public enum ConfigProperty {
	NU_LIGA_API_TOKEN("nuliga.api.token", String.class),
	NU_LIGA_API_RESFRESH_TOKEN("nuliga.api.refresh.token", String.class);

	private final String property;
	private final Class<String> valueClass;

	ConfigProperty(String property, Class<String> valueClass) {
		this.property = property;
		this.valueClass = valueClass;
	}

	public String getProperty() {
		return property;
	}

	public Class<String> getValueClass() {
		return valueClass;
	}
}
