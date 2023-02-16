package studio.entities;

import studio.dao.RolaDAO;
import studio.dao.UzytkownikDAO;
import studio.dao.RezerwacjaDAO;
import studio.entities.Rola;
import studio.entities.Uzytkownik;
import studio.entities.Rezerwacja;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@ViewScoped
@Named
public class RezerwacjaBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_USER_LOGIN = "index?faces-redirect=true";

	private String userName;
	private Rezerwacja rezerwacja = new Rezerwacja();




	
	@EJB
	RezerwacjaDAO rezerwacjaDAO;

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

	

	public Rezerwacja getRezerwacja() {
		return rezerwacja;
	}
	
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
	
	  
 
	
	public String rezerwacja() {

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        this.userName = (String) session.getAttribute("userName");
    


		
	    String login = rezerwacja.getLogin();
	    
	    // zmiana wartości pola login
	    login = userName;
	    
	    // zapisanie zmienionej wartości pola login
	    rezerwacja.setLogin(login);
		
		try {
			if (rezerwacja.getIdRezerwacja() == 0) {
				
				
				rezerwacjaDAO.create(rezerwacja);

			} else {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Taka rezerwacja już istnieje", null));
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