package com.utils;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import model.Sacrement;

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
	 * @param abc
	 * @return
	 */
	public static boolean isEmptyCheck(String[] abc){
		boolean result = false;
		for(String s : abc){
			if(s.isEmpty()){
				result = true;
				break;
			}
		}
		return result;
	}
	
	public static void clearTextboxes(Textbox[] txts){
		for(Textbox t : txts){
			t.setText("");
		}
	}

	public static Row buildSacrements(Sacrement s) {
		Label lblLibelleSacre = new Label(s.getLibelle());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Label lblDateSacre = new Label(sdf.format(s.getDateSacrement()));
		Label lblLieu = new Label(s.getLieu());
		Button btnDel = new Button();
		btnDel.setImage("/imgs/btn-del.png");
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
	
	//create and register event listener
    static EventListener<Event> actionListener = new SerializableEventListener<Event>() {
        private static final long serialVersionUID = 1L;

        public void onEvent(Event event) throws Exception {
           Button btnDel = (Button) getSessionAttribute("btnDel");
           btnDel.getParent().getParent().detach();
        }
    };

}
