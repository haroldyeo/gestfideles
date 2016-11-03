package com.main.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.utils.Constants;
import com.utils.OperationsDb;
import com.utils.Utils;

import model.Bapteme;
import model.Enfant;
import model.Fidele;
import model.Mariage;
import model.Sacrement;
import model.SacrementMalades;

@SuppressWarnings("unchecked")
public class FideleController  extends SelectorComposer<Component> implements EventListener<Event>{

	private static final long serialVersionUID = 1L;
	
	@Wire Div divForm, divList;
		
	@Wire Listbox listbox;

	@Wire Window winFidele;
	
	@Wire Button btnSearch, btnSave, btnSaveMod,  btnRefresh, btnAddSacrement, btnRefreshForm;
	
	@Wire Textbox txtSearch;
	
	ListModelList<Fidele> lml;
	
	
	/*-----	Init  ----*/
	Textbox[] textBoxes = null;
	Datebox[] dateBoxes = null;
	Row[] rowTitles = null;
	Rows[] rowsTitles = null;
	
	/*-----	Infos de base  ----*/
	@Wire Textbox txtNomF, txtPrenomsF, txtNomPere, txtlieuNaissance, 
				  txtOriginePere, txtNomMere, txtOrigineMere, txtNomParrain, txtNomMarraine;
	@Wire Datebox txtDateNaissance, dateDob;
	String nom, prenoms, lieuNaissance, nomPere, originePere, nomMere, origineMere, nomParain, nomMarraine;
	Date dob;
	
	/*-----	Bapteme  ----*/
	@Wire Textbox txtNumeroBapt, txtDiocese, txtEglise, txtPretreBapteme;
	@Wire Datebox dateBapt;
	String diocese, eglise, numero, pretreBapteme;
	Date dateBapteme;
	
	/*-----	Sacréments  ----*/
	@Wire Rows rowsSacrement;
	@Wire Row rowTitleSacrement,rowInnerTitleSacrement;
	String libelle, lieu;
	Date dateSacrement;
	List<Sacrement> listSacrement = new ArrayList<>();
	List<String> sacrementKeysInit = new ArrayList<>();
	List<String> sacrementKeysForm = new ArrayList<>();
	List<String> sacrementKeysThatRemain = new ArrayList<>();
	List<String> sacrementKeysToDelete = new ArrayList<>();
	List<Row> listNewRowsUpdate = new ArrayList<>();
	
	/*----  Mariage  ----*/
	@Wire Textbox txtNumMariage, txtEgliseMariage, txtConjoint, txtNumBaptConjoint, txtPretreMariage, txtTemoin1, txtTemoin2;
	@Wire Datebox dateboxMariage, dateboxBaptConjoint;
	String numMariage, egliseMariage, epoux, numBaptEpoux, pretreMariage, temoin1, temoin2;
	Date dateMariage, dateBaptEpoux;
	
	/*----  Bénédiction nuptiale  ----*/
	@Wire Textbox txtEgliseBenNupt, txtDispenseBenNupt, txtEvecheBenNupt;
	@Wire Datebox dateboxBenNupt;
	String benedNuptLieu, dispenseNum, benedNuptEveche;
	Date benedNuptDate;
	
	/*----  Formalités civiles  ----*/
	@Wire Datebox dateboxFormCivile;
	@Wire Textbox txtNumFormCivile, txtMairie;
	Date formalitesDate;
	String formalitesNum, formalitesMairie;
	
	/*----  Enfants  ----*/
	@Wire Rows rowsEnfants;
	@Wire Button btnAddEnfant;
	@Wire Row rowTitleEnfant, rowInnerTitleEnfants;
	String nomEnfant, numBaptemeEnfant, lieuBaptemeEnfant;
	Date dateNaissanceEnfant, dateBaptemeEnfant;
	List<Enfant> listEnfants = new ArrayList<>();
	List<String> enfantsKeysInit = new ArrayList<>();
	List<String> enfantsKeysForm = new ArrayList<>();
	List<String> enfantsKeysThatRemain = new ArrayList<>();
	List<String> enfantsKeysToDelete = new ArrayList<>();
	List<Row> listNewEnfantsRowsUpdate = new ArrayList<>();
	
	/*----  Sacréments malades  ----*/
	@Wire Rows rowsSacrementMalades;
	@Wire Button btnAddMalade;
	@Wire Row rowTitleMalade, rowInnerTitleSacreMalade;
	String lieuSacrementMalade;
	Date dateSacrementMalade;
	List<SacrementMalades> listSacrementsMalade = new ArrayList<>();
	List<String> maladesKeysInit = new ArrayList<>();
	List<String> maladesKeysForm = new ArrayList<>();
	List<String> maladesKeysThatRemain = new ArrayList<>();
	List<String> maladesKeysToDelete = new ArrayList<>();
	List<Row> listNewMaladesRowsUpdate = new ArrayList<>();
	
	@Override
    public void doAfterCompose(Component comp) throws Exception {
    	super.doAfterCompose(comp);
    	
    	if(comp.getId().equals("winFidele")){
    		displayList(null);
    		comp.addEventListener(Constants.events_sacrement, this);
    		comp.addEventListener(Constants.events_enfant, this);
    		comp.addEventListener(Constants.events_sacrementMalade, this);
    		Utils.setSessionAttribute("winFidele", winFidele);
    	}
    	
    	initLists();
	}
	
	
	/*------------------- BASIC STUFF ****************************/
	
	private void initLists(){
		textBoxes = new Textbox[]{txtNomF, txtPrenomsF, txtlieuNaissance, txtNomPere, txtNomMere, txtOriginePere,
				txtOrigineMere, txtNomParrain, txtNomMarraine,  txtNumeroBapt, txtDiocese, txtEglise, txtPretreBapteme,
				txtNumMariage, txtEgliseMariage, txtConjoint, txtNumBaptConjoint, txtPretreMariage, txtTemoin1, txtTemoin2,
				txtEgliseBenNupt, txtDispenseBenNupt, txtEvecheBenNupt,
				txtNumFormCivile, txtMairie};
		
		dateBoxes = new Datebox[]{dateDob, dateBapt, dateboxMariage, dateboxBenNupt, dateboxFormCivile, dateboxBaptConjoint};
		
		rowTitles = new Row[]{rowInnerTitleSacrement, rowInnerTitleSacreMalade, rowInnerTitleEnfants};
		
		rowsTitles = new Rows[]{rowsSacrement, rowsEnfants, rowsSacrementMalades};
	}
	
	private void displayList(List<Fidele> list) {
		if(list == null)
			list = OperationsDb.find(Constants.fideles, null);
		lml = new ListModelList<Fidele>(list);
		SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
		for(Fidele fid : list){
			if(fid.getDob() != null)
				fid.set_dob(sm.format(fid.getDob()));
		}
		
    	listbox.setModel(lml);
	}

	@Listen("onClick=#btnSearch")
	public void doSearch(){
		if(txtSearch.getValue().isEmpty()){
			// don't do anything
		} else {			
			List<Fidele> list = OperationsDb.doSearch(Constants.fideles, txtSearch.getValue());
	    	displayList(list);
		
		}
	}
	
	@Listen("onClick=#btnRefresh")
	public void doRefresh(){
		txtSearch.setValue("");
		List<Fidele> list = OperationsDb.find(Constants.fideles, null);
    	displayList(list);
	}
	
	/*--------------- ADD - UPDATE - DELETE - DETAILS ************************/
	
	@Listen("onClick=#menuAdd")
	public void onAdd(){
		refreshForm();
		undoReadOnly();
		doOnAddVisibility();
		listSacrement.clear();
		listSacrementsMalade.clear();
		listEnfants.clear();
	}
	
	

	@Listen("onClick=#menuUpdate")
	public void onUpdate() throws Exception{
		
		if(listbox.getSelectedItem() == null){
			Messagebox.show("Veuillez sélectionner un élement de la liste", "Modifier un fidèle", Messagebox.OK, Messagebox.EXCLAMATION);
		} else{
			
			listSacrement.clear();
			listSacrementsMalade.clear();
			listEnfants.clear();
			
			Fidele selected = ((Fidele)listbox.getSelectedItem().getValue());
			
			undoReadOnly();
			doOnUpdateVisibility();
			refreshForm();
			
			
			// infos de base
			txtNomF.setValue(selected.getNom());
			txtPrenomsF.setValue(selected.getPrenoms());
			dateDob.setValue(selected.getDob());
			txtNomPere.setValue(selected.getNomPere());
			txtlieuNaissance.setValue(selected.getLieuNaissance() );
			txtOriginePere.setValue(selected.getOriginePere()); 
			txtNomMere.setValue(selected.getNomMere() );
			txtOrigineMere.setValue(selected.getOrigineMere());
			txtNomParrain.setValue(selected.getNomParrain());
			txtNomMarraine.setValue(selected.getNomMarraine());
			
			
			//bapteme
			Bapteme bapt = selected.getBaptemes().size() == 1 ?  selected.getBaptemes().get(0) : null;
			if(bapt != null){
				txtNumeroBapt.setValue(bapt.getNumero());
				dateBapt.setValue(bapt.getDateBapteme());
				dateBapt.setFormat("dd/MM/yyyy");
				txtDiocese.setValue(bapt.getDiocese());
				txtEglise.setValue(bapt.getEglise());
				txtPretreBapteme.setValue(bapt.getPretre());
			}
			
			// sacrements
//			listSacrement.clear();
			List<Sacrement> listSacres = selected.getSacrements();
			
			sacrementKeysInit = new ArrayList<>();
			
			if(listSacres.size() > 0){
				// obtenir liste des sacrements initiaux
				for(Sacrement s : listSacres){
					rowsSacrement.appendChild(Utils.buildSacrements(s));
					sacrementKeysInit.add(s.getId().toString());
				}
			}
			
			// enfants
			List<Enfant> listenf = selected.getEnfants();  
			if(listenf.size() > 0){
				for(Enfant e : listenf){
					rowsEnfants.appendChild(Utils.buildEnfants(e));
					enfantsKeysInit.add(e.getId().toString());
				}
			}
			
			
			// sacrements malade
			List<SacrementMalades> listsacreMalades = selected.getSacrementMalades();
			if(listsacreMalades.size() > 0){
				for(SacrementMalades sm : listsacreMalades){
					rowsSacrementMalades.appendChild(Utils.buildSacreMalade(sm));
					maladesKeysInit.add(sm.getId().toString());
				}
			}
			
			
			// mariage
			Mariage m = selected.getMariages().size() == 1 ? selected.getMariages().get(0) : null;
			if(m!=null){
				txtNumMariage.setValue(m.getNumMariage()); 
				txtEgliseMariage.setValue(m.getLieu());
				txtConjoint.setValue(m.getEpoux());
				txtNumBaptConjoint.setValue(m.getNumBaptEpoux());
				txtPretreMariage.setValue(m.getPretre());
				txtTemoin1.setValue(m.getTemoin1());
				txtTemoin2.setValue(m.getTemoin2());
				dateboxMariage.setValue(m.getDateMariage());
				dateboxBaptConjoint.setValue(m.getDateBaptEpoux());
				
				// bénédiction nuptiale
				dateboxBenNupt.setValue(m.getBenedNuptDate());
				txtEgliseBenNupt.setValue(m.getBenedNuptLieu());
				txtDispenseBenNupt.setValue(m.getDispenseNum());
				txtEvecheBenNupt.setValue(m.getDispenseEveche());
				
				// Formalités civiles
				dateboxFormCivile.setValue(m.getFormalitesDate());
				txtNumFormCivile.setValue(m.getFormalitesNum());
				txtMairie.setValue(m.getformalitesMairie());
			}
			
		}// fin du else
		
	}
	


	@Listen("onClick=#menuDetails")
	public void onDetails() throws Exception{
		
		onUpdate();
		doReadOnly();
		
	}
	
	@SuppressWarnings("rawtypes")
	@Listen("onClick=#menuDelete")
	public void onDelete() throws Exception{
		if(listbox.getSelectedItem() == null){
			Messagebox.show("Veuillez sélectionner un élement de la liste", "Créer un fidèle", Messagebox.OK, Messagebox.EXCLAMATION);
		} else{
			Messagebox.show("Êtes vous sûrs de vouloir supprimer?", "Supprimer un fidèle", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
			    public void onEvent(Event evt) throws InterruptedException {
			        if (evt.getName().equals("onYes")) {
			        	try {
			        		Fidele entity = (Fidele)listbox.getSelectedItem().getValue();
							OperationsDb.deleteById(Fidele.class, entity.getId());
							Messagebox.show("Fidèle supprimé avec succès", "Supprimer un fidèle", Messagebox.OK, Messagebox.EXCLAMATION);
							displayList(null);
						} catch (Exception e) {
							e.printStackTrace();
						}
			        	
			        } else if (evt.getName().equals("onNo")) {
			            // nada
			        } 
			    }
		});
	  }
		
	}
	
	@Listen("onClick=#menuBack")
	public void onBack(){
		divForm.setVisible(false);
		divList.setVisible(true);
		btnSearch.setVisible(true);
		btnRefresh.setVisible(true);
		txtSearch.setReadonly(false);
		txtSearch.setValue("");
		displayList(null);
		
	}

	
	/*--------------- SAVE  -  UPDATE   ************************/
	
	@Listen("onClick=#btnSave")
	public void save() throws Exception{
		
			try{
				   errorCheck();
					//create new
					assignValues(Constants.modeSave);
					
					// Persist
					OperationsDb.persistObject(doFillFideleEntity(Constants.modeSave));
					Messagebox.show("Fidèle enregistré avec succès", Constants.popoup_title_create_fideles, Messagebox.OK, Messagebox.INFORMATION);
					refreshForm();
				
			} catch(Exception e){
				e.printStackTrace();
//				Messagebox.show("Une erreur est survenue", Constants.popoup_title_create_fideles, Messagebox.OK, Messagebox.ERROR);
			}
			
	}
	
	
	@Listen("onClick=#btnSaveMod")
	public void update() throws ParseException{
		
		try{
			errorCheck();
			assignValues(Constants.modeUpdate);		
			OperationsDb.updateObject(doFillFideleEntity(Constants.modeUpdate));
			Messagebox.show("Fidèle mis à jour avec succès", Constants.popoup_title_create_fideles, Messagebox.OK, Messagebox.INFORMATION);
			
		} catch(Exception e){
			e.printStackTrace();
//			Messagebox.show("Une erreur est survenue", Constants.popoup_title_create_fideles, Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	/*-------------    SACREMENTS   *****************************/

	
	@Listen("onClick=#btnAddSacrement")
	public void onAddSacrement() throws Exception{
		Utils.openModal("/references/sacrementForm.zul", null, null, "Ajouter un sacrément");
	}
	
	private void preCreateNewSacrements(Row row) {
		List<String> list = new ArrayList<String>();
		for(Component cell : row.getChildren()){
			if(cell.getFirstChild() instanceof Label){
				Label l = (Label) cell.getFirstChild();
				list.add(l.getValue()); 
				// ajout dans l'ordre de libelle, date, lieu
			}
		}
		
		if(list.size() == 3){
			libelle = list.get(0);
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			try {
				dateSacrement = formatter.parse(list.get(1));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			lieu = list.get(2);
			Sacrement sacr = new Sacrement(dateSacrement, libelle, lieu, null);
			listSacrement.add(sacr);
		}
		
	}
	
	/*-------------    ENFANTS   *****************************/

	@Listen("onClick=#btnAddEnfant")
	public void onAddEnfant() throws Exception{
		Utils.openModal("/references/enfantForm.zul", null, null, "Ajouter un enfant");
	}


	private void preCreateNewEnfants(Row row) {
		List<String> list = new ArrayList<String>();
		for(Component cell : row.getChildren()){
			if(cell.getFirstChild() instanceof Label){
				Label l = (Label) cell.getFirstChild();
				list.add(l.getValue()); 
				// ajout dans l'ordre: nom, dob, date bapt, num bapt, lieu bapt
				
				if(list.size() == 5){
					nomEnfant = list.get(0);
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					try {
						dateNaissanceEnfant = formatter.parse(list.get(1));
						dateBaptemeEnfant = formatter.parse(list.get(2));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					numBaptemeEnfant = list.get(3);
					lieuBaptemeEnfant = list.get(4);
					Enfant e = new Enfant(dateBaptemeEnfant, dateNaissanceEnfant, lieuBaptemeEnfant, nomEnfant, numBaptemeEnfant);
					listEnfants.add(e);
				}
			}
		}
	
		
	}
	
	
	/*-------------    SACREMENTS MALADE   *****************************/

	@Listen("onClick=#btnAddMalade")
	public void onAddMalade() throws Exception{
		Utils.openModal("/references/sacreMaladeForm.zul", null, null, "Ajouter un sacrément des malades");
	}


	private void preCreateNewSacreMalade(Row row) {
		List<String> list = new ArrayList<String>();
		List<Component> lcomp = row.getChildren();
		for(Component cell : lcomp){
			if(cell.getFirstChild() instanceof Label){
				Label l = (Label) cell.getFirstChild();
				list.add(l.getValue()); 
				// ajout dans l'ordre: date, lieu
				
				if(list.size() == 2){
					
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					try {
						dateSacrementMalade = formatter.parse(list.get(0));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					lieuSacrementMalade = list.get(1);
					SacrementMalades sm = new SacrementMalades(dateSacrementMalade, lieuSacrementMalade);
					listSacrementsMalade.add(sm);
				}
			}
		}
	
		
	}
	
	
	/*-------------    UTILS   *****************************/
	
	
	
	@Override
	public void onEvent(Event event) throws Exception {
		if(event.getName().equalsIgnoreCase(Constants.events_sacrement)){
			Row row = (Row) event.getData();
			rowsSacrement.appendChild(row);
		} 
		
		if(event.getName().equalsIgnoreCase(Constants.events_enfant)){
			Row row = (Row) event.getData();
			rowsEnfants.appendChild(row);
		} 
		
		if(event.getName().equalsIgnoreCase(Constants.events_sacrementMalade)){
			Row row = (Row) event.getData();
			rowsSacrementMalades.appendChild(row);
		} 
	}
	
	
	private Fidele doFillFideleEntity(String mode) {
		
		Fidele fid = null;
		
		if(mode.equals(Constants.modeSave)){
			
			// infos base
			fid = new Fidele(dob, lieuNaissance, nom, nomMarraine, nomMere, nomParain, nomPere, origineMere, originePere, prenoms);
			
			// bapteme
			Bapteme b = new Bapteme(dateBapteme, diocese, eglise, numero, pretreBapteme);
			fid.addBapteme(b);
			
			// mariage
			if(!numMariage.equals("") || !dispenseNum.equals("") || !formalitesNum.equals("")){
				Mariage m = new Mariage(benedNuptDate, benedNuptLieu, dateBaptEpoux, dateMariage, 
						benedNuptEveche, dispenseNum, epoux, formalitesDate, formalitesNum, formalitesMairie,
						lieu, numBaptEpoux, pretreMariage, temoin1, temoin2, numMariage);
				fid.addMariage(m);
			}
			
			
			
		} else if(mode.equals(Constants.modeUpdate)){
			fid = (Fidele)listbox.getSelectedItem().getValue();
			
			// infos base
			fid.setNom(nom);
			fid.setPrenoms(prenoms);
			fid.setDob(dob);
			fid.setLieuNaissance(lieuNaissance);
			fid.setNomPere(nomPere);
			fid.setNomMere(nomMere);
			fid.setOriginePere(originePere);
			fid.setOrigineMere(origineMere);
			fid.setNomParrain(nomParain);
			fid.setNomMarraine(nomMarraine);
			
			
		
			// bapteme			
			Bapteme b = fid.getBaptemes().size() == 1 ? fid.getBaptemes().get(0) : null;
			if(b != null){
				b.setDateBapteme(dateBapteme);
				b.setDiocese(diocese);
				b.setEglise(eglise);
				b.setNumero(numero);
				b.setPretre(pretreBapteme);
			}
			
			
			// mariage
			Mariage m = fid.getMariages().size() == 1 ?  fid.getMariages().get(0) : null;		
			
			if(m != null){
				m.setBenedNuptDate(benedNuptDate);
				m.setBenedNuptLieu(benedNuptLieu);
				m.setDateBaptEpoux(dateBaptEpoux);
				m.setDateMariage(dateMariage);
				
				m.setDispenseEveche(benedNuptEveche);
				m.setDispenseNum(dispenseNum);
				m.setEpoux(epoux);
				m.setFormalitesDate(formalitesDate);
				m.setformalitesMairie(formalitesMairie);
				
				m.setLieu(lieu);
				m.setNumBaptEpoux(numBaptEpoux);
				m.setPretre(pretreMariage);
				m.setTemoin1(temoin1);
				m.setTemoin2(temoin2);
				m.setNumMariage(numMariage);
				
			}
		
		} // fin modeUpdate
		
		
		// sacrements
		
		List<Sacrement> sacrementsFid = fid.getSacrements();
		if(sacrementsFid!= null && !sacrementsFid.isEmpty() && !sacrementKeysToDelete.isEmpty()){
			for(Iterator<Sacrement> it = sacrementsFid.iterator(); it.hasNext();){
				Sacrement s = it.next();
				if(sacrementKeysToDelete.contains(s.getId().toString())){
					it.remove();
				}
			}
		}
		
		if(!listSacrement.isEmpty()){
			for(Sacrement s : listSacrement){
				fid.addSacrement(s);
			}
		}
		
		
		// enfants
		List<Enfant> enfantsFid = fid.getEnfants();
		if(enfantsFid!= null && !enfantsFid.isEmpty() && !enfantsKeysToDelete.isEmpty()){
			for(Iterator<Enfant> it = enfantsFid.iterator(); it.hasNext();){
				Enfant e = it.next();
				if(enfantsKeysToDelete.contains(e.getId().toString())){
					it.remove();
				}
			}
		}
		if(!listEnfants.isEmpty()){
			for(Enfant f : listEnfants){
				fid.addEnfant(f);
			}
		}
		
		// sacrements malades
		List<SacrementMalades> maladesFid = fid.getSacrementMalades();
		if(maladesFid!= null && !maladesFid.isEmpty() && !maladesKeysToDelete.isEmpty()){
			for(Iterator<SacrementMalades> it = maladesFid.iterator(); it.hasNext();){
				SacrementMalades e = it.next();
				if(maladesKeysToDelete.contains(e.getId().toString())){
					it.remove();
				}
			}
		}
		if(!listSacrementsMalade.isEmpty()){
			for(SacrementMalades sm : listSacrementsMalade){
				fid.addSacrementMalades(sm);
			}
		}
		
		return fid;
	}
	
	private void doFillFormValues(List<Cell> listCells, String entity) {
		
		String id = null;
		List<String> keysForm = null;
		List<Row> listNewRows = null;
		
		switch (entity) {
		case Constants.sacrements:
			id = "idSacre";
			keysForm = sacrementKeysForm;
			listNewRows = listNewRowsUpdate;
			break;
		case Constants.enfants:
			id = "idEnfant";
			keysForm = enfantsKeysForm;
			listNewRows = listNewEnfantsRowsUpdate;
			break;
		case Constants.sacreMalades:
			id = "idSacrementMalade";
			keysForm = maladesKeysForm;
			listNewRows = listNewMaladesRowsUpdate;
			break;

		default:
			break;
		}
		
		for(Cell cell : listCells){
			if(cell.getFirstChild() instanceof Button){
				Button b = (Button) cell.getFirstChild();
				if(b.getAttribute(id) != null){
					keysForm.add((String) b.getAttribute(id));
				} else{
					listNewRows.add((Row) cell.getParent());
				}
			}
		}
	}
	
	public void errorCheck(){
		
		// check parrain et marraine
		if(txtNomParrain.getValue().isEmpty() && txtNomMarraine.getValue().isEmpty()){
			Utils.errorMessages("noParrainOrMarraine");
		}
		
		// check des champs obligatoire des infos de base
		if(Utils.checkEmptyComponents( new Component[]{txtNomF, txtPrenomsF, dateDob, txtlieuNaissance, txtNomPere, txtNomMere, txtOriginePere, txtOrigineMere})){
			Utils.errorMessages("emptyInfosBase");
		}
		
		// check des champs obligatoire de baptême
		if(Utils.checkEmptyComponents( new Component[]{dateBapt, txtDiocese, txtEglise, txtNumeroBapt, txtPretreBapteme})){
			Utils.errorMessages("emptyBapteme");
		}
		
		// check de mariage: si 1 élément d'une rubrique est saisi, alors saisir les autres éléments de la rubrique
		// ---> Mariage
		if(Utils.isNotEmptyCheck(new String[]{txtNumMariage.getText(), dateboxMariage.getText(), txtEgliseMariage.getText(), txtConjoint.getText(),
				                             dateboxBaptConjoint.getText(), txtNumBaptConjoint.getText(), txtPretreMariage.getText(),
				                             txtTemoin1.getText(), txtTemoin2.getText()})){
			if(Utils.checkEmptyComponents( new Component[]{txtNumMariage, dateboxMariage, txtEgliseMariage, txtConjoint,
											dateboxBaptConjoint, txtNumBaptConjoint, txtPretreMariage, txtTemoin1, txtTemoin2})){
				Utils.errorMessages("emptyMariage");
			}
		}
		
		// ---> Bénédiction nuptiale
		if(Utils.isNotEmptyCheck(new String[]{dateboxBenNupt.getText(), txtEgliseBenNupt.getText(), txtDispenseBenNupt.getText(), txtEvecheBenNupt.getText()})){
			if(Utils.checkEmptyComponents( new Component[]{dateboxBenNupt, txtEgliseBenNupt, txtDispenseBenNupt, txtEvecheBenNupt})){
				Utils.errorMessages("emptyBenNupt");
			}
		}
		
		// ---> Formalités civiles
		if(Utils.isNotEmptyCheck(new String[]{dateboxFormCivile.getText(), txtNumFormCivile.getText(), txtMairie.getText()})){
			if(Utils.checkEmptyComponents( new Component[]{dateboxFormCivile, txtNumFormCivile, txtMairie})){
				Utils.errorMessages("emptyFormCiviles");
			}
		}
		
		
	}

	private void assignValues(String mode) throws ParseException {
			// infos de base
				nom = txtNomF.getValue();
				prenoms = txtPrenomsF.getValue();
				dob = dateDob.getValue();
				lieuNaissance = txtlieuNaissance.getValue();
				nomPere = txtNomPere.getValue();
				nomMere = txtNomMere.getValue();
				originePere = txtOriginePere.getValue();
				origineMere = txtOrigineMere.getValue();
				nomParain = txtNomParrain.getValue();
				nomMarraine = txtNomMarraine.getValue();
				
				// bapteme
				dateBapteme = dateBapt.getValue();
				diocese = txtDiocese.getValue();
				eglise = txtEglise.getValue();
				numero = txtNumeroBapt.getValue();
				pretreBapteme = txtPretreBapteme.getValue();
				
				// mariage
				numMariage = txtNumMariage.getValue();
				egliseMariage = txtEgliseMariage.getValue();
				epoux = txtConjoint.getValue();
				numBaptEpoux = txtNumBaptConjoint.getValue();
				pretreMariage = txtPretreMariage.getValue();
				temoin1 = txtTemoin1.getValue();
				temoin2 = txtTemoin2.getValue();
				dateMariage = dateboxMariage.getValue();
				dateBaptEpoux = dateboxBaptConjoint.getValue();
				
				// bénédiction nuptiale
				benedNuptDate = dateboxBenNupt.getValue();
				benedNuptLieu = txtEgliseBenNupt.getValue();
				dispenseNum = txtDispenseBenNupt.getValue();
				benedNuptEveche = txtEvecheBenNupt.getValue();
				
				// Formalités civiles
				formalitesDate = dateboxFormCivile.getValue();
				formalitesNum =txtNumFormCivile.getValue();
				formalitesMairie = txtMairie.getValue();
				
				
				
				// sacréments

					// get rows
					List<Row> listrowSacrements = rowsSacrement.getChildren();
					if(mode.equals(Constants.modeUpdate)){
						doUpdateDynamicEntities(listrowSacrements, Constants.sacrements);							
					} // end mode update sacréments
							
					
					if(mode.equals(Constants.modeSave)){
						// create new sacrements
						for(Row row : listrowSacrements){
							if(row.getId().equals("rowTitleSacrement") || row.getId().equals("rowInnerTitleSacrement")){
								// nada
							}else{
								preCreateNewSacrements(row);
							}
						}
					} // end mode save sacréments
					
					
					
			// enfants
					
				
				// get rows
				List<Row> listRowEnfants = rowsEnfants.getChildren();
				if(mode.equals(Constants.modeUpdate)){
					doUpdateDynamicEntities(listRowEnfants,Constants.enfants);							
				} // end mode update enfants
						
				
				if(mode.equals(Constants.modeSave)){
					// create new enfants
					for(Row row : listRowEnfants){
						if(row.getId().equals("rowTitleEnfant") || row.getId().equals("rowInnerTitleEnfants")){
							// nada
						} else{
							preCreateNewEnfants(row);
						}
					}
				} // end mode save enfants
				
			
		   // sacrémentsMalades
					
				
				// get rows
				List<Row> listRowSacreMalades = rowsSacrementMalades.getChildren();
				if(mode.equals(Constants.modeUpdate)){
					doUpdateDynamicEntities(listRowSacreMalades,Constants.sacreMalades);							
				} // end mode update sacrémentsMalades
						
				
				if(mode.equals(Constants.modeSave)){
					// create new sacrémentsMalades
					for(Row row : listRowSacreMalades){
						if(row.getId().equals("rowTitleMalade") || row.getId().equals("rowInnerTitleSacreMalade")){
							// nada
						} else{
							preCreateNewSacreMalade(row);
						}
					}
				} // end mode save enfants
				
						
							
	}


	private void doUpdateDynamicEntities(List<Row> listrowSacrements, String entity ) {
		
		List<Row> listNewRows = null;
		List<String> keysInit = null ;
		List<String> keysForm = null ;
		List<String> keysThatRemain = null ;
		List<String> keysToDelete = null ;
		String rowTitle = null;
		String rowInnerTitle = null;
		
		switch (entity) { // assigner les bonnes listes en fonction de l'entité
		case Constants.sacrements:
			listNewRows = listNewRowsUpdate;
			keysInit = sacrementKeysInit;
			keysForm = sacrementKeysForm;
			keysThatRemain = sacrementKeysThatRemain;
			keysToDelete = sacrementKeysToDelete;
			rowTitle = "rowTitleSacrement";
			rowInnerTitle = "rowInnerTitleSacrement";
			break;
			
		case Constants.enfants:
			listNewRows = listNewEnfantsRowsUpdate;
			keysInit = enfantsKeysInit;
			keysForm = enfantsKeysForm;
			keysThatRemain = enfantsKeysThatRemain;
			keysToDelete = enfantsKeysToDelete;
			rowTitle = "rowTitleEnfant";
			rowInnerTitle = "rowInnerTitleEnfants";
			break;
		case Constants.sacreMalades:
			listNewRows = listNewMaladesRowsUpdate;
			keysInit = maladesKeysInit;
			keysForm =  maladesKeysForm;
			keysThatRemain =  maladesKeysThatRemain;
			keysToDelete = maladesKeysToDelete;
			rowTitle = "rowTitleMalade";
			rowInnerTitle = "rowInnerTitleSacreMalade";
			break;
		default:
			break;
		}
		
		// obtenir liste des items de l'entité choisi du form et ajout des rows des nouveaux
		listNewRows.clear();
		keysForm.clear();
		for(Row row : listrowSacrements){
			if(!row.getId().equals(rowTitle) || !row.getId().equals(rowInnerTitle)){
				List<Cell> listCells = row.getChildren();
				if(entity.equals(Constants.sacrements))
					doFillFormValues(listCells, Constants.sacrements);
				else if (entity.equals(Constants.enfants))
					doFillFormValues(listCells, Constants.enfants);
				else if (entity.equals(Constants.sacreMalades))
					doFillFormValues(listCells, Constants.sacreMalades);
			}
		}
		
		//==> comparaison des deux listes (init et form) pour déterminer ceux qui restent
		
		keysThatRemain.clear();
		for(String sInit : keysInit){
			for(String sForm : keysForm){
				if(sInit.equals(sForm)){
					// ajouter à la liste des éléments qui restent
					keysThatRemain.add(sInit);
				}
			}
		}
		
		
		// Liste des items à supprimer
		for(String sInit : keysInit){
			if(!keysThatRemain.contains(sInit)){
				keysToDelete.add(sInit);
			}
		}
		
		
		// liste des rows new items
		for(Row row : listNewRows){
			if(entity.equals(Constants.sacrements))
				preCreateNewSacrements(row);
			else if (entity.equals(Constants.enfants))
				preCreateNewEnfants(row);
			else if (entity.equals(Constants.sacreMalades))
				preCreateNewSacreMalade(row);
		}
		
	}


	@Listen("onClick=#btnRefreshForm")
	public void refreshForm() {
		Utils.clearComponents(textBoxes, dateBoxes);
		refreshRows(rowTitles);
	}

	private void refreshRows(Row[] list) {
		
		for(Row r : list){
			if(r.getNextSibling()!= null){
				while(r.getNextSibling()!= null){
					r.getNextSibling().detach();
				}
			}
		}
			
					
	}
	
	private void doReadOnly(){
		for(Component comp : divForm.getFellows()){
			if(comp instanceof Textbox){
				((Textbox) comp).setReadonly(true);
			} if (comp instanceof Datebox){
				((Datebox) comp).setDisabled(true);
			} if (comp instanceof Button){
					((Button) comp).setVisible(false);
			}
		}

		for(Rows rs : rowsTitles){
			for(Component comp : rs.getChildren()){
				if(comp instanceof Row){
					Row r = (Row)comp;

					for(Component comp2 : r.getChildren()){
						if( comp2 instanceof Cell){
							Cell c = (Cell)comp2;
							if(c.getFirstChild() instanceof Button){
								Button b = (Button) c.getFirstChild();
								b.setVisible(false);
							}
						}
					}
				}
			}
		}
		
	}
	
	
	private void undoReadOnly(){
		
		for(Component comp : divForm.getFellows()){
			if(comp instanceof Textbox){
				((Textbox) comp).setReadonly(false);
			} if (comp instanceof Datebox){
				((Datebox) comp).setDisabled(false);
			} if (comp instanceof Button){
					((Button) comp).setVisible(true);
			}
		}

		for(Rows rs : rowsTitles){
			for(Component comp : rs.getChildren()){
				if(comp instanceof Row){
					Row r = (Row)comp;
		
					for(Component comp2 : r.getChildren()){
						if( comp2 instanceof Cell){
							Cell c = (Cell)comp2;
							if(c.getFirstChild() instanceof Button){
								Button b = (Button) c.getFirstChild();
								b.setVisible(true);
							}
						}
					}
				}
			}
		}
		
		
	}


	
	public void doOnAddVisibility() {
		divList.setVisible(false);
		divForm.setVisible(true);
		btnSave.setVisible(true);
		btnSaveMod.setVisible(false);
		btnRefreshForm.setVisible(true);
		
	}
	
	private void doOnUpdateVisibility() {
		divList.setVisible(false);
		divForm.setVisible(true);
		btnSave.setVisible(false);
		btnSaveMod.setVisible(true);
		btnRefreshForm.setVisible(false);
	}
	
	
}
