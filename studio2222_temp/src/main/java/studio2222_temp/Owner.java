package studio2222_temp;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the owners database table.
 * 
 */
@Entity
@Table(name="owners")
@NamedQuery(name="Owner.findAll", query="SELECT o FROM Owner o")
public class Owner implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_owner")
	private int idOwner;

	@Column(name="owner_name")
	private String ownerName;

	@Column(name="owner_surname")
	private String ownerSurname;

	//bi-directional many-to-one association to Studia
	@ManyToOne
	@JoinColumn(name="id_studio")
	private Studia studia;

	public Owner() {
	}

	public int getIdOwner() {
		return this.idOwner;
	}

	public void setIdOwner(int idOwner) {
		this.idOwner = idOwner;
	}

	public String getOwnerName() {
		return this.ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerSurname() {
		return this.ownerSurname;
	}

	public void setOwnerSurname(String ownerSurname) {
		this.ownerSurname = ownerSurname;
	}

	public Studia getStudia() {
		return this.studia;
	}

	public void setStudia(Studia studia) {
		this.studia = studia;
	}

}