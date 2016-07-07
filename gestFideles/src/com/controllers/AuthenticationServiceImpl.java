/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
*/
package com.controllers;

import java.io.Serializable;

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

public class AuthenticationServiceImpl extends SelectorComposer<Component> implements AuthenticationService,Serializable{
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
	

	public boolean login(String nm, String pd) {
		return nm.equals("abc") && pd.equals("abc") ? true : false;
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
