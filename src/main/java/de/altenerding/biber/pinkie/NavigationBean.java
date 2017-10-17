package de.altenerding.biber.pinkie;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class NavigationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Logger logger = LogManager.getLogger(NavigationBean.class);
	private String page = "start.xhtml";

	public String getPage() {
		logger.info("Getting page=\'{}\'", page);
		return page;
	}

	public void setPage(String currentPage) {
		logger.info("Setting page=\'{}\'", currentPage);
		this.page = currentPage;
	}
}
