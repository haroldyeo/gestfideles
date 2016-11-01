package com.main.controllers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.utils.Utils;

import model.Sacrement;



public class SacrementFormController  extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	
	@Wire Textbox txtLibelle, txtLieu;
	
	@Wire Datebox dateSacrement;
	
	@Wire Window winSacrementForm;
	
	Button btnDel = new Button();
	
	@Override
    public void doAfterCompose(Component comp) throws Exception {
    	super.doAfterCompose(comp);
    	    		
	}
		
	
	@Listen("onClick=#saveSacre")
	public void onSave(){
		
		if(Utils.checkEmptyComponents(new Component[]{txtLibelle, txtLieu, dateSacrement})){
			Messagebox.show("Veuillez remplir tous les champs", "Sacrément", Messagebox.OK, Messagebox.EXCLAMATION);
		} else {
			
			Sacrement s = new Sacrement(dateSacrement.getValue(), txtLibelle.getValue(), txtLieu.getValue(), null);
						
			Window win = (Window) Utils.getSessionAttribute("winFidele");
			
			Events.postEvent("onAddSacrement", win, Utils.buildSacrements(s));
			
			winSacrementForm.detach();
			
		}
	}
	
}
