package studio.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import studio.entities.Rola;
import studio.entities.Uzytkownik;

@Stateless
public class UzytkownikDAO {
	
	@PersistenceContext(unitName = "Studio")
	protected EntityManager em;
	
	public void create(Uzytkownik uzytkownik) {
		em.persist(uzytkownik);
	}
	
	public Uzytkownik merge(Uzytkownik uzytkownik) {
		return em.merge(uzytkownik);
	}
	
	public void remove(Uzytkownik uzytkownik) {
		em.remove(uzytkownik);
	}
	
	public Uzytkownik find(Object id) {
		return em.find(Uzytkownik.class, id);
	}

	public List<Uzytkownik> getFullList() {
		List<Uzytkownik> list = null;

		Query query = em.createQuery("select u from Uzytkownik u");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}	
	
	public List<Uzytkownik> getList(Map<String, Object> searchParams) {
		List<Uzytkownik> list = null;

		// 1. Build query string with parameters
		String select = "select u ";
		String from = "from Uzytkownik u ";
		String where = "";
		String orderby = "order by u.idUzytkownik asc, u.imie";

		// search for surname
		String login = (String) searchParams.get("login");
		if (login != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.login like :login ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (login != null) {
			query.setParameter("login", login+"%");
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
	
	public Uzytkownik getUzytkownikFromDB(String login, String pass) {
		
		Uzytkownik u = null;
		
		Query query = em.createQuery("select u from Uzytkownik u where u.login like :login and u.pass like :pass");
		
		if (login != null) {
			query.setParameter("login", login);
		}
		
		if (pass != null) {
			query.setParameter("pass", pass);
		}
		
		try {
			u = (Uzytkownik)query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return u;
	}
	
	public String getLoginFromDB(String login) {
		
		String log = null;
		
		Query query = em.createQuery("select login from Uzytkownik u where u.login like :login");
		query.setParameter("login", login);
		
		try {
			log = (String)query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return log;
	}
	

	
	public List<String> getRolesFromDB(Uzytkownik uzytkownik) { // -> Role do ogarnięcia
		
		ArrayList<String> roles = new ArrayList<String>();
		
		if (uzytkownik.getLogin().equals("admin") && uzytkownik.getPass().equals("admin")) {
			Query query1 = em.createQuery("select nazwa from Rola r where id_rola='1'");
			roles.add((String)query1.getSingleResult());
		} else if (uzytkownik.getLogin().equals("pracownik") && uzytkownik.getPass().equals("pracownik")) {
			Query query2 = em.createQuery("select nazwa from Rola r where id_rola='2'");
			roles.add((String)query2.getSingleResult());
		} else {
			Query query3 = em.createQuery("select nazwa from Rola r where id_rola='3'");
			roles.add((String)query3.getSingleResult());
		}
		
		return roles;
	}
	
	public void insertUserRole(int ID_Uzytkownik) {
		
		Query query1 = em.createQuery("select ID_Rola from Rola r where r.ID_Rola='1'");
		
		int rola = (Integer)query1.getSingleResult();
		
		Query query = em.createNativeQuery("insert into uzytkownik_rola (ID_Rola, ID_Uzytkownik) values (?, ?)");
		
		query.setParameter(1, rola);
		query.setParameter(2, ID_Uzytkownik);
		query.executeUpdate();
	}
	
	public List<Uzytkownik> getUserWithRoles() {
		
		List<Uzytkownik> list = null;
		
		Query query = em.createQuery("select u from Uzytkownik u");
		
		list = query.getResultList();
		
		return list;
	}
	
	public List<Uzytkownik> findbyid(int idUser) {
		List<Uzytkownik> list = null;

		// 1. Build query string with parameters
		String select = "select p ";
		String from = "from Uzytkownik p ";
		String where = "where p.id_Uzytkownik = :id_Uzytkownik";

		Query query = em.createQuery(select + from + where).setParameter("idUser", idUser).setMaxResults(1);
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public Long getUserIdByName(String login) {
	    Query query = em.createQuery("SELECT u.id FROM Uzytkownik u WHERE u.login = :login");
	    query.setParameter("login", login);
	    try {
	        return (Long) query.getSingleResult();
	    } catch (Exception e) {
	        return (Long) null;
	    }
	}
	
	public List<Uzytkownik> getListLazy(int start, int size) {
        
		List<Uzytkownik> list = null;

        Query query = em.createQuery("select u from Uzytkownik u");

        query.setFirstResult(start);
        query.setMaxResults(size);

        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
	
	public Uzytkownik findByName(String id_uzytkownik) {
		Uzytkownik r = null;
		Query query = em.createQuery("Select r from Uzytkownik r where id_uzytkownik=:uzytkownik");
		if (id_uzytkownik != null) {
			query.setParameter("uzytkownik", id_uzytkownik );
		}
		
		try {
			r = (Uzytkownik)query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return r;
	}
}
