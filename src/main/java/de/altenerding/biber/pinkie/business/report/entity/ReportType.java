package de.altenerding.biber.pinkie.business.report.entity;

public enum ReportType {
	ANNOUNCEMENT("Ankündigung"),
	INFORMATION("Information"),
	GAMESREPORT("Spielbericht"),
	OTHER("Sonstiges");

	private final String label;

	ReportType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static ReportType get(String type) {
		for (ReportType reportType : values()) {
			if (reportType.getLabel().equals(type)) {
				return reportType;
			}
		}
		return null;
	}
}
