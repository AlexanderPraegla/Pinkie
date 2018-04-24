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
		try {
			return em.createNamedQuery("member.findById", Member.class).setParameter("id", id).getSingleResult();
		} catch (Exception e) {
			logger.error("Exception while leading member by id={}", id, e);
			return null;
		}
	}

	public Member getMemberByEmail(String email) {
		logger.info("Getting member by email={}", email);
		try {
			return em.createNamedQuery("member.findByEmail", Member.class).setParameter("email", email).getSingleResult();
		} catch (Exception e) {
			logger.error("Exception while leading member by email={}", email, e);
			return null;
		}
	}

    public Member getMemberByAlias(String alias) {
        logger.info("Getting member by alias={}", alias);
        try {
            return em.createNamedQuery("member.findByAlias", Member.class).setParameter("alias", alias).getSingleResult();
        } catch (Exception e) {
			logger.info("Exception while loading member by alias={}", alias, e);
            return null;
        }
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
