package de.altenerding.biber.pinkie.presentation.report;

import de.altenerding.biber.pinkie.business.members.entity.Access;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import de.altenerding.biber.pinkie.business.report.boundary.ReportService;
import de.altenerding.biber.pinkie.business.report.entity.Report;
import de.altenerding.biber.pinkie.presentation.session.UserSessionBean;
import org.apache.logging.log4j.Logger;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ReportReviewBean implements Serializable {

    private Logger logger;
    private ReportService reportService;

    @Inject
    private UserSessionBean userSessionBean;
    private long reportId;
    private Part file;

    private List<Report> reports;
    private Report report = new Report();
    private List<Report> unreleasedReports;


    public void initReport() {
        if (reportId > 0) {
            logger.info("Loading report with id={}", reportId);
            report = reportService.getReportById(reportId);
        }
    }

    @Access(role = Role.ADMIN)
    public String releaseReport(Report report) {
        reportService.releaseReport(report, userSessionBean.getMember());

        return "releaseReport";
    }

    @Inject
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Inject
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    public long getReportId() {
        return reportId;
    }

    public void setReportId(long reportId) {
        this.reportId = reportId;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public List<Report> getReports() {
        if (reports == null) {
            reports = reportService.getReports();
        }
        return reports;
    }

    public void setUserSessionBean(UserSessionBean userSessionBean) {
        this.userSessionBean = userSessionBean;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public List<Report> getUnreleasedReports() {
        if (unreleasedReports == null) {
            unreleasedReports = reportService.getReportsForReview();
        }
        return unreleasedReports;
    }
}
