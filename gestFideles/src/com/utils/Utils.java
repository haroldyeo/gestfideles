package com.utils;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

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
	 * True: not empty, ok <br>
	 * False : empty, ok
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

}
