/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
*/
package com.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.services.SidebarPage;
import com.services.SidebarPageConfig;

public class SidebarPageConfigChapter2Impl implements SidebarPageConfig{

	HashMap<String,SidebarPage> pageMap = new LinkedHashMap<String,SidebarPage>();
	public SidebarPageConfigChapter2Impl(){		
		pageMap.put("fn1",new SidebarPage("Accueil","Accueil","/imgs/home-icon.png","/common/bienvenue.zul"));
		pageMap.put("fn2",new SidebarPage("Utilisateurs","Utilisateurs","/imgs/user-group-icon.png","/references/users.zul"));
		pageMap.put("fn3",new SidebarPage("Fidèles","Fidèles","/imgs/people.png","/references/fideles.zul"));
		
	}
	
	public List<SidebarPage> getPages(){
		return new ArrayList<SidebarPage>(pageMap.values());
	}
	
	public SidebarPage getPage(String name){
		return pageMap.get(name);
	}
}
