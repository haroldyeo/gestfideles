package model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the fideles database table.
 * 
 */
@Entity
@Table(name="\"FIDELESDEV\".fideles")
@NamedQuery(name="Fidele.findAll", query="SELECT f FROM Fidele f")
public class Fidele implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date dob;
	private String lieuNaissance;
	private String nom;
	private String nomMarraine;
	private String nomMere;
	private String nomParrain;
	private String nomPere;
	private String origineMere;
	private String originePere;
	private String prenoms;
	private List<Bapteme> baptemes;
	private List<CommunionPascale> communionPascales;
	private List<Enfant> enfants;
	private List<Mariage> mariages;
	private List<Sacrement> sacrements;
	private String _dob;

	public Fidele() {
	}
	
	


	public Fidele(Date dob, String lieuNaissance, String nom, String nomMarraine, String nomMere, String nomParrain,
			String nomPere, String origineMere, String originePere, String prenoms) {
		super();
		this.dob = dob;
		this.lieuNaissance = lieuNaissance;
		this.nom = nom;
		this.nomMarraine = nomMarraine;
		this.nomMere = nomMere;
		this.nomParrain = nomParrain;
		this.nomPere = nomPere;
		this.origineMere = origineMere;
		this.originePere = originePere;
		this.prenoms = prenoms;
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
	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}


	@Column(name="lieu_naissance")
	public String getLieuNaissance() {
		return this.lieuNaissance;
	}

	public void setLieuNaissance(String lieuNaissance) {
		this.lieuNaissance = lieuNaissance;
	}


	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}


	@Column(name="nom_marraine")
	public String getNomMarraine() {
		return this.nomMarraine;
	}

	public void setNomMarraine(String nomMarraine) {
		this.nomMarraine = nomMarraine;
	}


	@Column(name="nom_mere")
	public String getNomMere() {
		return this.nomMere;
	}

	public void setNomMere(String nomMere) {
		this.nomMere = nomMere;
	}


	@Column(name="nom_parrain")
	public String getNomParrain() {
		return this.nomParrain;
	}

	public void setNomParrain(String nomParrain) {
		this.nomParrain = nomParrain;
	}


	@Column(name="nom_pere")
	public String getNomPere() {
		return this.nomPere;
	}

	public void setNomPere(String nomPere) {
		this.nomPere = nomPere;
	}


	@Column(name="origine_mere")
	public String getOrigineMere() {
		return this.origineMere;
	}

	public void setOrigineMere(String origineMere) {
		this.origineMere = origineMere;
	}


	@Column(name="origine_pere")
	public String getOriginePere() {
		return this.originePere;
	}

	public void setOriginePere(String originePere) {
		this.originePere = originePere;
	}


	public String getPrenoms() {
		return this.prenoms;
	}

	public void setPrenoms(String prenoms) {
		this.prenoms = prenoms;
	}


	//bi-directional many-to-one association to Bapteme
	@OneToMany(cascade={CascadeType.ALL}, orphanRemoval = true, mappedBy="fidele")
	public List<Bapteme> getBaptemes() {
		if(baptemes == null)
				baptemes = new ArrayList<>();
		return this.baptemes;
	}

	public void setBaptemes(List<Bapteme> baptemes) {
		this.baptemes = baptemes;
	}

	public Bapteme addBapteme(Bapteme bapteme) {
		getBaptemes().add(bapteme);
		bapteme.setFidele(this);

		return bapteme;
	}

	public Bapteme removeBapteme(Bapteme bapteme) {
		getBaptemes().remove(bapteme);
		bapteme.setFidele(null);

		return bapteme;
	}


	//bi-directional many-to-one association to CommunionPascale
	@OneToMany(mappedBy="fidele")
	public List<CommunionPascale> getCommunionPascales() {
		return this.communionPascales;
	}

	public void setCommunionPascales(List<CommunionPascale> communionPascales) {
		this.communionPascales = communionPascales;
	}

	public CommunionPascale addCommunionPascale(CommunionPascale communionPascale) {
		getCommunionPascales().add(communionPascale);
		communionPascale.setFidele(this);

		return communionPascale;
	}

	public CommunionPascale removeCommunionPascale(CommunionPascale communionPascale) {
		getCommunionPascales().remove(communionPascale);
		communionPascale.setFidele(null);

		return communionPascale;
	}


	//bi-directional many-to-one association to Enfant
	@OneToMany(mappedBy="fidele")
	public List<Enfant> getEnfants() {
		return this.enfants;
	}

	public void setEnfants(List<Enfant> enfants) {
		this.enfants = enfants;
	}

	public Enfant addEnfant(Enfant enfant) {
		getEnfants().add(enfant);
		enfant.setFidele(this);

		return enfant;
	}

	public Enfant removeEnfant(Enfant enfant) {
		getEnfants().remove(enfant);
		enfant.setFidele(null);

		return enfant;
	}


	//bi-directional many-to-one association to Mariage
	@OneToMany(mappedBy="fidele")
	public List<Mariage> getMariages() {
		return this.mariages;
	}

	public void setMariages(List<Mariage> mariages) {
		this.mariages = mariages;
	}

	public Mariage addMariage(Mariage mariage) {
		getMariages().add(mariage);
		mariage.setFidele(this);

		return mariage;
	}

	public Mariage removeMariage(Mariage mariage) {
		getMariages().remove(mariage);
		mariage.setFidele(null);

		return mariage;
	}


	//bi-directional many-to-one association to Sacrement
	@OneToMany(cascade={CascadeType.ALL}, orphanRemoval = true, mappedBy="fidele")
	public List<Sacrement> getSacrements() {
		if(sacrements == null)
			sacrements = new ArrayList<>();
		return this.sacrements;
	}

	public void setSacrements(List<Sacrement> sacrements) {
		this.sacrements = sacrements;
	}

	public Sacrement addSacrement(Sacrement sacrement) {
		getSacrements().add(sacrement);
		sacrement.setFidele(this);

		return sacrement;
	}

	public Sacrement removeSacrement(Sacrement sacrement) {
		getSacrements().remove(sacrement);
		sacrement.setFidele(null);

		return sacrement;
	}



	@Transient
	public String get_dob() {
		return _dob;
	}




	public void set_dob(String _dob) {
		this._dob = _dob;
	}
	
	

}