package com.controllers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Window;

import com.services.SidebarPage;
import com.services.SidebarPageConfig;
import com.utils.Utils;

public class MainController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//wire components
    @Wire Window winMain ;
    @Wire Grid fnList;
    @Wire Include mainFrame;

  //services
    SidebarPageConfig pageConfig = new SidebarPageConfigChapter2Impl();
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
    	super.doAfterCompose(comp);
    	
    	if(Utils.getSessionAttribute("userCredentials") == null){
    		Utils.getCurrentSession().invalidate();
    		Executions.sendRedirect("http://localhost:8083/gestFideles/");
    	}else{
    		String v = (String) Utils.getSessionAttribute("locationURI");
        	mainFrame.setSrc(v!=null ? v : "/common/bienvenue.zul");
        	
        	 //initialize view after view construction.
            	Rows rows = fnList.getRows();

                for(SidebarPage page : pageConfig.getPages()){
                    Row row = constructSidebarRow(page.getName(), page.getLabel(),page.getIconUri(),page.getUri());
                    rows.appendChild(row);
                }
    		}
        }
        
    
    
    
    private Row constructSidebarRow(String name,String label, String imageSrc, final String locationUri) {

        //construct component and hierarchy
        Row row = new Row();
        Image image = new Image(imageSrc);
        Label lab = new Label(label);

        row.appendChild(image);
        row.appendChild(lab);
        row.setAttribute("link", locationUri);

        //set style attribute
        row.setSclass("sidebar-fn");
        

        //create and register event listener
        EventListener<Event> actionListener = new SerializableEventListener<Event>() {
            private static final long serialVersionUID = 1L;

            public void onEvent(Event event) throws Exception {
                //redirect current url to new location
            	Utils.setSessionAttribute("locationURI", locationUri);
            	String v = (String) Utils.getSessionAttribute("locationURI");
                mainFrame.setSrc(v);
            }
        };

        row.addEventListener(Events.ON_CLICK, actionListener);

        return row;
    }
    
}
