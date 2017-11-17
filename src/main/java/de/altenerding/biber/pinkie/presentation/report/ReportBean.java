package de.altenerding.biber.pinkie.presentation.report;

import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import de.altenerding.biber.pinkie.business.file.entity.FileCategory;
import de.altenerding.biber.pinkie.business.file.entity.Image;
import de.altenerding.biber.pinkie.business.members.entity.Access;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import de.altenerding.biber.pinkie.business.report.boundary.ReportService;
import de.altenerding.biber.pinkie.business.report.entity.Report;
import de.altenerding.biber.pinkie.presentation.session.UserSessionBean;
import net.bootsfaces.utils.FacesMessages;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
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
	private UserSessionBean userSessionBean;
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
	@Access(role = Role.PRESS)
	public String saveReport() {
		logger.info("Creating new Report with title={}", report.getTitle());
		String result;
		try {
			report.setAuthor(userSessionBean.getMember());

			if (file != null) {
				Image image = fileService.uploadImage(file, FileCategory.IMAGES_REPORT, null);
				report.setImage(image);
			}

			reportService.createReport(report);

			FacesMessages.info(report.getType().getLabel(), "Erstellt");
			result = "/public/news/report.xhtml?faces-redirect=true";
		} catch (Exception e) {
			logger.info("Error while creating report", e);
			FacesMessages.error("Fehler beim speichern");
			result = "/secure/report/reportAdd.xhtml?faces-redirect=true";
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return result;
	}

	@Access(role = Role.PRESS)
	public String updateReport() {
		logger.info("Updating report with id={}", report.getId());
		reportService.updateReport(report);

		FacesMessages.info(report.getType().getLabel(), "Aktualisiert");
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return "/public/news/report.xhtml?faces-redirect=true";
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

	public void setReport(Report report) {
		this.report = report;
	}

	public Report getReport() {
		return report;
	}

	public List<Report> getReports() {
		return reports;
	}

	public void setUserSessionBean(UserSessionBean userSessionBean) {
		this.userSessionBean = userSessionBean;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public Part getFile() {
		return file;
	}
}
