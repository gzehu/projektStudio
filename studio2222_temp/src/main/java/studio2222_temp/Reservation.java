package studio2222_temp;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.Date;


/**
 * The persistent class for the reservation database table.
 * 
 */
@Entity
@NamedQuery(name="Reservation.findAll", query="SELECT r FROM Reservation r")
public class Reservation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_res")
	private int idRes;

	@Temporal(TemporalType.DATE)
	@Column(name="date_res")
	private Date dateRes;

	private String potwierdzona;

	private String rodzaj;

	@Column(name="time_res")
	private Time timeRes;

	//bi-directional one-to-one association to User
	@OneToOne(mappedBy="reservation")
	private User user;

	//bi-directional one-to-one association to Studia
	@OneToOne
	@JoinColumn(name="id_studio")
	private Studia studia;

	public Reservation() {
	}

	public int getIdRes() {
		return this.idRes;
	}

	public void setIdRes(int idRes) {
		this.idRes = idRes;
	}

	public Date getDateRes() {
		return this.dateRes;
	}

	public void setDateRes(Date dateRes) {
		this.dateRes = dateRes;
	}

	public String getPotwierdzona() {
		return this.potwierdzona;
	}

	public void setPotwierdzona(String potwierdzona) {
		this.potwierdzona = potwierdzona;
	}

	public String getRodzaj() {
		return this.rodzaj;
	}

	public void setRodzaj(String rodzaj) {
		this.rodzaj = rodzaj;
	}

	public Time getTimeRes() {
		return this.timeRes;
	}

	public void setTimeRes(Time timeRes) {
		this.timeRes = timeRes;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Studia getStudia() {
		return this.studia;
	}

	public void setStudia(Studia studia) {
		this.studia = studia;
	}

}