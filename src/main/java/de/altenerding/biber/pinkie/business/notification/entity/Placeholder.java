package de.altenerding.biber.pinkie.business.notification.entity;

public enum Placeholder {
	FIRSTNAME("[FIRSTNAME]"),
	PASSWORD("[PASSWORD]");

	private final String placeholder;

	Placeholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public String getPlaceholder() {
		return placeholder;
	}
}
