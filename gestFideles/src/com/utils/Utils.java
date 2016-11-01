package com.utils;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import model.Enfant;
import model.Sacrement;
import model.SacrementMalades;

public class Utils {
	
		
	public static Session getCurrentSession(){
		return Sessions.getCurrent();
	}
	
	public static  Object getSessionAttribute(String attr){
		return getCurrentSession().getAttribute(attr);
	}
	
	public static void setSessionAttribute(String key, Object value){
		 Sessions.getCurrent().setAttribute(key, value);
	}

	public static void openModal(String link, Component parent, Map<Object, Object> params, String title) {
		Window window = (Window)Executions.createComponents(
                link, parent, params);
		window.setClosable(true);
		window.setTitle(title);
        window.doModal();
		
	}
	
	/**
	 * Retourne true si un élément n'est pas 'empty'
	 * @param abc
	 * @return
	 */
	public static boolean isNotEmptyCheck(String[] abc){
		boolean result = false;
		for(String s : abc){
			if(!s.isEmpty()){
				result = true;
				break;
			}
		}
		return result;
	}
	
	public static boolean checkEmptyComponents(Component[] comps){
			boolean b = false;
			for(Component p : comps){
				if(p instanceof Textbox){
					if(((Textbox) p).getText().equals("")){
						b = true;
						break;
					}
				} else if (p instanceof Datebox){
					if(((Datebox)p).getText().equals("")){
						b = true;
						break;
					}
				}
			}
		
		return b;
	}
	
	public static void clearComponents(Component[]... comps){
		for(Component[] c : comps){
			if(c instanceof Textbox[]){
				for(Textbox t : (Textbox[])c){
					t.setText("");
				}
			}
			
			else if(c instanceof Datebox[]){
				for(Datebox d : (Datebox[])c){
					d.setText("");
				}
			}
		}
		
		
	}

	public static Row buildSacrements(Sacrement s) {
		Label lblLibelleSacre = new Label(s.getLibelle());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Label lblDateSacre = new Label(sdf.format(s.getDateSacrement()));
		Label lblLieu = new Label(s.getLieu());
		Button btnDel = new Button();
		btnDel.setImage("/imgs/btn-del.png");
		if(s.getId() != null)
			btnDel.setAttribute("idSacre", s.getId().toString());
		btnDel.addEventListener(Events.ON_CLICK, actionListener);
		
		Cell cell1 = new Cell();
		cell1.appendChild(lblLibelleSacre);
		
		Cell cell2 = new Cell();
		cell2.appendChild(lblDateSacre);
		
		Cell cell3 = new Cell();
		cell3.appendChild(lblLieu);
		
		Cell cell4 = new Cell();
		cell4.appendChild(btnDel);
		
		Row row = new Row();
		row.appendChild(cell1);
		row.appendChild(cell2);
		row.appendChild(cell3);
		row.appendChild(cell4);
		
		return row;
		
	}
	
    static EventListener<Event> actionListener = new SerializableEventListener<Event>() {
        private static final long serialVersionUID = 1L;

        public void onEvent(Event event) throws Exception {
           Button btnDel = (Button) event.getTarget();
           btnDel.getParent().getParent().detach();
        }
    };
    
    
    public static Row buildEnfants(Enfant e){
    	Label lblnomEnfant = new Label(e.getNom());
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Label lblDateNaissance = new Label(sdf.format(e.getDob()));
		Label lblDateBapt = new Label(sdf.format(e.getDateBapteme()));
		Label lblnumBaptEnfant = new Label(e.getNumBapteme());
		Label lblLieuBaptEnfant = new Label(e.getLieuBapteme());
		
		Button btnDel = new Button();
		btnDel.setImage("/imgs/btn-del.png");
		if(e.getId() != null)
			btnDel.setAttribute("idEnfant", e.getId().toString());
		btnDel.addEventListener(Events.ON_CLICK, actionListener);
		
		Cell cell1 = new Cell();
		cell1.appendChild(lblnomEnfant);
		
		Cell cell2 = new Cell();
		cell2.appendChild(lblDateNaissance);
		
		Cell cell3 = new Cell();
		cell3.appendChild(lblDateBapt);
		
		Cell cell5 = new Cell();
		cell5.appendChild(lblnumBaptEnfant); 
		
		Cell cell6 = new Cell();
		cell6.appendChild(lblLieuBaptEnfant); 
		
		Cell cell7 = new Cell();
		cell7.appendChild(btnDel);
		
		Row row = new Row();
		row.appendChild(cell1);
		row.appendChild(cell2);
		row.appendChild(cell3);
		row.appendChild(cell5);
		row.appendChild(cell6);
		row.appendChild(cell7);
		
		return row;
    	
    }

        
    public static Row buildSacreMalade(SacrementMalades sm){
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Label lblDate = new Label(sdf.format(sm.getDate()));
		Label lblLieu = new Label(sm.getLieu());
		
		Button btnDel = new Button();
		btnDel.setImage("/imgs/btn-del.png");
		if(sm.getId() != null)
			btnDel.setAttribute("idSacrementMalade", sm.getId().toString());
		btnDel.addEventListener(Events.ON_CLICK, actionListener);
		
		Cell cell1 = new Cell();
		cell1.appendChild(lblDate);
		
		Cell cell2 = new Cell();
		cell2.appendChild(lblLieu);
		
		Cell cell3 = new Cell();
		cell3.appendChild(btnDel);
		
		Row row = new Row();
		row.appendChild(cell1);
		row.appendChild(cell2);
		row.appendChild(cell3);
		
		return row;
    	
    }
    
    public static void errorMessages(String key){
    	switch (key) {
		case "noParrainOrMarraine":
			Messagebox.show("Veuillez saisir un parrain ou une marraine", Constants.popoup_title_create_fideles, Messagebox.OK, Messagebox.ERROR);
			throw new WrongValueException("Saisir parrain ou marraine!");
			
		case "emptyInfosBase":
			Messagebox.show("Veuillez saisir les champs obligatoires de la rubrique \"Informations de base\"", Constants.popoup_title_create_fideles, Messagebox.OK, Messagebox.ERROR);
			throw new WrongValueException("Empty info de base!");
			
		case "emptyBapteme":
			Messagebox.show("Veuillez saisir les champs obligatoires de la rubrique \"Baptême\"", Constants.popoup_title_create_fideles, Messagebox.OK, Messagebox.ERROR);
			throw new WrongValueException("Empty Bapteme!"); 
			
		case "emptyMariage":
			Messagebox.show("Veuillez saisir les champs obligatoires de la rubrique \"Mariage\"", Constants.popoup_title_create_fideles, Messagebox.OK, Messagebox.ERROR);
			throw new WrongValueException("Empty Mariage!"); 
			
		case "emptyBenNupt":
			Messagebox.show("Veuillez saisir les champs obligatoires de la rubrique \"Bénédiction nuptiale\"", Constants.popoup_title_create_fideles, Messagebox.OK, Messagebox.ERROR);
			throw new WrongValueException("Empty Bénédiction nuptiale!");  
			
		case "emptyFormCiviles":
			Messagebox.show("Veuillez saisir les champs obligatoires de la rubrique \"Formalités civiles\"", Constants.popoup_title_create_fideles, Messagebox.OK, Messagebox.ERROR);
			throw new WrongValueException("Empty Formalités civiles!");
			
		default:
			break;
		}
    }


}
