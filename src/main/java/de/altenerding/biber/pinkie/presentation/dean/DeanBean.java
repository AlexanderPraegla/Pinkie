package de.altenerding.biber.pinkie.presentation.dean;

import de.altenerding.biber.pinkie.business.dean.boundary.DeanService;
import de.altenerding.biber.pinkie.business.dean.entity.Dean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@RequestScoped
public class DeanBean implements Serializable {

	private DeanService deanService;
	private List<Dean> deans;

	@Inject
	public void setDeanService(DeanService deanService) {
		this.deanService = deanService;
	}

	public List<Dean> getDeans() {
		if (deans == null) {
			deans = deanService.getCurrentDeans();
		}
		return deans;
	}

	public void setDeans(List<Dean> deans) {
		this.deans = deans;
	}
}
