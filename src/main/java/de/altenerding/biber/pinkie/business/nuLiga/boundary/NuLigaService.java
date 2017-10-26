package de.altenerding.biber.pinkie.business.nuLiga.boundary;

import de.altenerding.biber.pinkie.business.nuLiga.control.NuLigaProcessor;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class NuLigaService {

	private NuLigaProcessor nuLigaProcessor;

	public void loadNuLigaData() throws Exception {
		nuLigaProcessor.loadNuLigaData();
	}

	@Inject
	public void setNuLigaProcessor(NuLigaProcessor nuLigaProcessor) {
		this.nuLigaProcessor = nuLigaProcessor;
	}
}
