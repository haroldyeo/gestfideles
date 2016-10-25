package com.main.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import model.Fidele;
import model.Mariage;
import model.Sacrement;

@SuppressWarnings("unchecked")
public class FideleController  extends SelectorComposer<Component> implements EventListener<Event>{

	private static final long serialVersionUID = 1L;
	
	@Wire Div divForm, divList;
		
	@Wire Listbox listbox;

	@Wire Window winFidele;
	
	@Wire Button btnSearch, btnSave, btnSaveMod,  btnRefresh, btnAddSacrement;
	
	@Wire Textbox txtSearch;
	
	ListModelList<Fidele> lml;
	
	/*-----	Infos de base  ----*/
	@Wire Textbox txtNomF, txtPrenomsF, txtNomPere, txtlieuNaissance, 
				  txtOriginePere, txtNomMere, txtOrigineMere, txtNomParrain, txtNomMarraine;
	@Wire Datebox txtDateNaissance, dateDob;
	String nom, prenoms, lieuNaissance, nomPere, originePere, nomMere, origineMere, nomParain, nomMarraine;
	Date dob;
	
	/*-----	Bapteme  ----*/
	@Wire Textbox txtNumeroBapt, txtDiocese, txtEglise, txtPretreBapteme;
	@Wire Datebox dateBapt;
	String diocese, eglise, numero, pretre;
	Date dateBapteme;
	
	/*-----	Sacr�ments  ----*/
	@Wire Rows rowsSacrement;
	@Wire Row rowTitle;
	String libelle, lieu;
	Date dateSacrement;
	List<Sacrement> listSacrement = new ArrayList<>();
	List<String> sacrementKeysInit = new ArrayList<>();
	List<String> sacrementKeysForm = new ArrayList<>();
	List<String> sacrementKeysThatRemain = new ArrayList<>();
	List<Row> listNewRowsUpdate = new ArrayList<>();
	
	/*----  Mariage  ----*/
	@Wire Textbox txtNumMariage, txtEgliseMariage, txtConjoint, txtNumBaptConjoint, txtPretreMariage, txtTemoin1, txtTemoin2;
	@Wire Datebox dateboxMariage, dateboxBaptConjoint;
	String numMariage, egliseMariage, epoux, numBaptEpoux, pretreMariage, temoin1, temoin2;
	Date dateMariage, dateBaptEpoux;
	
	/*----  B�n�diction nuptiale  ----*/
	@Wire Textbox txtEgliseBenNupt, txtDispenseBenNupt, txtEvecheBenNupt;
	@Wire Datebox dateboxBenNupt;
	String benedNuptLieu, dispenseNum, dispenseEveche;
	Date benedNuptDate;
	
	/*----  Formalit�s civiles  ----*/
	@Wire Datebox dateboxFormCivile;
	@Wire Textbox txtNumFormCivile, txtMairie;
	Date formalitesDate;
	String numFormalites, formalitesMairie;
	
	/*----  Enfants  ----*/
	@Wire Rows rowsEnfants;
	@Wire Button btnAddEnfant;
	String nomEnfant;
	Date dateNaissanceEnfant;
	List<Sacrement> listEnfants = new ArrayList<>();
	List<String> enfantsKeysInit = new ArrayList<>();
	List<String> enfantsKeysForm = new ArrayList<>();
	List<String> enfantsKeysThatRemain = new ArrayList<>();
	List<Row> listNewEnfantsRowsUpdate = new ArrayList<>();
	
	/*----  Sacr�ments malades  ----*/
	@Wire Rows rowsSacrementMalades;
	@Wire Button btnAddMalade;
	String nomMalade;
	Date dateSacrementMalade;
	List<Sacrement> listSacrementsMalade = new ArrayList<>();
	List<String> maladesKeysInit = new ArrayList<>();
	List<String> maladesKeysForm = new ArrayList<>();
	List<String> maladesKeysThatRemain = new ArrayList<>();
	List<Row> listNewMaladesRowsUpdate = new ArrayList<>();
	
	@Override
    public void doAfterCompose(Component comp) throws Exception {
    	super.doAfterCompose(comp);
    	
    	if(comp.getId().equals("winFidele")){
    		displayList(null);
    		comp.addEventListener("onAddSacrement", this);
    		comp.addEventListener("onAddEnfant", this);
    		Utils.setSessionAttribute("winFidele", winFidele);
    	}
    		
	}
	
	
	/*------------------- BASIC STUFF ****************************/
	
	private void displayList(List<Fidele> list) {
		if(list == null)
			list = OperationsDb.find(Constants.fideles, null);
		lml = new ListModelList<Fidele>(list);
		SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
		for(Fidele fid : list){
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
	}
	
	

	@Listen("onClick=#menuUpdate")
	public void onUpdate() throws Exception{
		
		if(listbox.getSelectedItem() == null){
			Messagebox.show("Veuillez s�lectionner un �lement de la liste", "Modifier un fid�le", Messagebox.OK, Messagebox.EXCLAMATION);
		} else{
			Fidele selected = ((Fidele)listbox.getSelectedItem().getValue());
			
			undoReadOnly();
			doOnUpdateVisibility();
			
			
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
			listSacrement.clear();
			refreshRowsSacrements();
			List<Sacrement> listSacres = selected.getSacrements();
			
			sacrementKeysInit = new ArrayList<>();
			
			// obtenir liste des sacrements initiaux
			for(Sacrement s : listSacres){
				rowsSacrement.appendChild(Utils.buildSacrements(s));
				sacrementKeysInit.add(s.getId().toString());
			}
			
			// mariage
			Mariage m = selected.getMariages().size() == 1 ? selected.getMariages().get(0) : null;
			txtNumMariage.setValue(m.getNumMariage()); 
			txtEgliseMariage.setValue(m.getLieu());
			txtConjoint.setValue(m.getEpoux());
			txtNumBaptConjoint.setValue(m.getNumBaptEpoux());
			txtPretreMariage.setValue(m.getPretre());
			txtTemoin1.setValue(m.getTemoin1());
			txtTemoin2.setValue(m.getTemoin2());
			dateboxMariage.setValue(m.getDateMariage());
			dateboxBaptConjoint.setValue(m.getDateBaptEpoux());
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
			Messagebox.show("Veuillez s�lectionner un �lement de la liste", "Cr�er un fid�le", Messagebox.OK, Messagebox.EXCLAMATION);
		} else{
			Messagebox.show("�tes vous s�rs de vouloir supprimer?", "Supprimer un fid�le", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
			    public void onEvent(Event evt) throws InterruptedException {
			        if (evt.getName().equals("onYes")) {
			        	Fidele entity = (Fidele)listbox.getSelectedItem().getValue();
						OperationsDb.deleteById(Fidele.class, entity.getId());
						Messagebox.show("Fid�le supprim� avec succ�s", "Supprimer un fid�le", Messagebox.OK, Messagebox.EXCLAMATION);
						displayList(null);
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
		displayList(null);
		
	}

	
	/*--------------- SAVE  -  UPDATE   ************************/
	
	@Listen("onClick=#btnSave")
	public void save() throws Exception{
		
			try{				
				assignValues(Constants.modeSave);
				
				//create new
				if(errorCheck()){
					Fidele fid = new Fidele(dob, lieuNaissance, nom, nomMarraine, nomMere, nomParain, nomPere, origineMere, originePere, prenoms);
					// Bapt�me
					Bapteme bapt = new Bapteme(dateBapteme, diocese, eglise, numero, pretre, fid);
					fid.addBapteme(bapt);
					
					// Sacr�ment
					for(Sacrement s : listSacrement){
						fid.addSacrement(s);
					}
					
					// Mariage
					Mariage m = new Mariage(benedNuptDate, benedNuptLieu, dateBaptEpoux, dateMariage, 
							dispenseEveche, dispenseNum, epoux, formalitesDate, numFormalites, formalitesMairie, 
							lieu, numBaptEpoux, pretre, temoin1, temoin2, numMariage);
					fid.addMariage(m);
					
					// Persist
					OperationsDb.persistObject(fid);
					
					Messagebox.show("Fid�le enregistr� avec succ�s", "Cr�er un fid�le", Messagebox.OK, Messagebox.INFORMATION);
					refreshForm();
				}	
				
			} catch(Exception e){
				e.printStackTrace();
			}
			
	}
	
	
	@Listen("onClick=#btnSaveMod")
	public void update() throws ParseException{
		
		assignValues(Constants.modeUpdate);
		
		if(errorCheck()){
			
			Fidele p = (Fidele)listbox.getSelectedItem().getValue();
			
			p.setNom(nom);
			p.setPrenoms(prenoms);
			p.setDob(dob);
			p.setLieuNaissance(lieuNaissance);
			p.setNomPere(nomPere);
			p.setNomMere(nomMere);
			p.setOriginePere(originePere);
			p.setOrigineMere(origineMere);
			p.setNomParrain(nomParain);
			p.setNomMarraine(nomMarraine);
			if(!listSacrement.isEmpty()){
				for(Sacrement s : listSacrement){
					p.addSacrement(s);
				}
			}
			OperationsDb.updateObject(p);
			
			Messagebox.show("Fid�le mis � jour avec succ�s", "Cr�er un fid�le", Messagebox.OK, Messagebox.INFORMATION);
		}
	}
	
	/*-------------    SACREMENTS   *****************************/

	
	@Listen("onClick=#btnAddSacrement")
	public void onAddSacrement() throws Exception{
		Utils.openModal("/references/sacrementForm.zul", null, null, "Ajouter un sacr�ment");
	}
	
	private void doFillSacrementsForm(List<Cell> listCells) {
		
		for(Cell cell : listCells){
			if(cell.getFirstChild() instanceof Button){
				Button b = (Button) cell.getFirstChild();
				if(b.getAttribute("idSacre") != null){
					sacrementKeysForm.add((String) b.getAttribute("idSacre"));
				} else{
					listNewRowsUpdate.add((Row) cell.getParent());
				}
			}
		}
	}

	private void createNewSacrements(Row row) {
		
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
	
	@Override
	public void onEvent(Event event) throws Exception {
		if(event.getName().equalsIgnoreCase("onAddSacrement")){
			Row row = (Row) event.getData();
			rowsSacrement.appendChild(row);
		} 
		
		if(event.getName().equalsIgnoreCase("onAddEnfant")){
			Row row = (Row) event.getData();
			rowsEnfants.appendChild(row);
		} 
	}
	
	/*-------------    ENFANTS   *****************************/

	@Listen("onClick=#btnAddEnfant")
	public void onAddEnfant() throws Exception{
		Utils.openModal("/references/enfantForm.zul", null, null, "Ajouter un enfant");
	}
	
	private void doFillEnfantsForm(List<Cell> listCells) {
		
		for(Cell cell : listCells){
			if(cell.getFirstChild() instanceof Button){
				Button b = (Button) cell.getFirstChild();
				if(b.getAttribute("idEnfant") != null){
					enfantsKeysForm.add((String) b.getAttribute("idEnfant"));
				} else{
					listNewEnfantsRowsUpdate.add((Row) cell.getParent());
				}
			}
		}
		
	}


	private void createNewEnfants(Row row) {
		
		List<String> list = new ArrayList<String>();
		for(Component cell : row.getChildren()){
			if(cell.getFirstChild() instanceof Label){
				Label l = (Label) cell.getFirstChild();
				list.add(l.getValue()); 
				// ajout dans l'ordre de libelle, date, lieu
			}
		}
	
		
	}
	
	
	/*-------------    UTILS   *****************************/
	
	
	public boolean errorCheck(){
		
		boolean bool = true;
				
		String[] abc = new String[]{nom, lieuNaissance, nomPere, originePere, nomMere, origineMere,   
									diocese, eglise, numero, pretre};
		
		
		if(Utils.isEmptyCheck(abc)){
			bool = false;
			Messagebox.show("Veuillez saisir les champs obligatoires", "Cr�er un fid�le", Messagebox.OK, Messagebox.INFORMATION);
		}
		
		return bool;
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
				pretre = txtPretreBapteme.getValue();
				
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
				
				// b�n�diction nuptiale
				benedNuptDate = dateboxBenNupt.getValue();
				benedNuptLieu = txtEgliseBenNupt.getValue();
				dispenseNum = txtDispenseBenNupt.getValue();
				dispenseEveche = txtEvecheBenNupt.getValue();
				
				// Formalit�s civiles
				formalitesDate = dateboxFormCivile.getValue();
				numFormalites =txtNumFormCivile.getValue();
				formalitesMairie = txtMairie.getValue();
				
				
				
				// sacr�ments
					
					// 	get rows
					List<Row> listrowSacrements = rowsSacrement.getChildren();
					if(mode.equals(Constants.modeUpdate)){
						
							// obtenir liste des sacrements du form et ajout des rows des new sacrements
							listNewRowsUpdate.clear();
							sacrementKeysForm.clear();
							for(Row row : listrowSacrements){
								if(!row.getId().equals("rowTitle")){
									List<Cell> listCells = row.getChildren();
									doFillSacrementsForm(listCells);
								}
							}
							
							// liste des sacrements inchang�s
							//==> comparaison des deux listes
							
							sacrementKeysThatRemain.clear();
							for(String sInit : sacrementKeysInit){
								for(String sForm : sacrementKeysForm){
									if(sInit.equals(sForm)){
										// ajouter � la liste des �l�ments qui restent
										sacrementKeysThatRemain.add(sInit);
									}
								}
							}
							
							
							// liste des sacrements to delete -----> on les supprime right away
							for(String sInit : sacrementKeysInit){
								if(!sacrementKeysThatRemain.contains(sInit)){
									OperationsDb.deleteById(Sacrement.class, Integer.parseInt(sInit));
								}
							}
							
							
							// liste des rows new sacrements -------> on les cr�e right away
							for(Row row : listNewRowsUpdate){
								createNewSacrements(row);
							}
							
					} // end mode update
							
					
					if(mode.equals(Constants.modeSave)){
						// create new sacrements
						for(Row row : listrowSacrements){
							if(!row.getId().equals("rowTitle")){
								createNewSacrements(row);
							}
						}
					} // end mode save
					
					
					
				// enfants
				List<Row> listRowsEnfants = rowsEnfants.getChildren();
				
				if(mode.equals(Constants.modeSave)){
					// create new enfants
					for(Row row : listRowsEnfants){
						if(!row.getId().equals("rowTitleEnfant")){
							createNewEnfants(row);
						}
					}
				} // end mode save
				
				if(mode.equals(Constants.modeUpdate)){
					// obtenir liste des enfants du form et ajout des rows des new enfants
					listNewEnfantsRowsUpdate.clear();
					enfantsKeysForm.clear();
					for(Row row : listRowsEnfants){
						if(!row.getId().equals("rowTitleEnfant")){
							List<Cell> listCells = row.getChildren();
							doFillEnfantsForm(listCells);
						}
					}
				}
				
							
	}


	@Listen("onClick=#btnRefreshForm")
	public void refreshForm() {
		
		Textbox[] textBoxes = new Textbox[]{txtNomF, txtPrenomsF, txtlieuNaissance, txtNomPere, txtNomMere, txtOriginePere,
				txtOrigineMere, txtNomParrain, txtNomMarraine,  txtNumeroBapt, txtDiocese, txtEglise, txtPretreBapteme,
				txtNumMariage, txtEgliseMariage, txtConjoint, txtNumBaptConjoint, txtPretreMariage, txtTemoin1, txtTemoin2,
				txtEgliseBenNupt, txtDispenseBenNupt, txtEvecheBenNupt};
		Utils.clearComponents(textBoxes);
		
		Datebox[] dateBoxes = {dateDob, dateBapt, dateboxMariage, dateboxBenNupt};
		Utils.clearComponents(dateBoxes);
		
		refreshRowsSacrements();
		
		
	}

	private void refreshRowsSacrements() {
		// refresh sacrements
			if(rowTitle.getNextSibling()!= null){
				while(rowTitle.getNextSibling()!= null){
					rowTitle.getNextSibling().detach();
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

		for(Component comp : rowsSacrement.getChildren()){
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
		
		for(Component comp : rowsSacrement.getChildren()){
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


	
	public void doOnAddVisibility() {
		divList.setVisible(false);
		divForm.setVisible(true);
		btnSave.setVisible(true);
		btnSaveMod.setVisible(false);
		
	}
	
	private void doOnUpdateVisibility() {
		divList.setVisible(false);
		divForm.setVisible(true);
		btnSave.setVisible(false);
		btnSaveMod.setVisible(true);
	}
	
	
}
