package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the communion_pascale database table.
 * 
 */
@Entity
@Table(name="communion_pascale")
@NamedQuery(name="CommunionPascale.findAll", query="SELECT c FROM CommunionPascale c")
public class CommunionPascale implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer annee;
	private String denierCulte;
	private Fidele fidele;

	public CommunionPascale() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getAnnee() {
		return this.annee;
	}

	public void setAnnee(Integer annee) {
		this.annee = annee;
	}


	@Column(name="denier_culte")
	public String getDenierCulte() {
		return this.denierCulte;
	}

	public void setDenierCulte(String denierCulte) {
		this.denierCulte = denierCulte;
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