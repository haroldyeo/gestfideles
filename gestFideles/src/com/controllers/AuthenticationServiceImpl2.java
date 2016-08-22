/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
*/
package com.controllers;

import java.io.Serializable;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;

import com.services.AuthenticationService;
import com.services.UserCredential;
import com.utils.OperationsDb;

import model.User;

public class AuthenticationServiceImpl2 extends SelectorComposer<Component> implements AuthenticationService,Serializable{
	private static final long serialVersionUID = 1L;
	
	@Wire Panel pnlLogin;
	
	@Wire Textbox txtIdentifiant;
	
	@Wire Textbox txtPassword;
	
	@Wire Button btnLogin;

	public UserCredential getUserCredential(){
		Session sess = Sessions.getCurrent();
		UserCredential cre = (UserCredential)sess.getAttribute("userCredential");
		if(cre==null){
			cre = new UserCredential();//new a anonymous user and set to session
			sess.setAttribute("userCredential",cre);
		}
		return cre;
	}
	

	@SuppressWarnings("unchecked")
	public boolean login(String nm, String pd) {
		OperationsDb.mapParams.clear();
		OperationsDb.mapParams.put("identifiant", nm);
		OperationsDb.mapParams.put("motPasse", pd);
		List<User> list = OperationsDb.find("users");
		return list != null &&  list.size() > 0 ? true : false;
	}


	public void logout() {
		// will be implemented in chapter7
	}
	
	@Listen("onClick=#btnLogin")
	public void doCheckLogin(){
		if(login(txtIdentifiant.getValue(), txtPassword.getValue())){
			getUserCredential();
			Executions.getCurrent().sendRedirect("/home.zul");
		}
	}
}
