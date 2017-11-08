package de.altenerding.biber.pinkie.presentation.global;

import de.altenerding.biber.pinkie.business.report.entity.ReportType;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class EnumBean {

	public ReportType[] getReportTypes() {
		return ReportType.values();
	}
}
