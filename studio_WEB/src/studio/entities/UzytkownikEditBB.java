package studio.entities;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.persistence.*;

import studio.dao.RolaDAO;
import studio.dao.UzytkownikDAO;
import studio.entities.Rola;
import studio.entities.Uzytkownik;

@Named
@ViewScoped
public class UzytkownikEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_USER_LIST = "uzytkownikList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Uzytkownik uzytkownik = new Uzytkownik();
	private Uzytkownik loaded = null;

	@EJB
	UzytkownikDAO uzytkownikDAO;

	@EJB
	RolaDAO rolaDAO;

	@Inject
	FacesContext context;

	@Inject
	ExternalContext extctx;

	@Inject
	Flash flash;

	public Uzytkownik getUzytkownik() {
		return uzytkownik;
	}

	public void onLoad() throws IOException {
		// 1. load person passed through session
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		loaded = (Uzytkownik) session.getAttribute("uzytkownik");

		// 2. load person passed through flash
		loaded = (Uzytkownik) flash.get("uzytkownik");

		// cleaning: attribute received => delete it from session
		if (loaded != null) {
			uzytkownik = loaded;
			session.removeAttribute("uzytkownik");
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
			if (!context.isPostback()) { // possible redirect
				context.getExternalContext().redirect("uzytkownikList.xhtml");
				context.responseComplete();
			}
		}

	}

	public String saveData() {
		// no Person object passed
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (uzytkownik.getIdUzytkownik() == 0) {
				// new record
				uzytkownikDAO.create(uzytkownik);
			} else {
				// existing record
				uzytkownikDAO.merge(uzytkownik);

			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_USER_LIST;
	}

	public List<Rola> getList() {
		List<Rola> list = null;

		// 1. Prepare search params
		Map<String, Object> searchParams = new HashMap<String, Object>();

		// 2. Get list
		list = rolaDAO.getList(searchParams);

		return list;
	}

}
