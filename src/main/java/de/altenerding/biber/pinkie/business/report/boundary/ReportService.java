package de.altenerding.biber.pinkie.business.report.boundary;

import de.altenerding.biber.pinkie.business.report.control.ReportProcessor;
import de.altenerding.biber.pinkie.business.report.control.ReportProvider;
import de.altenerding.biber.pinkie.business.report.entity.Report;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ReportService {

	private ReportProvider reportProvider;
	private ReportProcessor reportProcessor;

	public List<Report> getReports() throws Exception{
		return reportProvider.getReports();
	}

	public Report getReportById(long reportId) {
		return reportProvider.getReportById(reportId);
	}

	public void createReport(Report report) {
		reportProcessor.createReport(report);
	}

	public void updateReport(Report report) {
		reportProcessor.updateReport(report);
	}

	@Inject
	public void setReportProvider(ReportProvider reportProvider) {
		this.reportProvider = reportProvider;
	}

	@Inject
	public void setReportProcessor(ReportProcessor reportProcessor) {
		this.reportProcessor = reportProcessor;
	}
}
