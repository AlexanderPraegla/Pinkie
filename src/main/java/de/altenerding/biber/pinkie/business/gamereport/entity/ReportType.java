package de.altenerding.biber.pinkie.business.gamereport.entity;

public enum ReportType {
	ANNOUNCEMENT("Ankündigung"),
	INFORMATION("Information"),
	GAMESREPORT("Spielbericht"),
	OTHER("Sonstiges");

	private final String type;

	ReportType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static ReportType get(String type) {
		for (ReportType reportType : values()) {
			if (reportType.getType().equals(type)) {
				return reportType;
			}
		}
		return null;
	}
}
