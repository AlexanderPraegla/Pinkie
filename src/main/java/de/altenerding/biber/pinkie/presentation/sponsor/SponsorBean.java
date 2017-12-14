package de.altenerding.biber.pinkie.presentation.sponsor;

import de.altenerding.biber.pinkie.business.sponsor.Sponsor;
import de.altenerding.biber.pinkie.business.sponsor.SponsorService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class SponsorBean {

	private List<Sponsor> premiumSponsors;
	private List<Sponsor> nonePremiumSponsors;
	@Inject
	private SponsorService sponsorService;

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
}
