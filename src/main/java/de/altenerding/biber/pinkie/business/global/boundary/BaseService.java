package de.altenerding.biber.pinkie.business.global.boundary;

import de.altenerding.biber.pinkie.business.global.entity.BaseLongIdEntity;
import de.altenerding.biber.pinkie.business.global.entity.BaseStringIdEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class BaseService {

	@PersistenceContext
	private EntityManager em;

	public BaseStringIdEntity findByStringId(Class<BaseStringIdEntity> type, String id) {
		return em.find(type, id);
	}

	public BaseLongIdEntity findByLongId(Class<BaseLongIdEntity> type, Long id) {
		return em.find(type, id);
	}

}
