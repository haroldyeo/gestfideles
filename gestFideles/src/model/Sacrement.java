package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the sacrements database table.
 * 
 */
@Entity
@Table(name="\"FIDELESDEV\".sacrements")
@NamedQuery(name="Sacrement.findAll", query="SELECT s FROM Sacrement s")
public class Sacrement implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date dateSacrement;
	private String libelle;
	private String lieu;
	private Fidele fidele;

	public Sacrement() {
	}

	

	public Sacrement(Date dateSacrement, String libelle, String lieu, Fidele fidele) {
		super();
		this.dateSacrement = dateSacrement;
		this.libelle = libelle;
		this.lieu = lieu;
		this.fidele = fidele;
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
	@Column(name="date_sacrement")
	public Date getDateSacrement() {
		return this.dateSacrement;
	}

	public void setDateSacrement(Date dateSacrement) {
		this.dateSacrement = dateSacrement;
	}


	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	public String getLieu() {
		return this.lieu;
	}

	public void setLieu(String lieu) {
		this.lieu = lieu;
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