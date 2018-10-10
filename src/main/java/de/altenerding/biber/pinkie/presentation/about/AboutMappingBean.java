package de.altenerding.biber.pinkie.presentation.about;

import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import de.altenerding.biber.pinkie.business.file.entity.Mapping;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class AboutMappingBean implements Serializable {

    @Inject
    private FileService fileService;

    private String historyText;
    private String journeyText;
    private String youthText;
    private String lotteryText;
    private String homepageInfosText;

    public void initHistoryFileMappings(String page) {
        Map<String, List<Mapping>> mappingForPage = fileService.getMappingForPage(page);
        if (mappingForPage.size() > 0) {
            historyText = mappingForPage.get("history.text").get(0).getTextMapping().getText();
        }
    }

    public void initJourneyFileMappings(String page) {
        Map<String, List<Mapping>> mappingForPage = fileService.getMappingForPage(page);
        if (mappingForPage.size() > 0) {
            journeyText = mappingForPage.get("journey.text").get(0).getTextMapping().getText();
        }
    }

    public void initYouthFileMappings(String page) {
        Map<String, List<Mapping>> mappingForPage = fileService.getMappingForPage(page);
        if (mappingForPage.size() > 0) {
            youthText = mappingForPage.get("youth.text").get(0).getTextMapping().getText();
        }
    }

    public void initLotteryFileMappings(String page) {
        Map<String, List<Mapping>> mappingForPage = fileService.getMappingForPage(page);
        if (mappingForPage.size() > 0) {
            lotteryText = mappingForPage.get("lottery.text").get(0).getTextMapping().getText();
        }
    }

    public void initHomepageInfosFileMappings(String page) {
        Map<String, List<Mapping>> mappingForPage = fileService.getMappingForPage(page);
        if (mappingForPage.size() > 0) {
            homepageInfosText = mappingForPage.get("homepageInfos.text").get(0).getTextMapping().getText();
        }
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

    public String getYouthText() {
        return youthText;
    }

    public void setYouthText(String youthText) {
        this.youthText = youthText;
    }

    public String getLotteryText() {
        return lotteryText;
    }

    public void setLotteryText(String lotteryText) {
        this.lotteryText = lotteryText;
    }

    public String getHomepageInfosText() {
        return homepageInfosText;
    }

    public void setHomepageInfosText(String homepageInfosText) {
        this.homepageInfosText = homepageInfosText;
    }
}
