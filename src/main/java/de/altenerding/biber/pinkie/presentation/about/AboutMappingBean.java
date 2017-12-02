package de.altenerding.biber.pinkie.presentation.about;

import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import de.altenerding.biber.pinkie.business.file.entity.Mapping;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class AboutMappingBean {

	@Inject
	private FileService fileService;

	private String historyText;
	private String journeyText;

	public void initHistoryFileMappings(String page) {
		Map<String, List<Mapping>> mappingForPage = fileService.getMappingForPage(page);
		historyText = mappingForPage.get("history.text").get(0).getTextMapping().getText();
	}

	public void initJourneyFileMappings(String page) {
		Map<String, List<Mapping>> mappingForPage = fileService.getMappingForPage(page);
		journeyText = mappingForPage.get("journey.text").get(0).getTextMapping().getText();
	}

	public String getHistoryText() {
		return historyText;
	}

	public void setHistoryText(String historyText) {
		this.historyText = historyText;
	}

	public String getJourneyText() {
		return journeyText;
	}

	public void setJourneyText(String journeyText) {
		this.journeyText = journeyText;
	}
}
