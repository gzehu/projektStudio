package studio.entities;

import studio.dao.RolaDAO;
import studio.dao.UzytkownikDAO;
import studio.entities.Rola;
import studio.entities.Uzytkownik;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@ViewScoped
@Named
public class RegistrationBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_USER_LOGIN = "uzytkownikLogin?faces-redirect=true";

	private String haslo2;
	private Uzytkownik uzytkownik = new Uzytkownik();
	private Rola rola = new Rola();

	@EJB
	UzytkownikDAO uzytkownikDAO;

	@EJB
	RolaDAO rolaDAO;

	@Inject
	FacesContext context;
	
	@Inject
	@ManagedProperty("#{txtRegErr}")
	private ResourceBundle txtRegErr;
	
	@Inject
	@ManagedProperty("#{txtReg}")
	private ResourceBundle txtReg;

	
	public String getPass2() {
		return haslo2;
	}
	

	public void setPass2(String haslo2) {
		this.haslo2 = haslo2;
	}
	

	public Uzytkownik getUzytkownik() {
		return uzytkownik;
	}
	

	public Rola getRola() {
		return rola;
	}

	  
 
	
	public String register() {

		if (!uzytkownik.getPass().equals(haslo2)) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, txtRegErr.getString("registerPassNotSame"), null));
			return null;
		} else if (uzytkownik.getLogin().equals("admin") && uzytkownik.getPass().equals("admin")
				|| uzytkownik.getLogin().equals("moderator") && uzytkownik.getPass().equals("moderator")) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, txtRegErr.getString("registerWrongPass"), null));
			return null;
		} else if (uzytkownik.getLogin().equals(uzytkownikDAO.getLoginFromDB(uzytkownik.getLogin()))) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, txtRegErr.getString("registerUzytkownikExists"), null));
			return null;
		}

		try {
			if (uzytkownik.getIdUzytkownik() == 0) {
				
				Rola r = rolaDAO.findByName("3");
				uzytkownik.setRola(r);
				
				uzytkownikDAO.create(uzytkownik);

			} else {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Taki użytkownik już istnieje", null));
				return null;
			}
			

		
		     

		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return null;
		}

		return PAGE_USER_LOGIN;
	}

}