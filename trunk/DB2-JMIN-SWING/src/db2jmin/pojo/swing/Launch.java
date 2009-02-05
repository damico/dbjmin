/* ******************************************************************* 	*/
/* DB2-JMIN / MULTI-JMIN (MULTI DB CLIENT)								*/
/* Copyright (C) 2007  Jose' Ricardo de Oliveira Damico					*/
/* damico@dcon.com.br													*/
/* 																		*/
/* This program is free software; you can redistribute it and/or		*/
/* modify it under the terms of the GNU General Public License			*/
/* as published by the Free Software Foundation; either version 2		*/
/* of the License, or (at your option) any later version.				*/
/* 																		*/
/* This program is distributed in the hope that it will be useful,		*/
/* but WITHOUT ANY WARRANTY; without even the implied warranty of		*/
/* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the		*/
/* GNU General Public License for more details.							*/
/* 																		*/
/* You should have received a copy of the GNU General Public License	*/
/* along with this program; if not, write to the Free Software			*/
/* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 			*/
/* MA  02110-1301, USA.													*/
/* ******************************************************************** */

package db2jmin.pojo.swing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import db2jmin.pojo.data.DBconnector;
import db2jmin.pojo.util.Constants;
import db2jmin.pojo.util.ExecuteUpdateObject;
import db2jmin.pojo.util.InputDataValidation;
import db2jmin.pojo.util.Logger;
import db2jmin.pojo.util.PreServers;
import db2jmin.pojo.util.SQLParser;
import db2jmin.pojo.util.ServersObject;
import db2jmin.pojo.util.SystemOper;

/**
 * This class is used to start application and to build main UI Desktop Window
 * 
 * @author Jose Damico (damico@dcon.com.br)
 */

public class Launch {

	public static void main(String[] args) {
		
		String preServerFile = Constants.PRESERVER_FILE;

		if (SystemOper.singleton().isWindows()) {
			SystemOper.singleton().setWinUIManager();
			os_specH = 5;
		}else{
			/* verify folder */
			preServerFile = "/home/"+SystemOper.singleton().execExtCommand("whoami")+"/.dbjmin/"+Constants.PRESERVER_FILE;
			File preServer = new File(preServerFile);
			if( !preServer.isFile() ){
				/* create folder */
				String preServerFolder = preServerFile.replaceAll("/"+Constants.PRESERVER_FILE, "");
				File preServerFolderDir = new File(preServerFolder);
				boolean successFolder = preServerFolderDir.mkdir();
				log.AddLogLine(preServerFolder+" - "+successFolder);
				/* copy file */
				if( successFolder || preServerFolderDir.isDirectory() ){
					try{
						InputStream in = new FileInputStream("/usr/lib/dbjmin/"+Constants.PRESERVER_FILE);
						OutputStream out = new FileOutputStream(preServerFile);

						// Transfer bytes from in to out
						byte[] buf = new byte[1024];
						int len;
						while ((len = in.read(buf)) > 0) {
							out.write(buf, 0, len);
						}
						in.close();
						out.close();
					}catch(IOException ioe){
						log.AddLogLine("IOException: "+ioe.getMessage());
					}
				}
			}
		}
		
		
		
		if (PreServers.singleton().isPreServer(preServerFile)) {
			try {
				srvObjArrLst = PreServers.singleton().getPreServers(
						preServerFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		

		drivers = SwingUtils.singleton().prepareDrivers(drivers);

		if (srvObjArrLst != null && srvObjArrLst.size() > 0) {
			for (int i = 0; i < srvObjArrLst.size(); i++) {
				ServersObject srvObj = srvObjArrLst.get(i);
				drivers.insertItemAt("*" + srvObj.getDbname(), drivers
						.getComponentCount()
						+ i);
			}
		}

		drivers.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				driversActPerf(e);

			}

			private void driversActPerf(ActionEvent e) {

				if (srvObjArrLst != null && srvObjArrLst.size() > 0) {
					for (int i = 0; i < srvObjArrLst.size(); i++) {
						ServersObject srvObj = srvObjArrLst.get(i);
						if (srvObj.getDbname().equals(
								drivers.getSelectedItem().toString().substring(
										1))) {
							drivers.setSelectedItem(srvObj.getType());
							tf_server.setText(srvObj.getHost());
							tf_port.setText(srvObj.getPort());
							tf_db.setText(srvObj.getDbname());
							tf_user.setText(srvObj.getUser());
						}
					}
				}
			}

		}

		);

		SwingUtils.singleton().setGenericWindowSettings(panel, drivers,
				tf_server, tf_port, tf_db, tf_user, tf_passwd,
				actionValidadeAndConnect, schemas, tables,
				validateAndConnectButton, actionGetTables, data_button,
				structure_button, SQL_button, logtext, scrollableTextArea,
				mainDesktopFrame, os_specH);

		tf_passwd
				.setToolTipText("Press F3 to save this dababase credentials on preServers.xml file");

		tf_passwd.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				tf_passwdKeyPressed(evt);
			}

			private void tf_passwdKeyPressed(KeyEvent evt) {

				if (evt.getKeyCode() == KeyEvent.VK_F3) {

					form_data.add(tf_server.getText());
					form_data.add(tf_port.getText());
					form_data.add(tf_db.getText());
					form_data.add(tf_user.getText());
					form_data.add(tf_passwd.getText());
					form_data.add(drivers.getSelectedItem().toString());

					if (SwingUtils.singleton().isValidFormDataForPreServer(
							form_data)
							&& srvObjArrLst != null && srvObjArrLst.size() > 0) {
						int i = 0;
						ServersObject srvObj = null;
						boolean newServer = true;
						while (i < srvObjArrLst.size()) {
							srvObj = srvObjArrLst.get(i);
							if (srvObj.getDbname().equalsIgnoreCase(
									tf_db.getText().trim())) {
								logtext
										.setText("This credential already exist!");
								logtext.setForeground(Color.RED);
								newServer = false;
								break;
							}

							i++;
						}
						if (newServer) {
							int selectedOption = JOptionPane
									.showConfirmDialog(
											null,
											"Do you really want to save these DB's credentials?",
											"Warning!",
											JOptionPane.YES_NO_OPTION);
							if (selectedOption == JOptionPane.YES_OPTION)
								PreServers.singleton().setPreServers(
										drivers.getSelectedItem().toString(),
										tf_server.getText(), tf_port.getText(),
										tf_db.getText(), tf_user.getText());
						}
					}
				}
			}

		});

	}

	public static Action actionValidadeAndConnect = new AbstractAction(
			"Action InputValidation / Schemas") {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5388887340394323000L;

		// This is an instance initializer; it is executed just after the
		// constructor of the superclass is invoked
		{
			// The following values are completely optional

			// Set an accelerator key; this value is used by menu items
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F2"));

		}

		// This method is called when the action is invoked
		public void actionPerformed(ActionEvent evt) {
			// Perform action
			if (!open
					&& !connected
					|| SwingUtils.singleton().isNewSetup(form_data, tf_server,
							tf_port, tf_db, tf_user, tf_passwd, drivers)) {
				SwingUtils.singleton().hideSQLarea(scrollableSqlArea, goSQL);
				SwingUtils.singleton().resetData(schemas, tables);
				form_data.clear();
				ArrayList result_data = new ArrayList();

				if (drivers.getSelectedItem() != null) {
					if (drivers.getSelectedItem().toString().contains("derby")
							&& tf_user.getText().equals("")
							&& tf_passwd.equals("")) {
						tf_user.setText("null");
						tf_passwd.setText("null");
					}

					form_data.add(tf_server.getText());
					form_data.add(tf_port.getText());
					form_data.add(tf_db.getText());
					form_data.add(tf_user.getText());
					form_data.add(tf_passwd.getText());
					form_data.add(drivers.getSelectedItem().toString());

					if (SwingUtils.singleton().isValidFormData(form_data,
							drivers, logtext)) {
						schemas.setEnabled(true);

						logtext.setForeground(Color.CYAN);
						logtext.setText("Connected\n");
						open = true;
						connected = true;
						validateAndConnectButton.setText("<");
						DBconnector dbc = new DBconnector(form_data);
						schemas_array = dbc.getSchemas();

						int counter = 0;
						Iterator it = schemas_array.iterator();
						while (it.hasNext()) {

							schemas.insertItemAt(it.next().toString(), counter);
						}
					}
					SQL_button.setEnabled(true);
					mainDesktopFrame.setSize(798, 90 + Constants.LOGTEXTH);
				} else {
					drivers.setBackground(Color.RED);
				}
			} else if (open && connected) {
				mainDesktopFrame.setSize(Constants.frameWidth,
						Constants.frameHeight + os_specH);
				open = false;
				validateAndConnectButton.setText(">");
			} else if (!open && connected) {
				mainDesktopFrame.setSize(798, 570 + os_specH);
				open = true;
				validateAndConnectButton.setText("<");
			}

		}
	};

	public static Action actionGetTables = new AbstractAction("Action Tables") {

		public void actionPerformed(ActionEvent evt) {
			tables.removeAllItems();

			DBconnector dbc = new DBconnector(form_data);
			ArrayList<String> tables_array = dbc.getTables(schemas
					.getSelectedItem().toString());
			int counter = 0;
			Iterator<String> it = tables_array.iterator();
			while (it.hasNext()) {

				tables.insertItemAt(it.next().toString(), counter);
			}
			if (tables_array.size() > 0) {

				tables.setBackground(Color.GREEN);
				logtext.setText(tables_array.size()
						+ " table(s) found for this schema\n");
				logtext.setForeground(Color.GREEN);
			} else {
				tables.setBackground(Color.RED);
				logtext.setForeground(Color.RED);
				logtext.setText("No tables found for this schema\n");
			}
			tables.setEnabled(true);
			data_button.setEnabled(true);
			structure_button.setEnabled(true);
		}
	};

	public static Action action3 = new AbstractAction("Action Table Data") {

		// This method is called when the action is invoked
		public void actionPerformed(ActionEvent evt) {

			try {

				scrollableTable.setVisible(false);
				ArrayList data = new ArrayList();
				DBconnector DBc = new DBconnector(form_data);
				data = DBc.getTablesData(schemas.getSelectedItem().toString(),
						tables.getSelectedItem().toString());
				ArrayList columns_name = new ArrayList();
				ArrayList columns_data = new ArrayList();
				ArrayList set = new ArrayList();
				columns_name = (ArrayList) data.get(0);
				set = (ArrayList) data.get(1);

				String[] cols = (String[]) Array.newInstance(String.class,
						columns_name.size());
				String[] cols_data = (String[]) Array.newInstance(String.class,
						columns_name.size());

				for (int j = 0; j < columns_name.size(); j++) {
					cols[j] = columns_name.get(j).toString();
				}

				maintable.setModel(new javax.swing.table.DefaultTableModel(
						new Object[][] {}, cols));
				javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel) maintable
						.getModel();
				maintable.doLayout();
				maintable.setAutoCreateColumnsFromModel(true);

				scrollableTable.setBounds(5, 57 + Constants.LOGTEXTH
						+ Constants.SQLTEXTH, 782,
						480 - (Constants.LOGTEXTH + Constants.SQLTEXTH));
				int setSize = set.size();
				if (setSize > 0) {
					for (int i = 0; i < setSize; i++) {
						columns_data.clear();
						columns_data = (ArrayList) set.get(i);
						String element = "";
						for (int j = 0; j < columns_data.size(); j++) {
							try {
								element = columns_data.get(j).toString();
							} catch (NullPointerException e) {
								// log.AddLogLine("NullPointerException: "+e);
							}
							cols_data[j] = element;

						}
						dtm.addRow(cols_data);
						columns_data.clear();
					}
				} else {
					logtext.setText("Table empty.\n");
					logtext.setForeground(Color.YELLOW);
				}

				scrollableTable.setVisible(true);
				panel.add(scrollableTable);
				SwingUtils.singleton().showSQLarea(scrollableSqlArea, goSQL,
						sqltext, panel);
				mainDesktopFrame.setSize(798, 570);

			} catch (NullPointerException e) {

				logtext.setForeground(Color.YELLOW);
				logtext.setText("NO TABLE SELECTED!");
			}

		}
	};

	/**
	 * 
	 */

	public static Action mountSqlArea = new AbstractAction("Action SQL Area") {
		// This is an instance initializer; it is executed just after the
		// constructor of the superclass is invoked
		{

		}

		// This method is called when the action is invoked
		public void actionPerformed(ActionEvent evt) {

			SwingUtils.singleton().showSQLarea(scrollableSqlArea, goSQL,
					sqltext, panel);
			mainDesktopFrame.setSize(798, 570);

		}
	};

	public static Action executeSql = new AbstractAction("Action SQL Go!") {
		// This is an instance initializer; it is executed just after the
		// constructor of the superclass is invoked
		{

		}

		// This method is called when the action is invoked
		public void actionPerformed(ActionEvent evt) {

			scrollableTable.setVisible(false);
			javax.swing.table.DefaultTableModel dtm = null;

			DBconnector DBc = new DBconnector(form_data);

			if (sqltext.getText().contains(";")) {
				/* Discover how many statements was inserted */
				String preSql = sqltext.getText().replaceAll("\n", "");

				ArrayList<String> sqlStmts = SQLParser.singleton().breakSql(
						preSql);

				/* validates executeUpdate from sqlStmts */

				InputDataValidation idv = new InputDataValidation();
				if (idv.isExecuteUpdateValidArray(sqlStmts)) {
					log.AddLogLine("sqlStmts.size(): " + sqlStmts.size());
					String[] cols = { "#", "Status", "Statement" };
					dtm = SwingUtils.singleton().setUITable(cols, maintable,
							scrollableTable);
					for (int i = 0; i < sqlStmts.size(); i++) {
						String[] cols_data = new String[3];
						ExecuteUpdateObject eUO = DBc.getBatchSQL(sqlStmts
								.get(i));
						cols_data[0] = String.valueOf(i);
						cols_data[1] = eUO.getStatus();
						cols_data[2] = eUO.getStatement();
						dtm.addRow(cols_data);
					}
				} else {
					String error = "Invalid set of Statements.";
					logtext.setForeground(Color.RED);
					logtext.setText(error);
					log.AddLogLine(error);
				}
			} else {
				ArrayList data = data = DBc.getSQL(sqltext.getText());
				SwingUtils.singleton().callSql(sqltext, data, maintable,
						scrollableTable, logtext, panel);
			}

			SwingUtils.singleton().refreshTable(maintable, scrollableTable,
					panel);

		}
	};

	public static Action action6 = new AbstractAction("Action Table Structure") {
		// This is an instance initializer; it is executed just after the
		// constructor of the superclass is invoked
		{

		}

		// This method is called when the action is invoked
		public void actionPerformed(ActionEvent evt) {

			try {

				scrollableTable.setVisible(false);

				DBconnector DBc = new DBconnector(form_data);
				ArrayList<ArrayList> table_description = DBc
						.getTablesDescription(schemas.getSelectedItem()
								.toString(), tables.getSelectedItem()
								.toString());

				ArrayList colname = (ArrayList) table_description.get(0);
				ArrayList typename = (ArrayList) table_description.get(1);
				ArrayList length = (ArrayList) table_description.get(2);

				String[] cols = (String[]) Array.newInstance(String.class, 3);
				cols[0] = "Column Name";
				cols[1] = "Type Name";
				cols[2] = "Length";

				maintable.setModel(new javax.swing.table.DefaultTableModel(
						new Object[][] {}, cols));
				javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel) maintable
						.getModel();
				maintable.doLayout();
				maintable.setAutoCreateColumnsFromModel(true);
				maintable.setBounds(5, 57 + Constants.LOGTEXTH
						+ Constants.SQLTEXTH, 782,
						480 - (Constants.LOGTEXTH + Constants.SQLTEXTH));
				scrollableTable.setBounds(5, 57 + Constants.LOGTEXTH
						+ Constants.SQLTEXTH, 782,
						480 - (Constants.LOGTEXTH + Constants.SQLTEXTH));

				for (int i = 0; i < colname.size(); i++) {
					try {
						dtm.addRow(new Object[] { colname.get(i).toString(),
								typename.get(i).toString(),
								length.get(i).toString() });
					} catch (NullPointerException e) {
						log.AddLogLine("NullPointerException: " + e);
					}
				}

				scrollableTable.setVisible(true);
				panel.add(scrollableTable);
				SwingUtils.singleton().showSQLarea(scrollableSqlArea, goSQL,
						sqltext, panel);
				mainDesktopFrame.setSize(798, 570);

			} catch (NullPointerException e) {

				logtext.setForeground(Color.YELLOW);
				logtext.setText("NO TABLE SELECTED!");
			}

		}
	};

	public static JPanel panel = new JPanel(null);
	public static JFrame mainDesktopFrame = new JFrame();
	public static JComboBox schemas = new JComboBox();
	public static JComboBox tables = new JComboBox();
	public static JComboBox drivers = new JComboBox();
	public static JButton SQL_button = new JButton(mountSqlArea);
	public static JButton goSQL = new JButton(executeSql);
	public static JButton structure_button = new JButton(action6);
	public static JButton data_button = new JButton(action3);
	public static JTextArea logtext = new JTextArea();
	public static JTextArea sqltext = new JTextArea();
	public static JScrollPane scrollableTextArea = new JScrollPane(logtext);
	public static JScrollPane scrollableSqlArea = new JScrollPane(sqltext);
	public static JTextField tf_server = new JTextField("");
	public static JTextField tf_port = new JTextField("");
	public static JTextField tf_db = new JTextField("");
	public static JTextField tf_user = new JTextField("");
	public static JPasswordField tf_passwd = new JPasswordField("");
	public static ArrayList<String> form_data = new ArrayList<String>();
	public static ArrayList<String> schemas_array = new ArrayList<String>();
	public static JTable maintable = new JTable();
	public static JScrollPane scrollableTable = new JScrollPane(maintable);
	public static Logger log = new Logger(Constants.LOGNAME);
	public static JButton validateAndConnectButton = new JButton(
			actionValidadeAndConnect);
	public static boolean open = false;
	public static boolean connected = false;
	public static int os_specH = Constants.DEFAULT_OS_SPEC_H;
	public static ArrayList<ServersObject> srvObjArrLst = null;
}
