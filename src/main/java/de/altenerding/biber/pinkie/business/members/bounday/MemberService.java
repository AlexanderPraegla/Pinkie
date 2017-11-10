package de.altenerding.biber.pinkie.business.members.bounday;

import de.altenerding.biber.pinkie.business.members.entity.Member;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Stateless
public class MemberService implements Serializable {

	@PersistenceContext
	private EntityManager em;
	private Logger logger;

	public Member getMemberById(long id) {
		logger.info("Getting member by id={}", id);
		return em.createNamedQuery("member.findById", Member.class).setParameter("id", id).getSingleResult();
	}

	public Member getMemberByEmail(String email) {
		logger.info("Getting member by email={}", email);
		return em.createNamedQuery("member.findByEmail", Member.class).setParameter("email", email).getSingleResult();
	}

	public List<Member> getMembers() {
		return em.createNamedQuery("member.findAll", Member.class).getResultList();
	}

	public Member createMember(Member member) {
		logger.info("Persisting member with name={}", member.getFullName());
		em.persist(member);
		em.flush();
		return member;
	}

	public void updateMember(Member member) {
		logger.info("Updating member with id={}", member.getId());
		em.merge(member);
		em.flush();
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
