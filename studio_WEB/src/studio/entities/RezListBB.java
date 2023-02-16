package studio.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import studio.dao.RezerwacjaDAO;

@Named
@RequestScoped
//@SessionScoped
public class RezListBB {
	
	private static final String PAGE_AUTO_EDIT = "rezEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;


	private String idRezerwacja;
	private String data;
		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	RezerwacjaDAO rezerwacjaDAO;

		
	public String getRezerwacja() {
		return idRezerwacja;
	}

	public void setRezerwacja(String idRezerwacja) {
		this.idRezerwacja = idRezerwacja;
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public List<Rezerwacja> getFullList(){
		return rezerwacjaDAO.getFullList();
	}

	public List<Rezerwacja> getListByLogin(){
		return rezerwacjaDAO.getListByLogin();
	}
	
	public List<Rezerwacja> getList(){
		List<Rezerwacja> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (data != null && data.length() > 0){
			searchParams.put("data", data);
		}
		
		//2. Get list
		list = rezerwacjaDAO.getList(searchParams);
		
		return list;
	}
	
	
	public String editRezerwacja(Rezerwacja rezerwacja) { // 1. Pass object through session
		HttpSession session = (HttpSession) extcontext.getSession(true);
		session.setAttribute("idRezerwacja", rezerwacja);

		// 2. Pass object throughflash
		flash.put("idRezerwacja", rezerwacja);

		return PAGE_AUTO_EDIT;
	}

	public String deleteRezerwacja(Rezerwacja rezerwacja) {
		rezerwacjaDAO.remove(rezerwacja);
		return PAGE_STAY_AT_THE_SAME;
	}
	
	public String newRezerwacja() {
		Rezerwacja rezerwacja = new Rezerwacja();

		// 1. Pass object through session
		HttpSession session = (HttpSession) extcontext.getSession(true);
		session.setAttribute("idRezerwacja", rezerwacja);

		// 2. Pass object through flash
		flash.put("idRezerwacja", rezerwacja);

		return PAGE_AUTO_EDIT;
	}
	
	
}