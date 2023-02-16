package studio.entities;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import studio.dao.RolaDAO;
import studio.dao.UzytkownikDAO;
import studio.entities.Uzytkownik;
import studio.entities.Rola;
import javax.ejb.*;
import javax.faces.context.ExternalContext;
import javax.faces.context.Flash;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named
@RequestScoped
//@SessionScoped
public class UzytkownikListBB {

	private static final String PAGE_USER_EDIT = "uzytkownikEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String login;

	@Inject
	ExternalContext extcontext;

	@Inject
	Flash flash;

	@EJB
	UzytkownikDAO uzytkownikDAO;

	@EJB
	RolaDAO rolaDAO;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public List<Uzytkownik> getFullList() {
		return uzytkownikDAO.getFullList();
	}

	public List<Uzytkownik> getList() {
		List<Uzytkownik> list = null;

		// 1. Prepare search params
		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (login != null && login.length() > 0) {
			searchParams.put("login", login);
		}

		// 2. Get list
		list = uzytkownikDAO.getList(searchParams);

		return list;
	}

	

	public String editUzytkownik(Uzytkownik uzytkownik) { // 1. Pass object through session
		HttpSession session = (HttpSession) extcontext.getSession(true);
		session.setAttribute("uzytkownik", uzytkownik);

		// 2. Pass object throughflash
		flash.put("uzytkownik", uzytkownik);

		return PAGE_USER_EDIT;
	}

	public String deleteUzytkownik(Uzytkownik uzytkownik) {
		uzytkownikDAO.remove(uzytkownik);
		return PAGE_STAY_AT_THE_SAME;
	}

}
