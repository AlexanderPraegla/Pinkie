package de.altenerding.biber.pinkie.presentation.sponsor;

import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import de.altenerding.biber.pinkie.business.file.entity.FileCategory;
import de.altenerding.biber.pinkie.business.file.entity.Image;
import de.altenerding.biber.pinkie.business.members.entity.Access;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import de.altenerding.biber.pinkie.business.sponsor.Sponsor;
import de.altenerding.biber.pinkie.business.sponsor.SponsorService;
import net.bootsfaces.utils.FacesMessages;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.util.List;

@Named
@RequestScoped
public class SponsorBean {

	private List<Sponsor> premiumSponsors;
	private List<Sponsor> nonePremiumSponsors;
	private List<Sponsor> sponsors;
	private Part file;
	private Sponsor sponsor = new Sponsor();
	@Inject
	private SponsorService sponsorService;
	@Inject
	private FileService fileService;
	@Inject
	private Logger logger;

	@Access(role = Role.ADMIN)
	public String creatSponsor() {
		sponsorService.createSponsor(sponsor);
		FacesMessages.info(sponsor.getName(), "Sponsor erstellt");
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return "success";
	}

	@Access(role = Role.ADMIN)
	public String updateSponsor(Sponsor sponsor) {
		try {
			if (file != null) {
				Image image = fileService.uploadImage(file, FileCategory.SPONSOR);
				sponsor.setImage(image);
			}

			sponsorService.updateSponsor(sponsor);

			FacesMessages.info(sponsor.getName(), "Sponsor aktualisiert");
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);
			return "success";
		} catch (Exception e) {
			logger.error("Error while updating sponsor", e);
			FacesMessages.error(sponsor.getName(), "Fehler beim Aktualisieren des Sponsors");
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);
			return "error";
		}
	}

	public List<Sponsor> getPremiumSponsors() {
		if (premiumSponsors == null) {
			premiumSponsors = sponsorService.getPremiumSponsors();
		}
		return premiumSponsors;
	}

	public List<Sponsor> getNonePremiumSponsors() {
		if (nonePremiumSponsors == null) {
			nonePremiumSponsors = sponsorService.getNonePremiumSponsors();
		}
		return nonePremiumSponsors;
	}

	public List<Sponsor> getSponsors() {
		if (sponsors == null) {
			sponsors = sponsorService.allSponsors();
		}
		return sponsors;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public Sponsor getSponsor() {
		return sponsor;
	}

	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}
}
