package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the bapteme database table.
 * 
 */
@Entity

@Table(name="\"FIDELESDEV\".sacrement_malade")
//@NamedQuery(name="Bapteme.findAll", query="SELECT b FROM Bapteme b")
public class SacrementMalades implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date date;
	private String lieu;
	private Fidele fidele;

	public SacrementMalades() {
	}
	

	public SacrementMalades(Date date, String lieu) {
		super();
		this.date = date;
		this.lieu = lieu;
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
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLieu() {
		return lieu;
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