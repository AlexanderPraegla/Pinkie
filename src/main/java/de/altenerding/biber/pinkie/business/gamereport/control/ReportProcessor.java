package de.altenerding.biber.pinkie.business.gamereport.control;

import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ReportProcessor {

	@PersistenceContext
	private EntityManager em;
	private Logger logger;

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
