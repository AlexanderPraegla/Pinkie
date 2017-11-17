package de.altenerding.biber.pinkie.business.global.entity;

import java.io.Serializable;

public abstract class BaseLongIdEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public abstract long getId();

	public abstract void setId(long id);

	@Override
	public int hashCode() {
		return (getId() != 0)
				? (getClass().getSimpleName().hashCode() + String.valueOf(getId()).hashCode())
				: super.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		return (other != null && getId() != 0
				&& other.getClass().isAssignableFrom(getClass())
				&& getClass().isAssignableFrom(other.getClass()))
				? getId() == (((BaseLongIdEntity) other).getId())
				: (other == this);
	}

}
