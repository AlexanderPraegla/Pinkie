package de.altenerding.biber.pinkie.business.members.bounday;

import de.altenerding.biber.pinkie.business.members.control.MemberProvider;
import de.altenerding.biber.pinkie.business.members.entity.Member;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class MemberService {

	private MemberProvider memberProvider;

	public List<Member> getMembers() {
		return memberProvider.getMembers();
	}

	public Member getMemberById(long id) {
		return memberProvider.getMemberById(id);
	}

    @Inject
	public void setMemberProvider(MemberProvider memberProvider) {
		this.memberProvider = memberProvider;
	}
}
