package controllers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import services.SidebarPage;
import services.SidebarPageConfig;

public class SideBarController extends MainController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	  //wire components
    @Wire Grid fnList;

  //services
    SidebarPageConfig pageConfig = new SidebarPageConfigChapter2Impl();

    @Override
    public void doAfterCompose(Component comp) throws Exception{
        super.doAfterCompose(comp);

        //initialize view after view construction.
        if(comp instanceof Grid){
        	Rows rows = fnList.getRows();

            for(SidebarPage page:pageConfig.getPages()){
                Row row = constructSidebarRow(page.getName(), page.getLabel(),page.getIconUri(),page.getUri());
                rows.appendChild(row);
            }
            
            Row row = constructSidebarRow("Yahoo", "Yahoo", null, "http://www.yahoo.fr");
            rows.appendChild(row);
            
            Row row2 = constructSidebarRow("Page de connexion", "Page de connexion", null, "/index.zul");
            rows.appendChild(row2);
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
                mainFrame.setSrc(locationUri);
            	
            		
            }
        };

        row.addEventListener(Events.ON_CLICK, actionListener);

        return row;
    }
    
}
