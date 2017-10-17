package de.altenerding.biber.pinkie.members.control;

import de.altenerding.biber.pinkie.members.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class MemberProvider {

	@PersistenceContext
	private EntityManager em;

	public Member getMemberById(long id) {
		return em.createNamedQuery("member.findById", Member.class).setParameter("id", id).getSingleResult();
	}

	public List<Member> getMembers() {
		return em.createNamedQuery("member.findAll", Member.class).getResultList();
	}
}
