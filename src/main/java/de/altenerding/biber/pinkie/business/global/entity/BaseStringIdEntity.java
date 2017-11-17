package de.altenerding.biber.pinkie.business.global.entity;

import java.io.Serializable;

public abstract class BaseStringIdEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public abstract String getId();

	public abstract void setId(String id);

	@Override
	public int hashCode() {
		return (getId() != null)
				? (getClass().getSimpleName().hashCode() + getId().hashCode())
				: super.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		return (other != null && getId() != null
				&& other.getClass().isAssignableFrom(getClass())
				&& getClass().isAssignableFrom(other.getClass()))
				? getId().equals(((BaseStringIdEntity) other).getId())
				: (other == this);
	}

}
