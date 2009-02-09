package org.dbjmin.plugin.views;


import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;


/**
 * To be started as an application. Go to Run->Run..., create a new Eclipse
 * Application, select org.dbjmin.plugin as the application
 * and make sure you have all required plug-ins.
 * 
 */

public class LaunchView extends ViewPart {
	

	public LaunchView() {
	}
	
	
	public void createPartControl(Composite parent) {
		
		SWTUtils.singleton().setGenericWindowSettings(parent);
  		
	}
		
	public void setFocus() {
		
	}
}