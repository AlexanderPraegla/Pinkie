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

	public List<Report> getReports() {
		return reportProvider.getReports();
	}

	public List<Report> getLatestReports() {
		return reportProvider.getLatestReports();
	}

	public List<Report> getReportsForReview() {
		return reportProvider.getUnreleasedReports();
	}

	public Report getReportById(long reportId) {
		return reportProvider.getReportById(reportId);
	}

	public List<Report> getReportsForTeam(long teamId, long seasonId) {
		return reportProvider.getReportsForTeam(teamId, seasonId);
	}

	public void createReport(Report report) {
		reportProcessor.createReport(report);
	}

	public void updateReport(Report report) {
		reportProcessor.updateReport(report);
	}

	public void releaseReport(Report report) {
		reportProcessor.releaseReport(report);
	}

	public void deleteReport(Report report) {
		reportProcessor.deleteReport(report);
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
