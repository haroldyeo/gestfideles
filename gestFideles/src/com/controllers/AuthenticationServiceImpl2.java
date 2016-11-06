/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
*/
package com.controllers;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;

import com.services.UserCredential;
import com.utils.Constants;
import com.utils.OperationsDb;
import com.utils.Utils;

import model.User;

public class AuthenticationServiceImpl2 extends SelectorComposer<Component> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Wire Panel pnlLogin;
	
	@Wire Textbox txtIdentifiant;
	
	@Wire Textbox txtPassword;
	
	@Wire Button btnLogin;
	
	@Listen("onClick=#btnLogin")
	public void doCheckLogin(){
		User onlineUser = login(txtIdentifiant.getValue(), txtPassword.getValue()); 
		if( onlineUser != null){
			getUserCredential(onlineUser);
			Executions.getCurrent().sendRedirect("/home.zul");
		} else{
			Messagebox.show("Informations incorrectes", Constants.app_title, Messagebox.OK, Messagebox.EXCLAMATION);
			
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public User login(String nm, String pd) {
		Map<String, Object> mapParams = new HashMap<String, Object>();
		mapParams.put("identifiant", nm);
		mapParams.put("motPasse", pd);
		List<User> list = OperationsDb.find(Constants.users, mapParams);
		
		return list!=null && list.size()==1 ? list.get(0) : null;
	}

	public UserCredential getUserCredential(User user){
		UserCredential cre = (UserCredential)Sessions.getCurrent().getAttribute("userCredentials");
		if(cre==null){
			cre = new UserCredential();
			cre.setNom(user.getNom());
			cre.setPrenoms(user.getPrenoms());
			cre.setIdentifiant(user.getIdentifiant());
			Sessions.getCurrent().setAttribute("userCredentials",cre);
			
			
			
		}
		return cre;
	}
	

	public void logout() {
		
		Utils.getCurrentSession().invalidate();
		Executions.getCurrent().sendRedirect("/login.zul");
	}
	
	
}
