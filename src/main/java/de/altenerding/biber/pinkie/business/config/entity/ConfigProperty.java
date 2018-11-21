package de.altenerding.biber.pinkie.business.config.entity;

public enum ConfigProperty {
	DEFAULT("do_not_user", Object.class),
	NU_LIGA_API_TOKEN("nuliga.api.token", String.class),
	NU_LIGA_API_TOKEN_REFRESH("nuliga.api.token.refresh", String.class),
	NU_LIGA_API_CLIENT_ID("nuliga.api.client.id", String.class),
	NU_LIGA_API_CLIENT_SECRET("nuliga.api.client.secret", String.class),
	NU_LIGA_API_GRANT_TYPE("nuliga.api.grant.type", String.class),
	NU_LIGA_API_GRANT_TYPE_REFRESH("nuliga.api.grant.type.refresh", String.class),
	NU_LIGA_API_SCOPE("nuliga.api.scope", String.class),
	NU_LIGA_API_BASE_URL("nuliga.api.base.url", String.class),
	RESOURCE_FOLDER("resources.folder", String.class),

	;

	private final String property;
	private final Class<?> valueClass;

	ConfigProperty(String property, Class<?> valueClass) {
		this.property = property;
		this.valueClass = valueClass;
	}

	public String getProperty() {
		return property;
	}

	public Class<?> getValueClass() {
		return valueClass;
	}
}
