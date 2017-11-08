package de.altenerding.biber.pinkie.presentation;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class TabBean implements Serializable {
	private String tabIndex = "0";

	public String getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(String tabIndex) {
		this.tabIndex = tabIndex;
	}
}
