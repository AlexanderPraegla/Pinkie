package de.altenerding.biber.pinkie.business.report.control;

import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.notification.control.MessageSender;
import de.altenerding.biber.pinkie.business.notification.entity.NotificationType;
import de.altenerding.biber.pinkie.business.notification.entity.Placeholder;
import de.altenerding.biber.pinkie.business.report.entity.Report;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReportProcessor {

	@PersistenceContext
	private EntityManager em;
	private Logger logger;
    @Inject
    private MessageSender messageSender;

	public void createReport(Report report) {
		logger.info("Creating new Report with title={} from author={}", report.getTitle(), report.getAuthor().getFullName());
		em.persist(report);
		em.flush();

        Map<Placeholder, String> placeholders = new HashMap<>();
        placeholders.put(Placeholder.AUTHOR, report.getAuthor().getFullName());
        messageSender.sendAdminNotifications(NotificationType.REPORT_IN_REVIEW, placeholders);
    }

    public void releaseReport(Report report, Member member) {
        logger.info("Releasing report with id={}", report.getId());
        report.setReleased(true);
        report.setReleasedBy(member);
        report.setReleasedOn(new Date(System.currentTimeMillis()));
        updateReport(report);

        Map<Placeholder, String> placeholders = new HashMap<>();
        placeholders.put(Placeholder.REPORT_TITLE, report.getTitle());
		placeholders.put(Placeholder.AUTHOR, report.getAuthor().getFullName());
        if (report.getTeam() == null) {
            messageSender.sendNotifications(NotificationType.REPORT_GENERAL, placeholders);
        } else {
            placeholders.put(Placeholder.TEAM_NAME, report.getTeam().getName());
            messageSender.sendReportNotifications(NotificationType.REPORT_TEAM, report.getTeam(), placeholders);
        }
    }

	public void updateReport(Report report) {
		logger.info("Updating report with id={}", report.getId());
		em.merge(report);
		em.flush();
	}

    public void deleteReport(Report report) {
        logger.info("Deleting report with id={}", report.getId());
        report = em.merge(report);
        em.remove(report);
        em.flush();
    }

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
