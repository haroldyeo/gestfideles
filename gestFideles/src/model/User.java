package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="\"FIDELESDEV\".utilisateurs")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String identifiant;
	private String motPasse;
	private String nom;
	private String prenoms;

	public User() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getIdentifiant() {
		return this.identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}


	@Column(name="mot_passe")
	public String getMotPasse() {
		return this.motPasse;
	}

	public void setMotPasse(String motPasse) {
		this.motPasse = motPasse;
	}


	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenoms() {
		return this.prenoms;
	}

	public void setPrenoms(String prenoms) {
		this.prenoms = prenoms;
	}

}