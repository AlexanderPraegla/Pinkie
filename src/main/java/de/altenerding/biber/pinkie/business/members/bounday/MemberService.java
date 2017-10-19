package de.altenerding.biber.pinkie.business.members.bounday;

import de.altenerding.biber.pinkie.business.members.control.LoginProvider;
import de.altenerding.biber.pinkie.business.members.control.MemberProvider;
import de.altenerding.biber.pinkie.business.members.entity.Member;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class MemberService {

	private MemberProvider memberProvider;
	private LoginProvider loginProvider;

	public List<Member> getMembers() {
		return memberProvider.getMembers();
	}

	public Member getMemberById(long id) {
		return memberProvider.getMemberById(id);
	}

	public Member login(String email, String password) {
		return loginProvider.login(email, password);
	}

    @Inject
	public void setMemberProvider(MemberProvider memberProvider) {
		this.memberProvider = memberProvider;
	}

	@Inject
	public void setLoginProvider(LoginProvider loginProvider) {
		this.loginProvider = loginProvider;
	}
}
