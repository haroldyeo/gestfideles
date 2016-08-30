package com.utils;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
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

}
