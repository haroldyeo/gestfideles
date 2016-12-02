package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the bapteme database table.
 * 
 */
@Entity

@Table(name="bapteme")
@NamedQuery(name="Bapteme.findAll", query="SELECT b FROM Bapteme b")
public class Bapteme implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date dateBapteme;
	private String diocese;
	private String eglise;
	private String numero;
	private String pretre;
	private Fidele fidele;

	public Bapteme() {
	}
	
	


	public Bapteme(Date dateBapteme, String diocese, String eglise, String numero, String pretre) {
		super();
		this.dateBapteme = dateBapteme;
		this.diocese = diocese;
		this.eglise = eglise;
		this.numero = numero;
		this.pretre = pretre;
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


	public String getDiocese() {
		return this.diocese;
	}

	public void setDiocese(String diocese) {
		this.diocese = diocese;
	}


	public String getEglise() {
		return this.eglise;
	}

	public void setEglise(String eglise) {
		this.eglise = eglise;
	}


	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}


	public String getPretre() {
		return this.pretre;
	}

	public void setPretre(String pretre) {
		this.pretre = pretre;
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