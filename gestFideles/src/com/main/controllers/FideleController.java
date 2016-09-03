package com.main.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zhtml.Button;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.utils.Constants;
import com.utils.OperationsDb;

import model.Fidele;

@SuppressWarnings("unchecked")
public class FideleController  extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	
	@Wire Div divForm, divList;
		
	@Wire Listbox listbox;
	
	@Wire Textbox txtNom, txtPrenoms, txtNomF, txtPrenomsF, txtNomPere, dateLieu, txtOriginePere, txtNomMere, txtOrigineMere;
	
	@Wire Datebox txtDateNaissance, dateDob;
	
	@Wire Window winFidele;
	
	@Wire Button btnSearch, btnSave, btnRefresh;
	
	String nom, prenoms,ident, mdp1, mdp2;
	
	ListModelList<Fidele> lml;
	
	
	@Override
    public void doAfterCompose(Component comp) throws Exception {
    	super.doAfterCompose(comp);
    	
    	if(comp.getId().equals("winFidele")){
    		displayList(null);
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
			
			txtNomF.setValue(selected.getNom());
			txtPrenomsF.setValue(selected.getPrenoms());
			dateDob.setValue(selected.getDob());
			txtNomPere.setValue(selected.getNomPere());
			dateLieu.setValue(selected.getLieuNaissance() );
			txtOriginePere.setValue(selected.getOriginePere()); 
			txtNomMere.setValue(selected.getNomMere() );
			txtOrigineMere.setValue(selected.getOrigineMere());
			
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
		
		Fidele entity = null;
		
		if(listbox.getSelectedItem() == null){
			//create new
			if(errorCheck()){
				entity= new Fidele(ident, mdp1, nom, prenoms);
				OperationsDb.persistObject(usr);
				Messagebox.show("Fidèle enregistré avec succès", "Créer un fidèle", Messagebox.OK, Messagebox.INFORMATION);
				refreshForm();
			}
			
		} else{
			// update
			if(errorCheck()){
				
				Fidele luser = (Fidele)listbox.getSelectedItem().getValue();
				Map<String, Object> params = new HashMap<String, Object>();
				params.put(Constants.id_user, luser.getId());
				List<Fidele> listUsers = OperationsDb.find(Constants.users, params);
				usr = listUsers.get(0);
				usr.setIdentifiant(ident);
				usr.setNom(nom);
				usr.setPrenoms(prenoms);
				usr.setMotPasse(mdp1);
				OperationsDb.updateObject(usr);	
				Messagebox.show("Fidèle mis à jour avec succès", "Créer un fidèle", Messagebox.OK, Messagebox.INFORMATION);
			}
		}
		
	}
	
	
	public boolean errorCheck(){
		
		boolean bool = true;
		
		nom = txtNomF.getValue();
		prenoms = txtPrenomsF.getValue();
		ident = txtIdentifiantF.getValue();
		mdp1 = txtMdpF.getValue();
		mdp2 = txtMdp2F.getValue();
		
		if(nom.isEmpty() ||  ident.isEmpty() || mdp1.isEmpty() || mdp2.isEmpty()){
			bool = false;
			Messagebox.show("Veuillze saisir les champs obligatoires", "Créer un fidèle", Messagebox.OK, Messagebox.INFORMATION);
		} else if(!mdp1.equals(mdp2)){
			bool = false;
			Messagebox.show("Les mots de passe saisis ne sont pas identiques", "Créer un fidèle", Messagebox.OK, Messagebox.EXCLAMATION);
			txtMdpF.setValue("");
			txtMdp2F.setValue("");
		}
		
		return bool;
	}

	@Listen("onClick=#btnRefreshForm")
	public void refreshForm() {
		txtNomF.setText("");
		txtPrenomsF.setText("");
		txtIdentifiantF.setText("");
		txtMdpF.setText("");
		txtMdp2F.setText("");
	}
	
	
}
