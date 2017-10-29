package de.altenerding.biber.pinkie.business.referee.boundary;

import de.altenerding.biber.pinkie.business.referee.control.RefereeProvider;
import de.altenerding.biber.pinkie.business.referee.entity.Referee;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class RefereeService {

	private RefereeProvider refereeProvider;

	public List<Referee> getCurrentReferees() {
		return refereeProvider.getCurrentReferees();
	}

	@Inject
	public void setRefereeProvider(RefereeProvider refereeProvider) {
		this.refereeProvider = refereeProvider;
	}
}
