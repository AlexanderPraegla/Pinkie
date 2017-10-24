package de.altenerding.biber.pinkie.business.report.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ReportTypeConverter  implements AttributeConverter<ReportType, String> {


	@Override
	public String convertToDatabaseColumn(ReportType reportType) {
		return reportType.name();
	}

	@Override
	public ReportType convertToEntityAttribute(String reportType) {
		return ReportType.valueOf(reportType);
	}
}

