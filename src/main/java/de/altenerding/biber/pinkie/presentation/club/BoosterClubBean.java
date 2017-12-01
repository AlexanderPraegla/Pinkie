package de.altenerding.biber.pinkie.presentation.club;

import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import de.altenerding.biber.pinkie.business.file.entity.Mapping;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class BoosterClubBean {

	@Inject
	private FileService fileService;

	private String purpose;
	private String donationAccount;
	private String dean;

	public void initFileMappings(String page) {
		Map<String, List<Mapping>> mappingForPage = fileService.getMappingForPage(page);
		purpose = mappingForPage.get("boosterClub.purpose").get(0).getTextMapping().getText();
		donationAccount = mappingForPage.get("boosterClub.donationAccount").get(0).getTextMapping().getText();
		dean = mappingForPage.get("boosterClub.dean").get(0).getTextMapping().getText();
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getDonationAccount() {
		return donationAccount;
	}

	public void setDonationAccount(String donationAccount) {
		this.donationAccount = donationAccount;
	}

	public String getDean() {
		return dean;
	}

	public void setDean(String dean) {
		this.dean = dean;
	}
}
