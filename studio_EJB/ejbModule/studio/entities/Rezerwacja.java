package studio.entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;


/**
 * The persistent class for the rezerwacja database table.
 * 
 */
@Entity
@NamedQuery(name="Rezerwacja.findAll", query="SELECT r FROM Rezerwacja r")
public class Rezerwacja implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_rezerwacja")
	private int idRezerwacja;

	private String data;
	
	private String godzina;
	
	private String potwierdzona;

	private String zakonczona;
	
	private String imie;
	
	private String nazwisko;
	
	private String login;


	public Rezerwacja() {
	}

	public int getIdRezerwacja() {
		return this.idRezerwacja;
	}

	public void setIdRezerwacja(int idRezerwacja) {
		this.idRezerwacja = idRezerwacja;
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getGodzina() {
		return this.godzina;
	}

	public void setGodzina(String godzina) {
		this.godzina = godzina;
	}

	public String getPotwierdzona() {
		return this.potwierdzona;
	}

	public void setPotwierdzona(String potwierdzona) {
		this.potwierdzona = "NIE";
	}

	public String getZakonczona() {
		return this.zakonczona;
	}

	public void setZakonczona(String zakonczona) {
		this.zakonczona = "NIE";
	}

	
	public String getImie() {
		return this.imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}
	
	public String getNazwisko() {
		return this.nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}
	
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
	    this.login = login;
	}
}