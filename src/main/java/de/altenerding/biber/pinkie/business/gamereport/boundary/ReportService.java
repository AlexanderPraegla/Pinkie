package de.altenerding.biber.pinkie.business.gamereport.boundary;

import de.altenerding.biber.pinkie.business.gamereport.control.ReportProcessor;
import de.altenerding.biber.pinkie.business.gamereport.control.ReportProvider;
import de.altenerding.biber.pinkie.business.gamereport.entity.Report;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ReportService {

	private ReportProvider reportProvider;
	private ReportProcessor reportProcessor;

	public List<Report> getGameReports() throws Exception{
		return reportProvider.getGameReports();
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
