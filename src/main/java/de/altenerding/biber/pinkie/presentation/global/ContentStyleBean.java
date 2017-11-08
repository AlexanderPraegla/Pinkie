package de.altenerding.biber.pinkie.presentation.global;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class ContentStyleBean {

	public static final String CLUB_NAME = "Altenerding";

	public String svaTeamStyle(String teamName) {
		if (teamName.contains(CLUB_NAME)) {
			return "font-weight:bold";
		} else {
			return "";
		}
	}
}
