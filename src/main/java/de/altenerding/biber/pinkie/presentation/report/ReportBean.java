package de.altenerding.biber.pinkie.presentation.report;

import de.altenerding.biber.pinkie.business.report.boundary.ReportService;
import de.altenerding.biber.pinkie.business.report.entity.Report;
import de.altenerding.biber.pinkie.presentation.login.LoginBean;
import net.bootsfaces.utils.FacesMessages;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.List;

@ManagedBean
@RequestScoped
public class ReportBean {

	private Logger logger;
	private ReportService reportService;

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;
	@ManagedProperty(value = "#{param.reportId}")
	private long reportId;

	private List<Report> reports;
	private Report report = new Report();

	@PostConstruct
	public void init() {
		if (reports == null) {
			try {
				reports = reportService.getReports();
			} catch (Exception e) {
				logger.error("Error while loading all reports", e);
				FacesMessages.error("Es ist ein Fehler beim laden der Berichte aufgetreten: " + e.getMessage());
			}
		}
	}

	public void initReport() {
		logger.info("Loading report with id={}", reportId);
		report = reportService.getReportById(reportId);
	}

	/*
	TODO BUG: When refreshing the page after submitting the form the post request is sent again
	This is prevented by 'faces-redirect=true' but with this, the info message is not displayed.
	The workaround for this is the option 'context.getExternalContext().getFlash().setKeepMessages(true);'
	 */
	public String saveReport() {
		logger.info("Creating new Report with title={}", report.getTitle());
		report.setAuthor(loginBean.getMember());
		reportService.createReport(report);

		FacesMessages.info(report.getTeam().getName(), "Bericht erstellt");
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return "report.xhtml?faces-redirect=true";
	}

	public String updateReport() {
		logger.info("Updating report with id={}", report.getId());
		Report editReport = reportService.getReportById(report.getId());
		editReport.setTitle(report.getTitle());
		editReport.setSummary(report.getSummary());
		editReport.setText(report.getText());
		reportService.updateReport(editReport);

		FacesMessages.info(editReport.getTeam().getName(), "Bericht aktualisiert");
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return "report.xhtml?faces-redirect=true";
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Inject
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
	}

	public long getReportId() {
		return reportId;
	}

	public Report getReport() {
		return report;
	}

	public List<Report> getReports() {
		return reports;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}
}
