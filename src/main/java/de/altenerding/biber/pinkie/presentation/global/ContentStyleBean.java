package de.altenerding.biber.pinkie.presentation.global;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class ContentStyleBean {

	public static final String CLUB_ID = "10640";

	public String svaTeamStyle(String clubId) {
		if (clubId.contains(CLUB_ID)) {
			return "font-weight:bold";
		} else {
			return "";
		}
	}
}
