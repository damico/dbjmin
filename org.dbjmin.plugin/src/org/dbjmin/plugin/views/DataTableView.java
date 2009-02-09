package org.dbjmin.plugin.views;



import org.dbjmin.plugin.data.DBConnect;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.internal.dialogs.ViewContentProvider;
import org.eclipse.ui.internal.dialogs.ViewLabelProvider;
import org.eclipse.ui.part.ViewPart;



public class DataTableView extends ViewPart {
	
	
	

	public DataTableView() {
	}
	
	public void createPartControl(Composite parent){
	
		SWTUtils.singleton().dataTableView(parent);
	}
	
	public void createStructureTable(Composite parent) {


	}

	
	public void setFocus() {
		
	}

}