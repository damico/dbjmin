package org.dbjmin.plugin.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.text.TableView.TableRow;

import org.dbjmin.plugin.data.DBConnect;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.dialogs.ViewContentProvider;
import org.eclipse.ui.internal.dialogs.ViewLabelProvider;

/**
 * SWTUtils is a Class that returns Singleton that building SWT main of Views
 * @author Mario Cezar Ponciano - a.k.a: Razec (mrazec@gmail.com)
 *
 */

public class SWTUtils {
	private static final SWTUtils getInstance = new SWTUtils();
	public StyledText textArea;
	public StyledText cmdSQL;
	private DBConnect dbc = new DBConnect();
	private Button btnSchemas;
	private Button btnData;
	private Combo cb_schemas;
	private Combo cb_tables;
	private ArrayList<String> prefs;
	private Combo driver;
	private String b;
	private TableColumn tableColumn;
	private TableRow tableRow;
	private Table table;
	private int rowCount =0;
    public static final String ID = "org.dbjmin.plugin.views.DataTableView";
    public static final String IDSQL = "org.dbjmin.plugin.sqlview";
	
	public static SWTUtils singleton() {
		return getInstance;
	}
	
	public void setGenericWindowSettings(final Composite parent){
		
		parent.setLayout(new GridLayout(14, false));
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label lb_type = new Label(parent, SWT.NONE);
		lb_type.setText("Type:");		
		
		
		//Show Combo for Select Database
		driver =
			   new Combo(parent,SWT.DROP_DOWN);
			
			   driver.add("db2");
			   driver.add("mysql");
			   driver.add("postgre");
			   driver.add("firebird");
			   driver.add("derby");
			   driver.add("derby-e");
			   driver.add("oracle");
						 
		Label lb_svr = new Label(parent, SWT.NONE);
		lb_svr.setText("Srv:");				
		final Text txtSrv = new Text(parent, SWT.BORDER);
		
		Label lb_port = new Label(parent, SWT.NONE);
		lb_port.setText("Port:");	
		final Text txtPort = new Text(parent, SWT.BORDER);
		
		Label lb_db = new Label(parent, SWT.NONE);
		lb_db.setText("DB:");
		final Text txtDb = new Text(parent, SWT.BORDER);
		Button btnOpenDb = new Button(parent, SWT.PUSH);
		btnOpenDb.setText(">");
		btnOpenDb.setBounds(385, 2, 40, 20);
		btnOpenDb.addListener (SWT.Selection, new Listener () {  
            public void handleEvent (Event e) {  			
            	JFileChooser fc = new JFileChooser();
            	int res = fc.showOpenDialog(null);
            	if(res == JFileChooser.APPROVE_OPTION){
				File arq = fc.getSelectedFile();
				txtDb.setText(arq.getAbsolutePath());
                        		
			}
		}}); 
		
		Label lb_user = new Label(parent, SWT.NONE);
		lb_user.setText("User:");
		final Text txtUser = new Text(parent, SWT.BORDER);
	
		final Label lb_passwd = new Label(parent, SWT.NONE);
		lb_passwd.setText("Passwd: ");
		final Text txtPasswd = new Text(parent, SWT.BORDER);
		txtPasswd.setEchoChar('#');
		
		Button btnConnect = new Button(parent, SWT.PUSH);
		btnConnect.setText("Connect");
		btnConnect.addListener (SWT.Selection, new Listener () {  
         
			public void handleEvent (Event e) {  			
           
        		prefs = new ArrayList<String>();
        		prefs.add(txtSrv.getText());
        		prefs.add(txtPort.getText());
        		prefs.add(txtDb.getText());
        		prefs.add(txtUser.getText());
        		prefs.add(txtPasswd.getText());
        		System.out.println(driver.getItem(driver.getSelectionIndex()).toString());
        	    dbc.isValidConnect(driver, prefs);
        	    getSchema();
          		
			}
		});  
		
		Label lb_schemas = new Label(parent, SWT.NONE);
		lb_schemas.setText("Schemas: ");
		cb_schemas = new Combo(parent, SWT.DROP_DOWN);
		cb_schemas.setEnabled(false);
	    btnSchemas = new Button(parent, SWT.PUSH);
		btnSchemas.setText(">>");
		btnSchemas.addListener (SWT.Selection, new Listener () {  
           public void handleEvent (Event e) {  
            
            	getTables(cb_schemas.getItem(cb_schemas.getSelectionIndex()));
           }
        });
		
		Label lb_tables = new Label(parent, SWT.NONE);
		lb_tables.setText("Tables: ");
		cb_tables = new Combo(parent, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY);
		cb_tables.setEnabled(false);
		
		btnData = new Button(parent, SWT.PUSH);
		btnData.setText("Data");
		btnData.setEnabled(true);
		btnData.addListener (SWT.Selection, new Listener () {  
            public void handleEvent (Event e) {  
            	IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
            	try {
            		
					page.showView(ID);
					rowCount++;
					for(int i=0; i < rowCount;i++){
						
						getTablesDescription(cb_schemas.getItem(cb_schemas.getSelectionIndex()),cb_tables.getItem(cb_tables.getSelectionIndex()));
						
					}
					
				} catch (PartInitException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});  
		
		Button btnStructure = new Button(parent, SWT.PUSH);
		btnStructure.setText("Structure");
		btnStructure.setEnabled(true);
		btnStructure.addListener (SWT.Selection, new Listener () {  
            public void handleEvent (Event e) {  
            	
			}
		});  
		
		Button btnSQL = new Button(parent, SWT.PUSH);
		btnSQL.setText(" SQL ");
		btnSQL.addListener (SWT.Selection, new Listener () {  
            public void handleEvent (Event e) {  
            	IWorkbenchPage pageSQL = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
            	try {
					pageSQL.showView(IDSQL);
            		//pageSQL.hideView(view);
            		//pageSQL.hideActionSet(IDSQL);
					
				} catch (PartInitException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	          		
			}
		});  
	}
	
	/**
	 * This method creates SWT for SQLview.
	 *  * @param parent
	 */
	public void callSQL(final Composite parent){
		
		parent.setLayout(new GridLayout(1, false));
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));
		
			cmdSQL= new StyledText(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
			cmdSQL.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));
			cmdSQL.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_BLACK));
			cmdSQL.setForeground(parent.getDisplay().getSystemColor(SWT.COLOR_GREEN));
			cmdSQL.setEditable(false);
	}
	
	
	public void showSQLarea(final Composite parent){
	    parent.setLayout(new GridLayout(1, false));
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));
		
			textArea = new StyledText(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
			textArea.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));
		 	
			
			Button btnSql = new Button(parent, SWT.NONE);
			btnSql.setText(" ! ");
			btnSql.addListener (SWT.Selection, new Listener () {  
				public void handleEvent (Event e) {  
				
					        				
				}
			});   

	} 
	
	/**
	 * This method returns the table in the database
	 * and filling up the Combo(cb_tables)
	 **/	
	
	public void getTables(String schema){
		btnData.setEnabled(true);
		cb_tables.setEnabled(true);
		
		Iterator itrTable = dbc.getTables(schema).iterator();
		while(itrTable.hasNext())
		{
			cb_tables.add(itrTable.next().toString());
			System.out.println(itrTable.next().toString());

		}
	}
	
	/**
	 * This method returns the table with the schemas
	 * and filling up the Combo(cb_schemas)
	 **/		
	
	public void getSchema(){
		cb_schemas.setEnabled(true);
		Iterator itr = dbc.getSchemas().iterator();
		while(itr.hasNext())
		{
			cb_schemas.add(itr.next().toString());
			System.out.println(itr.next().toString());

		}
	}
	
	/**
	 * This method creates a Table for DataTableView with your columns
	 **/
	
	public void dataTableView(Composite parent){
		 table = new Table(parent, SWT.SINGLE);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		 String[] columnNames = new String[] {"Column Name", "Type Name", "Length"};
		 int[] columnWidths = new int[] {
		         100, 100, 35, 75};
	      int[] columnAlignments = new int[] {
	    	         SWT.LEFT, SWT.LEFT, SWT.CENTER, SWT.CENTER};
		 for (int i = 0; i < columnNames.length; i++) {
	      tableColumn = 
	            new TableColumn(table, columnAlignments[i]);
	         tableColumn.setText(columnNames[i]);
	         tableColumn.setWidth(columnWidths[i]);
	      }
	
		
	}
	/**
	 * This method returns data Tables Description of database.
	 **/
	
	public void getTablesDescription(String schema, String tables){

	     TableItem item = new TableItem(table, SWT.NONE);
	 
	        ArrayList table_description  = dbc.getTablesDescription(schema, tables);
			ArrayList colname = (ArrayList) table_description.get(0);
			ArrayList typename = (ArrayList) table_description.get(1);
			ArrayList length = (ArrayList) table_description.get(2);
	
			/*
			 * There is a problem here. Because this(FOR) need fill up 
			 *  each line of DataTableView
			 */
			 
			for (int i = 0; i < rowCount; i++) {
				
	 			item.setText(new String[]{colname.get(i).toString(),
	 					typename.get(i).toString(),
	 					length.get(i).toString()
	 			
	 			});
	 		}
		}
}
	

        
	
		
