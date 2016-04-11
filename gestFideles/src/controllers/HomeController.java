package controllers;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;

public class HomeController extends SelectorComposer<Window>{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	@Wire
	Button btn;
	
	@Wire
	Button abc;
	
	@Listen("onClick=#abc")
	 public void clickButton(){
		alert("hello");
		 
	 }
	
	

}
