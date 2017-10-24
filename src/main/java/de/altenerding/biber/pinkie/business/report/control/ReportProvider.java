package de.altenerding.biber.pinkie.business.report.control;

import de.altenerding.biber.pinkie.business.report.entity.Report;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ReportProvider {

	@PersistenceContext
	private EntityManager em;
	private Logger logger;

	public List<Report> getReports() {
		logger.info("Loading all reports from database");
		return em.createNamedQuery("Report.findAll", Report.class).setMaxResults(30).getResultList();
	}

	public Report getReportById(long reportId) {
		logger.info("Loading report with id={}", reportId);
		return em.createNamedQuery("Report.findById", Report.class).setParameter("id", reportId).getSingleResult();
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
