package de.altenerding.biber.pinkie.business.dean.boundary;

import de.altenerding.biber.pinkie.business.dean.control.DeanProvider;
import de.altenerding.biber.pinkie.business.dean.entity.Dean;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class DeanService {

	private DeanProvider deanProvider;

	public List<Dean> getCurrentDeans() {
		return deanProvider.getCurrentDeans();
	}

	@Inject
	public void setDeanProvider(DeanProvider deanProvider) {
		this.deanProvider = deanProvider;
	}
}
