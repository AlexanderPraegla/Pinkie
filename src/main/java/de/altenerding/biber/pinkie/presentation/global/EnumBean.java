package de.altenerding.biber.pinkie.presentation.global;

import de.altenerding.biber.pinkie.business.report.entity.ReportType;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class EnumBean {

	public ReportType[] getReportTypes() {
		return ReportType.values();
	}
}
