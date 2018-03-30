package de.altenerding.biber.pinkie.business.referee.boundary;

import de.altenerding.biber.pinkie.business.referee.control.RefereeProcessor;
import de.altenerding.biber.pinkie.business.referee.control.RefereeProvider;
import de.altenerding.biber.pinkie.business.referee.entity.Referee;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class RefereeService {

	private RefereeProvider refereeProvider;
	private RefereeProcessor refereeProcessor;

	public List<Referee> getCurrentReferees() {
		return refereeProvider.getCurrentReferees();
	}

	public Referee getRefereeById(long refereeId) {
		return refereeProvider.getRefereeById(refereeId);
	}

	public void updateReferee(Referee referee) {
		refereeProcessor.updateReferee(referee);
	}


	public void removeReferee(Referee referee) {
		refereeProcessor.removeReferee(referee);
	}

	public void updateReferees(List<Referee> referees) {
		refereeProcessor.updateReferees(referees);
	}

	public void createReferee(Referee referee) {
		refereeProcessor.createReferee(referee);
	}

	@Inject
	public void setRefereeProvider(RefereeProvider refereeProvider) {
		this.refereeProvider = refereeProvider;
	}

	@Inject
	public void setRefereeProcessor(RefereeProcessor refereeProcessor) {
		this.refereeProcessor = refereeProcessor;
	}
}
