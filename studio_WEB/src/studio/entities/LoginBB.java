package studio.entities;

import javax.inject.Inject;
import javax.inject.Named;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import studio.dao.RolaDAO;
import studio.dao.UzytkownikDAO;
import studio.entities.Uzytkownik;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.annotation.ManagedProperty;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.simplesecurity.RemoteClient;

@Named
@RequestScoped
//@SessionScoped
public class LoginBB {

	private static final String PAGE_MAIN = "/index";
	private static final String PAGE_LOGIN = "/login";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	
	private String login;
	private String pass;
	private int idUzytkownika;


	@Inject
	Flash flash;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public int getIdUzytkownika() {
		return idUzytkownika;
	}

	public void setIdUzytkownika(int idUzytkownika) {
		this.idUzytkownika = idUzytkownika;
	}
	



	@EJB
	RolaDAO rolaDAO;

	@Inject
	UzytkownikDAO uzytkownikDAO;
	
	@Inject
	@ManagedProperty("#{txtLogErr}")
	private ResourceBundle txtLogErr;

	
	public String doLogin() {

		FacesContext ctx = FacesContext.getCurrentInstance();

		// 1. verify login and password - get Uzytkownik from "database"
		Uzytkownik uzytkownik = uzytkownikDAO.getUzytkownikFromDB(login, pass);

		// 2. if bad login or password - stay with error info
		if (uzytkownik == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, txtLogErr.getString("loginWrongPass"), null));
			return PAGE_STAY_AT_THE_SAME;
		}

		// 3. if logged in: get Uzytkownik roles, save in RemoteClient and store it in session

		RemoteClient<Uzytkownik> client = new RemoteClient<Uzytkownik>(); // create new RemoteClient
		client.setDetails(uzytkownik);

		List<String> roles = uzytkownikDAO.getRolesFromDB(uzytkownik); // get Uzytkownik roles

		if (roles != null) { // save roles in RemoteClient
			for (String role : roles) {
				client.getRoles().add(role);
			}
		}
		Long userId = uzytkownikDAO.getUserIdByName(login);
		// store RemoteClient with request info in session (needed for SecurityFilter)
		HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
		client.store(request);
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		HttpServletRequest request2 = (HttpServletRequest) externalContext.getRequest();
		HttpSession session = request2.getSession();
		session.setAttribute("userName", login);

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ID", userId);

		// and enter the system (now SecurityFilter will pass the request)

		return PAGE_MAIN;
	}

	public String doLogout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		// Invalidate session
		// - all objects within session will be destroyed
		// - new session will be created (with new ID)
		session.invalidate();
		return PAGE_LOGIN;
	}

}
