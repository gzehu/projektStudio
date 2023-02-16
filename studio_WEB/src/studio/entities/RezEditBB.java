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


import studio.dao.RezerwacjaDAO;
import studio.entities.Rezerwacja;


@Named
@ViewScoped
public class RezEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_AUTO_LIST = "rezList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Rezerwacja rezerwacja = new Rezerwacja();
	private Rezerwacja loaded = null;



	@EJB
	RezerwacjaDAO rezerwacjaDAO;
	
	@Inject
	FacesContext context;

	@Inject
	ExternalContext extctx;

	@Inject
	Flash flash;

	public Rezerwacja getRezerwacja() {
		return rezerwacja;
	}
	
	


	public void onLoad() throws IOException {
		// 1. load person passed through session
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		loaded = (Rezerwacja) session.getAttribute("idRezerwacja");

		// 2. load person passed through flash
		loaded = (Rezerwacja) flash.get("idRezerwacja");

		// cleaning: attribute received => delete it from session
		if (loaded != null) {
			rezerwacja = loaded;
			session.removeAttribute("idRezerwacja");
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
			if (!context.isPostback()) { // possible redirect
				context.getExternalContext().redirect("rezerwacjaList.xhtml");
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
			if (rezerwacja.getIdRezerwacja() == 0) {
				// new record
				rezerwacjaDAO.create(rezerwacja);
			} else {
				// existing record
				rezerwacjaDAO.merge(rezerwacja);

			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_AUTO_LIST;
	}

	public List<Rezerwacja> getList() {
		List<Rezerwacja> list = null;

		// 1. Prepare search params
		Map<String, Object> searchParams = new HashMap<String, Object>();

		// 2. Get list
		list = rezerwacjaDAO.getList(searchParams);

		return list;
	}

}
