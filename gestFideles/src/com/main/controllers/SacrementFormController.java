package com.main.controllers;

import java.text.SimpleDateFormat;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.utils.Utils;



public class SacrementFormController  extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	
	@Wire Textbox txtLibelle, txtLieu;
	
	@Wire Datebox dateSacrement;
	
	@Wire Window winSacrementForm;
	
	@Override
    public void doAfterCompose(Component comp) throws Exception {
    	super.doAfterCompose(comp);
    	    		
	}
		
	
	@Listen("onClick=#saveSacre")
	public void onSave(){
		String[] textBoxes = new String[]{txtLibelle.getValue(), txtLieu.getValue()};
		if(Utils.isEmptyCheck(textBoxes) && dateSacrement.getText().equals("")){
			Messagebox.show("Veuillez remplir tous les champs", "Sacrément", Messagebox.OK, Messagebox.EXCLAMATION);
		} else {
			Label lblLibelleSacre = new Label(txtLibelle.getValue());
			SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
			Label lblDateSacre = new Label(s.format(dateSacrement.getValue()));
			Label lblLieu = new Label(txtLieu.getValue());
			Image imgdel = new Image("/imgs/del-icon.png");
//			imgdel.addEventListener("onClick", imgdel.);
			
			Cell cell1 = new Cell();
			cell1.appendChild(lblLibelleSacre);
			
			Cell cell2 = new Cell();
			cell2.appendChild(lblDateSacre);
			
			Cell cell3 = new Cell();
			cell3.appendChild(lblLieu);
			
			Cell cell4 = new Cell();
			cell4.appendChild(imgdel);
			
			
			Row row = new Row();
			row.appendChild(cell1);
			row.appendChild(cell2);
			row.appendChild(cell3);
			
			Window win = (Window) Utils.getSessionAttribute("winFidele");
			
			Events.postEvent("onAddSacrement", win, row);
			
			winSacrementForm.detach();
			
		}
	}
	
}
