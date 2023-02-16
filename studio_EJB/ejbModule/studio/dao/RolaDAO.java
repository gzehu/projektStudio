package studio.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import studio.entities.Rola;

@Stateless
public class RolaDAO {

	@PersistenceContext(unitName = "Studio")
	protected EntityManager em;

	public void create(Rola rola) {
		em.persist(rola);
	}

	public Rola merge(Rola rola) {
		return em.merge(rola);
	}

	public void remove(Rola rola) {
		em.remove(rola);
	}

	public Rola find(Object id) {
		return em.find(Rola.class, id);
	}
	
	
	public Rola findByName(String id_rola) {
		Rola r = null;
		Query query = em.createQuery("Select r from Rola r where id_rola=:rola");
		if (id_rola != null) {
			query.setParameter("rola", id_rola );
		}
		
		try {
			r = (Rola)query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return r;
	}

	public List<Rola> getFullList() {
		List<Rola> list = null;

		Query query = em.createQuery("select r from Rola r");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Rola> getList(Map<String, Object> searchParams) {
		List<Rola> list = null;

		// 1. Build query string with parameters
		String select = "select r ";
		String from = "from Rola r ";
		String where = "";
		String orderby = "order by r.nazwa asc, r.id_rola";

		// search for surname
		String name = (String) searchParams.get("nazwa");
		if (name != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.nazwa like :nazwa ";
		}

		// ... other parameters ...

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (name != null) {
			query.setParameter("nazwa", name + "%");
		}

		// ... other parameters ...

		// 4. Execute query and retrieve list of Person objects
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

//	public void insertRola(int idRola) {
//
//		Query query1 = em.createQuery("select idRola from Rola r where r.idRola='3'");
//
//		int rola = (Integer) query1.getSingleResult();
//
//		Query query = em.createNativeQuery("insert into rola (idRola) values (?1)");
//
//		query.setParameter(1, rola);
//
//		query.executeUpdate();
//
//	}

}
