package de.altenerding.biber.pinkie.presentation.referee;

import de.altenerding.biber.pinkie.business.referee.boundary.RefereeService;
import de.altenerding.biber.pinkie.business.referee.entity.Referee;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@RequestScoped
public class RefereeBean implements Serializable {

	private RefereeService refereeService;
	private List<Referee> referees;

	@Inject
	public void setRefereeService(RefereeService refereeService) {
		this.refereeService = refereeService;
	}

	public List<Referee> getReferees() {
		if (referees == null) {
			referees = refereeService.getCurrentReferees();
		}
		return referees;
	}

	public void setReferees(List<Referee> referees) {
		this.referees = referees;
	}
}
