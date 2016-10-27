package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the mariage database table.
 * 
 */
@Entity
@Table(name="\"FIDELESDEV\".mariage")
@NamedQuery(name="Mariage.findAll", query="SELECT m FROM Mariage m")
public class Mariage implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date benedNuptDate;
	private String benedNuptLieu;
	private Date dateBaptEpoux;
	private Date dateMariage;
	private String dispenseEveche;
	private String dispenseNum;
	private String epoux;
	private Date formalitesDate;
	private String formalitesNum;
	private String formalitesMairie;
	private String lieu;
	private String numBaptEpoux;
	private String pretre;
	private String temoin1;
	private String temoin2;
	private String numMariage;
	private Fidele fidele;

	public Mariage() {
	}
	
	


	public Mariage(Date benedNuptDate, String benedNuptLieu, Date dateBaptEpoux, Date dateMariage,
			String dispenseEveche, String dispenseNum, String epoux, Date formalitesDate, String formalitesNum,
			String formalitesMairie, String lieu, String numBaptEpoux, String pretre, String temoin1, String temoin2,
			String numMariage) {
		super();
		this.benedNuptDate = benedNuptDate;
		this.benedNuptLieu = benedNuptLieu;
		this.dateBaptEpoux = dateBaptEpoux;
		this.dateMariage = dateMariage;
		this.dispenseEveche = dispenseEveche;
		this.dispenseNum = dispenseNum;
		this.epoux = epoux;
		this.formalitesDate = formalitesDate;
		this.formalitesNum = formalitesNum;
		this.formalitesMairie = formalitesMairie;
		this.lieu = lieu;
		this.numBaptEpoux = numBaptEpoux;
		this.pretre = pretre;
		this.temoin1 = temoin1;
		this.temoin2 = temoin2;
		this.numMariage = numMariage;
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
	@Column(name="bened_nupt_date")
	public Date getBenedNuptDate() {
		return this.benedNuptDate;
	}

	public void setBenedNuptDate(Date benedNuptDate) {
		this.benedNuptDate = benedNuptDate;
	}


	@Column(name="bened_nupt_lieu")
	public String getBenedNuptLieu() {
		return this.benedNuptLieu;
	}

	public void setBenedNuptLieu(String benedNuptLieu) {
		this.benedNuptLieu = benedNuptLieu;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_bapt_epoux")
	public Date getDateBaptEpoux() {
		return this.dateBaptEpoux;
	}

	public void setDateBaptEpoux(Date dateBaptEpoux) {
		this.dateBaptEpoux = dateBaptEpoux;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_mariage")
	public Date getDateMariage() {
		return this.dateMariage;
	}

	public void setDateMariage(Date dateMariage) {
		this.dateMariage = dateMariage;
	}


	@Column(name="dispense_eveche")
	public String getDispenseEveche() {
		return this.dispenseEveche;
	}

	public void setDispenseEveche(String dispenseEveche) {
		this.dispenseEveche = dispenseEveche;
	}


	@Column(name="dispense_num")
	public String getDispenseNum() {
		return this.dispenseNum;
	}

	public void setDispenseNum(String dispenseNum) {
		this.dispenseNum = dispenseNum;
	}


	public String getEpoux() {
		return this.epoux;
	}

	public void setEpoux(String epoux) {
		this.epoux = epoux;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="formalites_date")
	public Date getFormalitesDate() {
		return this.formalitesDate;
	}

	public void setFormalitesDate(Date formalitesDate) {
		this.formalitesDate = formalitesDate;
	}

	@Column(name="formalites_num")
	public String getFormalitesNum() {
		return formalitesNum;
	}

	
	public void setFormalitesNum(String numFormalites) {
		this.formalitesNum = numFormalites;
	}


	@Column(name="formalites_mairie")
	public String getformalitesMairie() {
		return this.formalitesMairie;
	}


	public void setformalitesMairie(String formalitesMairie) {
		this.formalitesMairie = formalitesMairie;
	}


	public String getLieu() {
		return this.lieu;
	}

	public void setLieu(String lieu) {
		this.lieu = lieu;
	}


	@Column(name="num_bapt_epoux")
	public String getNumBaptEpoux() {
		return this.numBaptEpoux;
	}

	public void setNumBaptEpoux(String numBaptEpoux) {
		this.numBaptEpoux = numBaptEpoux;
	}


	public String getPretre() {
		return this.pretre;
	}

	public void setPretre(String pretre) {
		this.pretre = pretre;
	}


	public String getTemoin1() {
		return this.temoin1;
	}

	public void setTemoin1(String temoin1) {
		this.temoin1 = temoin1;
	}


	public String getTemoin2() {
		return this.temoin2;
	}

	public void setTemoin2(String temoin2) {
		this.temoin2 = temoin2;
	}


	//bi-directional many-to-one association to Fidele
	@ManyToOne
	public Fidele getFidele() {
		return this.fidele;
	}

	public void setFidele(Fidele fidele) {
		this.fidele = fidele;
	}

	@Column(name="num_mariage")
	public String getNumMariage() {
		return numMariage;
	}


	public void setNumMariage(String numMariage) {
		this.numMariage = numMariage;
	}
	
	

}