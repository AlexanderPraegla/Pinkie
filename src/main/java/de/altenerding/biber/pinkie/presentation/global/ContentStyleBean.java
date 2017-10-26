package de.altenerding.biber.pinkie.presentation.global;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
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
