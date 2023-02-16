package studio.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the rola database table.
 * 
 */
@Entity
@NamedQuery(name="Rola.findAll", query="SELECT r FROM Rola r")
public class Rola implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_rola")
	private int idRola;

	private String nazwa;

	private String opis;

	//bi-directional many-to-one association to Uzytkownik
	@OneToMany(mappedBy="rola")
	private List<Uzytkownik> uzytkowniks;

	public Rola() {
	}

	public int getIdRola() {
		return this.idRola;
	}

	public void setIdRola(int idRola) {
		this.idRola = idRola;
	}

	public String getNazwa() {
		return this.nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getOpis() {
		return this.opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public List<Uzytkownik> getUzytkowniks() {
		return this.uzytkowniks;
	}

	public void setUzytkowniks(List<Uzytkownik> uzytkowniks) {
		this.uzytkowniks = uzytkowniks;
	}

	public Uzytkownik addUzytkownik(Uzytkownik uzytkownik) {
		getUzytkowniks().add(uzytkownik);
		uzytkownik.setRola(this);

		return uzytkownik;
	}

	public Uzytkownik removeUzytkownik(Uzytkownik uzytkownik) {
		getUzytkowniks().remove(uzytkownik);
		uzytkownik.setRola(null);

		return uzytkownik;
	}

}