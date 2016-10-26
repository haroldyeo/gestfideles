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

import model.SacrementMalades;



public class SacreMaladeFormController  extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	
	@Wire Textbox txtLieuSacreMalade;
	
	@Wire Datebox dateSacreMalade;
	
	@Wire Window winSacreMaladeForm;
	
	Button btnDel = new Button();
	
	@Override
    public void doAfterCompose(Component comp) throws Exception {
    	super.doAfterCompose(comp);
    	    		
	}
		
	
	@Listen("onClick=#saveSacreMalade")
	public void onSave(){
		String[] textBoxes = new String[]{ txtLieuSacreMalade.getValue() };
		if(Utils.isEmptyCheck(textBoxes) || dateSacreMalade.getText().equals("") ){
			Messagebox.show("Veuillez remplir tous les champs", "Sacrément des malades", Messagebox.OK, Messagebox.EXCLAMATION);
		} else {
			
			SacrementMalades sm = new SacrementMalades(dateSacreMalade.getValue(), txtLieuSacreMalade.getValue());
			
			Window win = (Window) Utils.getSessionAttribute("winFidele");
			
			Events.postEvent("onAddSacreMalade", win, Utils.buildSacreMalade(sm));
			
			winSacreMaladeForm.detach();
			
		}
	}
	
}
