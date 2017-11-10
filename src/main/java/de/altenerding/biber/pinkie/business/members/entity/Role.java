package de.altenerding.biber.pinkie.business.members.entity;

import java.util.ArrayList;
import java.util.List;

public enum Role {

	GUEST("Gast", false),
	ADMIN("Admin", true),
	PRESS("Pressebeauftrager", true),
	MEMBER("Mitglied", true);

	private final String label;
	private final boolean assignable;

	Role(String label, boolean assignable) {
		this.label = label;
		this.assignable = assignable;
	}

	public String getLabel() {
		return label;
	}

	public static Role[] getAssignableRoles() {
		List<Role> roleList = new ArrayList<>();
		for (Role role : Role.values()) {
			if (role.assignable) {
				roleList.add(role);
			}
		}
		Role[] roles = new Role[roleList.size()];
		return roleList.toArray(roles);
	}
}
