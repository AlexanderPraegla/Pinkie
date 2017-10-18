package de.altenerding.biber.pinkie.members.bounday;

import de.altenerding.biber.pinkie.members.entity.Member;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Map;

@ManagedBean
@SessionScoped
public class MemberBean implements Serializable {

	private MemberService memberService;
	private Logger logger;

	private Member member;


	public String login() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params =
				fc.getExternalContext().getRequestParameterMap();
		logger.info("Number of params={}", params.size());
		return "report.xhtml";
	}

	public Member getMember() {
		return member;
	}

	@Inject
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
