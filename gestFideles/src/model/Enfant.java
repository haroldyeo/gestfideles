package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the enfants database table.
 * 
 */
@Entity
@Table(name="\"FIDELESDEV\".enfants")
@NamedQuery(name="Enfant.findAll", query="SELECT e FROM Enfant e")
public class Enfant implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date dateBapteme;
	private Date dob;
	private String lieuBapteme;
	private String nom;
	private String numBapteme;
	private Fidele fidele;

	public Enfant() {
	}

	

	public Enfant(Date dateBapteme, Date dob, String lieuBapteme, String nom, String numBapteme) {
		super();
		this.dateBapteme = dateBapteme;
		this.dob = dob;
		this.lieuBapteme = lieuBapteme;
		this.nom = nom;
		this.numBapteme = numBapteme;
	}



	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_bapteme")
	public Date getDateBapteme() {
		return this.dateBapteme;
	}

	public void setDateBapteme(Date dateBapteme) {
		this.dateBapteme = dateBapteme;
	}


	@Temporal(TemporalType.DATE)
	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}


	@Column(name="lieu_bapteme")
	public String getLieuBapteme() {
		return this.lieuBapteme;
	}

	public void setLieuBapteme(String lieuBapteme) {
		this.lieuBapteme = lieuBapteme;
	}


	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}


	@Column(name="num_bapteme")
	public String getNumBapteme() {
		return this.numBapteme;
	}

	public void setNumBapteme(String numBapteme) {
		this.numBapteme = numBapteme;
	}


	//bi-directional many-to-one association to Fidele
	@ManyToOne
	public Fidele getFidele() {
		return this.fidele;
	}

	public void setFidele(Fidele fidele) {
		this.fidele = fidele;
	}

}