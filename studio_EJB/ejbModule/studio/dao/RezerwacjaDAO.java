package studio.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import studio.entities.Rola;
import studio.entities.Rezerwacja;


@Stateless
public class RezerwacjaDAO {
	
	private String userName;
	
		public String getUserName() {
	        return userName;
	    }

	    public void setUserName(String userName) {
	        this.userName = userName;
	    }
	
	@PersistenceContext(unitName = "Studio")
	protected EntityManager em;
	
	public void create(Rezerwacja rezerwacja) {
		em.persist(rezerwacja);
	}
	
	public Rezerwacja merge(Rezerwacja rezerwacja) {
		return em.merge(rezerwacja);
	}
	
	public void remove(Rezerwacja rezerwacja) {
		em.remove(rezerwacja);
	}
	
	public Rezerwacja find(Object id) {
		return em.find(Rezerwacja.class, id);
	}

	public List<Rezerwacja> getFullList() {
		List<Rezerwacja> list = null;

		Query query = em.createQuery("select u from Rezerwacja u");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}	
	
	public List<Rezerwacja> getList(Map<String, Object> searchParams) {
		List<Rezerwacja> list = null;

		// 1. Build query string with parameters
		String select = "select r ";
		String from = "from Rezerwacja r ";
		String where = "";
		String orderby = "order by r.idRezerwacja asc, r.data";

		// search for surname
		String data = (String) searchParams.get("data");
		if (data != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "r.data like :data ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (data != null) {
			query.setParameter("data", data+"%");
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
	

	public List<Rezerwacja> getListByLogin() {
		List<Rezerwacja> list = null;
		 ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	        HttpSession session = (HttpSession) externalContext.getSession(true);
	        this.userName = (String) session.getAttribute("userName");

		Query query = em.createQuery("select r from Rezerwacja r where r.login='"+ userName + "'");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}	
	
	
	public Rezerwacja getRezerwacjaFromDB(String data, String pass) {
		
		Rezerwacja u = null;
		
		Query query = em.createQuery("select u from Rezerwacja u where u.data like :data and u.pass like :pass");
		
		if (data != null) {
			query.setParameter("data", data);
		}
		
		if (pass != null) {
			query.setParameter("pass", pass);
		}
		
		try {
			u = (Rezerwacja)query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return u;
	}
	
	public String getLoginFromDB(String data) {
		
		String log = null;
		
		Query query = em.createQuery("select data from Rezerwacja u where u.data like :data");
		query.setParameter("data", data);
		
		try {
			log = (String)query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return log;
	}
	



	
	public List<Rezerwacja> getUserWithRoles() {
		
		List<Rezerwacja> list = null;
		
		Query query = em.createQuery("select u from Rezerwacja u");
		
		list = query.getResultList();
		
		return list;
	}
	
	public List<Rezerwacja> findbyid(int idUser) {
		List<Rezerwacja> list = null;

		// 1. Build query string with parameters
		String select = "select p ";
		String from = "from Rezerwacja p ";
		String where = "where p.id_Rezerwacja = :id_Rezerwacja";

		Query query = em.createQuery(select + from + where).setParameter("idUser", idUser).setMaxResults(1);
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public List<Rezerwacja> getListLazy(int start, int size) {
        
		List<Rezerwacja> list = null;

        Query query = em.createQuery("select u from Rezerwacja u");

        query.setFirstResult(start);
        query.setMaxResults(size);

        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
