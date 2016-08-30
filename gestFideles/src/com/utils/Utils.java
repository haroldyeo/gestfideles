package com.utils;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

public class Utils {
	
	public static Session getCurrentSession(){
		return Sessions.getCurrent();
	}
	
	public static  Object getSessionAttribute(String attr){
		System.out.println("Utils.getSessionAttribute()   "+Sessions.getCurrent().toString());
		return getCurrentSession().getAttribute(attr);
	}
	
	public static void setSessionAttribute(String key, Object value){
		 Sessions.getCurrent().setAttribute(key, value);
	}

}
