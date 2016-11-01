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

import com.utils.Constants;
import com.utils.Utils;

import model.Enfant;



public class EnfantFormController  extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	
	@Wire Textbox txtNomEnfant, txtNumBaptEnfant, txtLieuBaptEnfant;
	
	@Wire Datebox dateNaissanceEnfant, dateBaptEnfant;
	
	@Wire Window winEnfantForm;
	
	Button btnDel = new Button();
	
	@Override
    public void doAfterCompose(Component comp) throws Exception {
    	super.doAfterCompose(comp);
    	    		
	}
		
	
	@Listen("onClick=#saveEnfant")
	public void onSave(){
		
		if(Utils.checkEmptyComponents(new Component[]{txtNomEnfant, txtNumBaptEnfant, txtLieuBaptEnfant, dateNaissanceEnfant, dateBaptEnfant})){
			Messagebox.show("Veuillez remplir tous les champs", "Enfant", Messagebox.OK, Messagebox.EXCLAMATION);
		} else {
			
			Enfant f = new Enfant(dateBaptEnfant.getValue(), dateNaissanceEnfant.getValue(),
					txtLieuBaptEnfant.getValue(), txtNomEnfant.getValue(), txtNumBaptEnfant.getValue());
			
			Window win = (Window) Utils.getSessionAttribute("winFidele");
			
			Events.postEvent(Constants.events_enfant, win, Utils.buildEnfants(f));
			
			winEnfantForm.detach();
			
		}
	}
	
}
