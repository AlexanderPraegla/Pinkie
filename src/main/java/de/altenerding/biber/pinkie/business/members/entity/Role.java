package de.altenerding.biber.pinkie.business.members.entity;

public enum Role {
	ADMIN("Admin"),
	PRESS("Pressebeauftrager"),
	MEMBER("Mitglied");

	private final String label;

	Role(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
