package com.main.controllers;

import java.util.Calendar;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.utils.Constants;
import com.utils.Utils;

import model.CommunionPascale;



public class ComPascaleFormController  extends SelectorComposer<Component> {
	
private static final long serialVersionUID = 1L;
	
	@Wire Combobox cbAnnee;
	
	@Wire Checkbox chkDenierCulte;
	
	@Wire Window winEnfantForm;
	
	Button btnDel = new Button();
	
	@Override
    public void doAfterCompose(Component comp) throws Exception {
    	super.doAfterCompose(comp);
    	for(int i=1900; i<=2100; i++){
    		Comboitem item = new Comboitem();
    		item.setValue(i);
    		item.setLabel(String.valueOf(i));
    		item.setParent(cbAnnee);
    	}
    	cbAnnee.setValue(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
    	    		
	}
		
	
	@Listen("onClick=#saveComPascale")
	public void onSave(){
		
			CommunionPascale cp = new CommunionPascale(Integer.parseInt(cbAnnee.getValue()), chkDenierCulte.isChecked() ? "oui" : "non");
			
			Window win = (Window) Utils.getSessionAttribute("winFidele");
			
			Events.postEvent(Constants.events_comPascale, win, Utils.buildCommunionPascale(cp));
			
			winEnfantForm.detach();
			
//		}
	}
	
}
