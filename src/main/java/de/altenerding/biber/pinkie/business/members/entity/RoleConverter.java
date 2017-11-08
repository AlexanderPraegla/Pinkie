package de.altenerding.biber.pinkie.business.members.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {

	@Override
	public String convertToDatabaseColumn(Role role) {
		return role.name();
	}

	@Override
	public Role convertToEntityAttribute(String role) {
		return Role.valueOf(role);
	}
}

