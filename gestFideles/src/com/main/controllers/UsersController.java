package com.main.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zhtml.Button;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Textbox;

import com.utils.Constants;
import com.utils.OperationsDb;

import model.User;

@SuppressWarnings("unchecked")
public class UsersController  extends SelectorComposer<Component>{

	private static final long serialVersionUID = 1L;
	
	@Wire Listbox listboxUsers;
	@Wire Button btnSearch;
	@Wire Textbox txtIdentifiant;
	@Wire Textbox txtNom;
	@Wire Textbox txtPrenoms;
	@Wire Menuitem menuAdd;
	
	
	
	ListModelList<User> lml;
	
	
	@Override
    public void doAfterCompose(Component comp) throws Exception {
    	super.doAfterCompose(comp);
    	
    	System.out.println("USERSS: "+Sessions.getCurrent().toString()+"    "+Sessions.getCurrent().getAttribute("userCredentials"));
    	if(Sessions.getCurrent().getAttribute("userCredentials") == null){
    		Sessions.getCurrent().invalidate();
    		Executions.sendRedirect("http://localhost:8083/gestFideles/");
    	}else{
    		List<User> listUsers = OperationsDb.find(Constants.users, null);
        	lml = new ListModelList<>(listUsers);
        	listboxUsers.setModel(lml);
    	}
    	
    	
	}
	
	@Listen("onClick=#btnSearch")
	public void doSearch(){
		
		if(txtIdentifiant.getValue().equals("") &&  txtNom.getValue().equals("") && txtPrenoms.getValue().equals("")){
			// don't do anything
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(Constants.identifiant, txtIdentifiant.getValue());
			params.put(Constants.nom, txtNom.getValue());
			params.put(Constants.prenoms, txtPrenoms.getValue());
			
			List<User> listUsers = OperationsDb.find(Constants.users, params);
	    	lml = new ListModelList<>(listUsers);
	    	listboxUsers.setModel(lml);
		}
		
		
	}
	
	@Listen("onClick=#btnRefresh")
	public void doRefresh(){
		txtIdentifiant.setValue(""); txtNom.setValue(""); txtPrenoms.setValue("");
		List<User> listUsers = OperationsDb.find(Constants.users, null);
    	lml = new ListModelList<>(listUsers);
    	listboxUsers.setModel(lml);
	}
	
	@Listen("onClick=#menuAdd")
	public void onAdd(){
		alert("ajouter");
	}
	
	
	
	
}
