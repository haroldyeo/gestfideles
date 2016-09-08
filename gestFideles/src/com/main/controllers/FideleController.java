package com.main.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import model.Sacrement;

@SuppressWarnings("unchecked")
public class FideleController  extends SelectorComposer<Component> implements EventListener<Event>{

	private static final long serialVersionUID = 1L;
	
	@Wire Div divForm, divList;
		
	@Wire Listbox listbox;

	@Wire Window winFidele;
	
	@Wire Button btnSearch, btnSave, btnSaveMod,  btnRefresh, btnAddSacrement;
	
	ListModelList<Fidele> lml;
	
	/*-----	Infos de base  ----*/
	@Wire Textbox txtNom, txtPrenoms, txtNomF, txtPrenomsF, txtNomPere, txtlieuNaissance, 
				  txtOriginePere, txtNomMere, txtOrigineMere, txtNomParrain, txtNomMarraine;
	@Wire Datebox txtDateNaissance, dateDob;
	String nom, prenoms, lieuNaissance, nomPere, originePere, nomMere, origineMere, nomParain, nomMarraine;
	Date dob;
	
	/*-----	Bapteme  ----*/
	@Wire Textbox txtNumeroBapt, txtDiocese, txtEglise, txtPretreBapteme;
	@Wire Datebox dateBapt;
	String diocese, eglise, numero, pretre;
	Date dateBapteme;
	
	/*-----	Sacréments  ----*/
	@Wire Rows rowsSacrement;
	@Wire Row rowTitle;
	String libelle, lieu;
	Date dateSacrement;
	List<Sacrement> listSacrement = new ArrayList<>();
	
	
	@Override
    public void doAfterCompose(Component comp) throws Exception {
    	super.doAfterCompose(comp);
    	
    	if(comp.getId().equals("winFidele")){
    		displayList(null);
    		comp.addEventListener("onAddSacrement", this);
    		Utils.setSessionAttribute("winFidele", winFidele);
    	}
    		
	}
	
	private void displayList(List<Fidele> list) {
		if(list == null)
			list = OperationsDb.find(Constants.fideles, null);
		lml = new ListModelList<Fidele>(list);
    	listbox.setModel(lml);
		
	}

	@Listen("onClick=#btnSearch")
	public void doSearch(){
		if(txtDateNaissance.getValue() == null &&  txtNom.getValue().isEmpty() && txtPrenoms.getValue().isEmpty()){
			// don't do anything
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(Constants.dateNaissance, txtDateNaissance.getValue());
			params.put(Constants.nom, txtNom.getValue());
			params.put(Constants.prenoms, txtPrenoms.getValue());
			
			List<Fidele> list = OperationsDb.find(Constants.fideles, params);
	    	displayList(list);
		}
	}
	
	@Listen("onClick=#btnRefresh")
	public void doRefresh(){
		txtDateNaissance.setValue(null); txtNom.setValue(""); txtPrenoms.setValue("");
		List<Fidele> list = OperationsDb.find(Constants.fideles, null);
    	displayList(list);
	}
	
	@Listen("onClick=#menuAdd")
	public void onAdd(){
		refreshForm();
		divList.setVisible(false);
		divForm.setVisible(true);
	}
	
	@Listen("onClick=#menuUpdate")
	public void onUpdate() throws Exception{
		
		if(listbox.getSelectedItem() == null){
			Messagebox.show("Veuillez sélectionner un élement de la liste", "Modifier un fidèle", Messagebox.OK, Messagebox.EXCLAMATION);
		} else{
			Fidele selected = ((Fidele)listbox.getSelectedItem().getValue());
			
			divList.setVisible(false);
			divForm.setVisible(true);
			
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
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(Constants.fideleId, selected.getId());
			List<Bapteme> listBapt = (List<Bapteme>)OperationsDb.find(Constants.bapteme, params);
			Bapteme bapt = listBapt.size() == 1 ? listBapt.get(0) : null;
			
			if(bapt != null){
				txtNumeroBapt.setValue(bapt.getNumero());
				dateBapt.setValue(bapt.getDateBapteme());
				dateBapt.setFormat("dd/MM/yyyy");
				txtDiocese.setValue(bapt.getDiocese());
				txtEglise.setValue(bapt.getEglise());
				txtPretreBapteme.setValue(bapt.getPretre());
			}
			
			// sacrements
			refreshRowsSacrements();
			List<Sacrement> listSacres = (List<Sacrement>)OperationsDb.find(Constants.sacrements, params);
			for(Sacrement s : listSacres){
				rowsSacrement.appendChild(Utils.buildSacrements(s));
			}
		}
		
	}
	
	@Listen("onClick=#menuDetails")
	public void onDetails() throws Exception{
		
		onUpdate();
			
		for(Component comp : divForm.getFellows()){
			if(comp instanceof Textbox){
				((Textbox) comp).setReadonly(true);
			} if (comp instanceof Datebox){
				((Datebox) comp).setDisabled(true);
			} if (comp instanceof Button){
					((Button) comp).setVisible(false);
			}
		}
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
			        	Fidele entity = (Fidele)listbox.getSelectedItem().getValue();
						OperationsDb.deleteById(Fidele.class, entity.getId());
						Messagebox.show("Fidèle supprimé avec succès", "Supprimer un fidèle", Messagebox.OK, Messagebox.EXCLAMATION);
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
		displayList(null);
		
	}

	
	@Listen("onClick=#btnSave")
	public void save() throws Exception{
		
			try{				
				assignValues();
				
				//create new
				if(errorCheck()){
					Fidele fid = new Fidele(dob, lieuNaissance, nom, nomMarraine, nomMere, nomParain, nomPere, origineMere, originePere, prenoms);
					Bapteme bapt = new Bapteme(dateBapteme, diocese, eglise, numero, pretre, fid);
					for(Sacrement s : listSacrement){
						s.setFidele(fid);
					}
					List<Object> listObj = new ArrayList<>();
					listObj.add(fid);
					listObj.add(bapt);
					listObj.addAll(listSacrement);
					
					OperationsDb.persistObject(listObj);
					
					Messagebox.show("Fidèle enregistré avec succès", "Créer un fidèle", Messagebox.OK, Messagebox.INFORMATION);
					refreshForm();
				}	
				
			} catch(Exception e){
				e.printStackTrace();
			}
		
			
	}
	
	
	@Listen("onClick=#btnSaveMod")
	public void update() throws ParseException{
		
		assignValues();
		
		if(errorCheck()){
			
			Fidele fid = (Fidele)listbox.getSelectedItem().getValue();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(Constants.id, fid.getId());
			List<Fidele> list = OperationsDb.find(Constants.fideles, params);
			Fidele p = list.get(0);
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
			OperationsDb.updateObject(p);	
			Messagebox.show("Fidèle mis à jour avec succès", "Créer un fidèle", Messagebox.OK, Messagebox.INFORMATION);
		}
	}
	
	
	@Listen("onClick=#btnAddSacrement")
	public void onAddSacrement() throws Exception{
		Utils.openModal("/references/sacrementForm.zul", null, null, "Ajouter un sacrément");
	}
	
	public boolean errorCheck(){
		
		boolean bool = true;
				
		String[] abc = new String[]{nom, lieuNaissance, nomPere, originePere, nomMere, origineMere,   
									diocese, eglise, numero, pretre};
		
		
		if(Utils.isEmptyCheck(abc)){
			bool = false;
			Messagebox.show("Veuillez saisir les champs obligatoires", "Créer un fidèle", Messagebox.OK, Messagebox.INFORMATION);
		}
		
		return bool;
	}

	private void assignValues() throws ParseException {
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
				
				// sacréments
					
					// 	get rows
					List<Row> listrow = rowsSacrement.getChildren();
					// for each row, get cells and then labels
					for(Row row : listrow){
						if(!row.getId().equals("title")){
							List<String> list = new ArrayList<String>();
							List<Cell> listCells = row.getChildren();
							for(Cell cell : listCells){
								if(cell.getFirstChild() instanceof Label){
									Label l = (Label) cell.getFirstChild();
									list.add(l.getValue()); 
									// ajout dans l'ordre de libelle, date, lieu
								}
							}
							if(list.size() == 3){
								libelle = list.get(0);
								SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
								dateSacrement = formatter.parse(list.get(1));
								lieu = list.get(2);
								Sacrement sacr = new Sacrement(dateSacrement, libelle, lieu, null);
								listSacrement.add(sacr);
							}
						}
					}
		
	}

	@Listen("onClick=#btnRefreshForm")
	public void refreshForm() {
		
		Textbox[] textBoxes = new Textbox[]{txtNomF, txtPrenomsF, txtlieuNaissance, txtNomPere, txtNomMere, txtOriginePere,
				txtOrigineMere, txtNomParrain, txtNomMarraine,  txtNumeroBapt, txtDiocese, txtEglise, txtPretreBapteme};
		Utils.clearTextboxes(textBoxes);
		dateDob.setText("");
		dateBapt.setText("");
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

	@Override
	public void onEvent(Event event) throws Exception {
		if(event.getName().equalsIgnoreCase("onAddSacrement")){
			Row row = (Row) event.getData();
			rowsSacrement.appendChild(row);
		}
		
	}
	
	
}
