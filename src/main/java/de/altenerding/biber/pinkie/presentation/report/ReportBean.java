package de.altenerding.biber.pinkie.presentation.report;

import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import de.altenerding.biber.pinkie.business.file.entity.FileDirectory;
import de.altenerding.biber.pinkie.business.report.boundary.ReportService;
import de.altenerding.biber.pinkie.business.report.entity.Report;
import de.altenerding.biber.pinkie.presentation.login.SessionBean;
import net.bootsfaces.utils.FacesMessages;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.util.List;

@Named
@RequestScoped
public class ReportBean {

	private Logger logger;
	private ReportService reportService;

	@Inject
	private SessionBean sessionBean;
	@ManagedProperty(value = "#{param.reportId}")
	private long reportId;
	private Part file;

	private List<Report> reports;
	private Report report = new Report();
	private FileService fileService;

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
		String result;
		try {
			report.setAuthor(sessionBean.getMember());

			if (file != null) {
				String fileName = fileService.uploadImage(file, FileDirectory.REPORT_IMAGE);
				report.setReportImage(fileName);
			}

			reportService.createReport(report);

			FacesMessages.info(report.getType().getLabel(), "Erstellt");
			result = "report.xhtml?faces-redirect=true";
		} catch (Exception e) {
			logger.info("Error while creating report", e);
			FacesMessages.error("Fehler beim speichern");
			result = "reportAdd.xhtml?faces-redirect=true";
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return result;
	}

	public String updateReport() {
		logger.info("Updating report with id={}", report.getId());
		Report editReport = reportService.getReportById(report.getId());
		editReport.setTitle(report.getTitle());
		editReport.setSummary(report.getSummary());
		editReport.setText(report.getText());
		reportService.updateReport(editReport);

		FacesMessages.info(report.getType().getLabel(), "Aktualisiert");
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

	@Inject
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
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

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public Part getFile() {
		return file;
	}
}
