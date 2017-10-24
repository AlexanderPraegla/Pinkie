package de.altenerding.biber.pinkie.business.report.control;

import de.altenerding.biber.pinkie.business.report.entity.Report;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ReportProcessor {

	@PersistenceContext
	private EntityManager em;
	private Logger logger;

	public void createReport(Report report) {
		logger.info("Creating new Report with title={} from author={}", report.getTitle(), report.getAuthor().getFullName());
		em.persist(report);
		em.flush();
	}

	public void updateReport(Report report) {
		logger.info("Updating report with id={}", report.getId());
		em.merge(report);
		em.flush();
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
