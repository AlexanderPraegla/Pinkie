package de.altenerding.biber.pinkie.user.control;

import de.altenerding.biber.pinkie.user.entity.PinkieUser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class UserProvider {

	@PersistenceContext
	private EntityManager em;

	public PinkieUser getUserById(long id) {
		return em.createNamedQuery("user.findById", PinkieUser.class).setParameter("id", id).getSingleResult();
	}

	public List<PinkieUser> getUsers() {
		return em.createNamedQuery("user.findAll", PinkieUser.class).getResultList();
	}
}
