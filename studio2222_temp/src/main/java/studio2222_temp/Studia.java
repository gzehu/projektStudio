package studio2222_temp;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the studia database table.
 * 
 */
@Entity
@NamedQuery(name="Studia.findAll", query="SELECT s FROM Studia s")
public class Studia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_studio")
	private int idStudio;

	@Column(name="`Kod pocztowy`")
	private String kod_pocztowy;

	private String miejscowosc;

	private String ulica;

	//bi-directional many-to-one association to Owner
	@OneToMany(mappedBy="studia")
	private List<Owner> owners;

	//bi-directional one-to-one association to Reservation
	@OneToOne(mappedBy="studia")
	private Reservation reservation;

	public Studia() {
	}

	public int getIdStudio() {
		return this.idStudio;
	}

	public void setIdStudio(int idStudio) {
		this.idStudio = idStudio;
	}

	public String getKod_pocztowy() {
		return this.kod_pocztowy;
	}

	public void setKod_pocztowy(String kod_pocztowy) {
		this.kod_pocztowy = kod_pocztowy;
	}

	public String getMiejscowosc() {
		return this.miejscowosc;
	}

	public void setMiejscowosc(String miejscowosc) {
		this.miejscowosc = miejscowosc;
	}

	public String getUlica() {
		return this.ulica;
	}

	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

	public List<Owner> getOwners() {
		return this.owners;
	}

	public void setOwners(List<Owner> owners) {
		this.owners = owners;
	}

	public Owner addOwner(Owner owner) {
		getOwners().add(owner);
		owner.setStudia(this);

		return owner;
	}

	public Owner removeOwner(Owner owner) {
		getOwners().remove(owner);
		owner.setStudia(null);

		return owner;
	}

	public Reservation getReservation() {
		return this.reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

}