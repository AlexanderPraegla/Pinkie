package de.altenerding.biber.pinkie.business.gamereport.control;

import de.altenerding.biber.pinkie.business.gamereport.entity.Report;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ReportProvider {

	@PersistenceContext
	private EntityManager em;
	private Logger logger;

	public List<Report> getGameReports() {
		logger.info("Loading all reports from database");
		return em.createNamedQuery("GameReport.findAll", Report.class).getResultList();
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
