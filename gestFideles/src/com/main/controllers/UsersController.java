package com.main.controllers;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.utils.Constants;
import com.utils.OperationsDb;
import com.utils.Utils;

import model.User;

@SuppressWarnings("unchecked")
public class UsersController  extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	
	@Wire Div divUsersForm1, divUsersList;
		
	@Wire Listbox listboxUsers;
	
	@Wire Textbox txtIdentifiant, txtNom, txtPrenoms, txtIdentifiantF, txtNomF, txtPrenomsF, txtMdpF, txtMdp2F;
	
	@Wire Window winUsers;
	
	@Wire Button btnSearch,  btnRefresh, btnSave, btnSaveMod, btnRefreshForm;
	
	@Wire Textbox txtSearch;
	
	String nom, prenoms,ident, mdp1, mdp2;
	
	ListModelList<User> lml;
	
	
	@Override
    public void doAfterCompose(Component comp) throws Exception {
    	super.doAfterCompose(comp);
    	
    	if(comp.getId().equals("winUsers")){
    		displayListUsers(null);
    	}
    		
	}
	
	private void displayListUsers(List<User> listUsers) {
		if(listUsers == null)
			listUsers = OperationsDb.find(Constants.users, null, 10);
		lml = new ListModelList<User>(listUsers);
    	listboxUsers.setModel(lml);
		
	}

	@Listen("onClick=#btnSearch")
	public void doSearch(){
		if(txtSearch.getValue().isEmpty()){
			// don't do anything
		} else {			
			List<User> list = OperationsDb.doSearch(Constants.users, txtSearch.getValue());
	    	displayListUsers(list);
		
		}
	}
	
	@Listen("onClick=#btnRefresh")
	public void doRefresh(){
		txtSearch.setValue("");
		List<User> listUsers = OperationsDb.find(Constants.users, null, 10);
    	displayListUsers(listUsers);
	}
	
	@Listen("onClick=#menuAdd")
	public void onAdd(){
		refreshForm();
		divUsersList.setVisible(false);
		divUsersForm1.setVisible(true);
		btnSave.setVisible(true);
		btnSaveMod.setVisible(false);
		btnRefreshForm.setVisible(true);
	}
	
	@Listen("onClick=#menuUpdate")
	public void onUpdate() throws Exception{
		
		if(listboxUsers.getSelectedItem() == null){
			Messagebox.show("Veuillez sélectionner un élement de la liste", "Modifier un utilisateur", Messagebox.OK, Messagebox.EXCLAMATION);
		} else{
			User userSelected = ((User)listboxUsers.getSelectedItem().getValue());
			
			divUsersList.setVisible(false);
			divUsersForm1.setVisible(true);
			btnSave.setVisible(false);
			btnSaveMod.setVisible(true);
			btnRefreshForm.setVisible(false);
			
			txtNomF.setValue(userSelected.getNom());
			txtPrenomsF.setValue(userSelected.getPrenoms());
			txtIdentifiantF.setValue(userSelected.getIdentifiant());
			txtMdpF.setValue(userSelected.getMotPasse());
			txtMdp2F.setValue(userSelected.getMotPasse());
			
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	@Listen("onClick=#menuDelete")
	public void onDelete() throws Exception{
		if(listboxUsers.getSelectedItem() == null){
			Messagebox.show("Veuillez sélectionner un élement de la liste", "Créer un utilisateur", Messagebox.OK, Messagebox.EXCLAMATION);
		} else{
			Messagebox.show("Êtes vous sûrs de vouloir supprimer?", "Supprimer un utilisateur", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
			    public void onEvent(Event evt) throws InterruptedException {
			        if (evt.getName().equals("onYes")) {
			        	User luser = (User)listboxUsers.getSelectedItem().getValue();
						OperationsDb.deleteById(User.class, luser.getId());
						Messagebox.show("Utilisateur supprimé avec succès", "Supprimer un utilisateur", Messagebox.OK, Messagebox.EXCLAMATION);
						displayListUsers(null);
			        } else if (evt.getName().equals("onNo")) {
			            // nada
			        } 
			    }
		});
	  }
		
	}
	
	@Listen("onClick=#menuBack")
	public void onBack(){
		divUsersForm1.setVisible(false);
		divUsersList.setVisible(true);
		txtSearch.setValue("");
		displayListUsers(null);
		
	}
	
	@Listen("onClick=#btnSaveMod")
	public void update(){
		
		errorCheck();
		
		User usr = (User)listboxUsers.getSelectedItem().getValue();
		usr.setIdentifiant(ident);
		usr.setNom(nom);
		usr.setPrenoms(prenoms);
		usr.setMotPasse(mdp1);
		OperationsDb.updateObject(usr);	
		Messagebox.show("Utilisateur mis à jour avec succès", Constants.popoup_title_create_user, Messagebox.OK, Messagebox.INFORMATION);
	}
	
	@Listen("onClick=#btnSave")
	public void save() throws Exception{
			//create new
			errorCheck();
			User usr= new User(ident, mdp1, nom, prenoms);
			OperationsDb.persistObject(usr);
			Messagebox.show("Utilisateur enregistré avec succès", Constants.popoup_title_create_user, Messagebox.OK, Messagebox.INFORMATION);
			refreshForm();
	}
	
	
	public void errorCheck(){
				
		nom = txtNomF.getValue();
		prenoms = txtPrenomsF.getValue();
		ident = txtIdentifiantF.getValue();
		mdp1 = txtMdpF.getValue();
		mdp2 = txtMdp2F.getValue();
				
		if(Utils.checkEmptyComponents(new Component[]{txtNomF, txtIdentifiantF, txtMdpF, txtMdp2F})){
			Utils.errorMessages("emptyUser");
		} else if(!mdp1.equals(mdp2)){
			txtMdpF.setValue("");
			txtMdp2F.setValue("");
			Utils.errorMessages("passwordNotMatching");
		}
		
	}

	@Listen("onClick=#btnRefreshForm")
	public void refreshForm() {
		
		Textbox[] textBoxes = new Textbox[]{txtNomF, txtPrenomsF, txtIdentifiantF, txtMdpF, txtMdp2F};
		Utils.clearComponents(textBoxes);
		
	}
	
	
}
