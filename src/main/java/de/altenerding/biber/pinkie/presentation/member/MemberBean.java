package de.altenerding.biber.pinkie.presentation.member;

import de.altenerding.biber.pinkie.business.members.bounday.MemberService;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@javax.enterprise.context.RequestScoped
public class MemberBean implements Serializable {

	@ManagedProperty(value = "#{param.memberId}")
	private long memberId;
	private MemberService memberService;
	private Logger logger;
	private List<Member> members;
	private Member member;

	public Member getMember() {
		return member;
	}

	public void initMember() {
		member = memberService.getMemberById(memberId);
	}

	@Inject
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public List<Member> getMembers() {
		if (members == null) {
			members = memberService.getMembers();
		}
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	public long getMemberId() {
		return memberId;
	}
}
