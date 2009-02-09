package org.dbjmin.plugin.views;


import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;


public class SQLview extends ViewPart {


	public SQLview() {
	}
	
	
	public void createPartControl(Composite parent) {

		 SWTUtils.singleton().callSQL(parent);
		 SWTUtils.singleton().showSQLarea(parent);
				
	}
	
	public void setFocus() {
		
	}
}