package de.altenerding.biber.pinkie.business.dean.boundary;

import de.altenerding.biber.pinkie.business.dean.control.DeanProcessor;
import de.altenerding.biber.pinkie.business.dean.control.DeanProvider;
import de.altenerding.biber.pinkie.business.dean.entity.Dean;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class DeanService {

	private DeanProvider deanProvider;
	private DeanProcessor deanProcessor;

	public List<Dean> getCurrentDeans() {
		return deanProvider.getCurrentDeans();
	}

	public Dean getDeanById(long deanId) {
		return deanProvider.getDeanById(deanId);
	}

	public void updateDean(Dean dean) {
		deanProcessor.updateDean(dean);
	}

	public void updateDeans(List<Dean> deans) {
		deanProcessor.updateDeans(deans);
	}

	public void createDean(Dean dean) {
		deanProcessor.createDean(dean);
	}

	@Inject
	public void setDeanProvider(DeanProvider deanProvider) {
		this.deanProvider = deanProvider;
	}

	@Inject
	public void setDeanProcessor(DeanProcessor deanProcessor) {
		this.deanProcessor = deanProcessor;
	}
}
