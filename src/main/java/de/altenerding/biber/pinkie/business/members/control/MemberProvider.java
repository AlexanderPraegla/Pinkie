package de.altenerding.biber.pinkie.business.members.control;

import de.altenerding.biber.pinkie.business.members.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class MemberProvider {

	@PersistenceContext
	private EntityManager em;

	public Member getMemberById(long id) {
		return em.createNamedQuery("member.findById", Member.class).setParameter("id", id).getSingleResult();
	}

	public Member getMemberByEmail(String email) {
		return em.createNamedQuery("member.findByEmail", Member.class).setParameter("email", email).getSingleResult();
	}

	public List<Member> getMembers() {
		return em.createNamedQuery("member.findAll", Member.class).getResultList();
	}

}
