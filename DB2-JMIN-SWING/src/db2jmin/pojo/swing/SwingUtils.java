/*******************************************************************************
 * DB2-JMIN / MULTI-JMIN (MULTI DB CLIENT)	
 * Copyright (c) 2007, 2009 Jose' Ricardo de Oliveira Damico and others.
 * This program is free software; you can redistribute it and/or		
 * modify it under the terms of the GNU General Public License			
 * as published by the Free Software Foundation; either version 2		
 * of the License, or (at your option) any later version.
 *
 *Contributors:
 *Jose' Ricardo de Oliveira Damico (jd.comment@gmail.com)
 *Argemiro Jos� de Lima - a.k.a Mir0 (mirolima@gmail.com)
 *Mario C. Ponciano - a.k.a Razec (mrazec@gmail.com) 
 *
 *	
 *******************************************************************************/
package db2jmin.pojo.swing;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import db2jmin.pojo.data.DBconnector;
import db2jmin.pojo.data.Preferences;
import db2jmin.pojo.util.Constants;
import db2jmin.pojo.util.InputDataValidation;
import db2jmin.pojo.util.Logger;
import db2jmin.pojo.util.RWhistory;
import db2jmin.pojo.util.SQLSyntax;
import db2jmin.pojo.util.SystemOper;

/**
 * This class intents to be a helper to Launch class, in order to set main UI
 * Desktop Window configuration
 * 
 * @author Jose Damico (damico@dcon.com.br)
 * */

public class SwingUtils {

	private static final SwingUtils getInstance = new SwingUtils();

	public static SwingUtils singleton() {
		return getInstance;
	}

	public JComboBox prepareDrivers(JComboBox drivers) {
		drivers
				.setBounds(40, 2, 60,
						Constants.FRAME_DEFAULT_BUTTON_HEIGHT_SIZE);
		drivers.setToolTipText("Select DataBase");
		ArrayList<String> defaultSrvs = new ArrayList<String>();
		defaultSrvs.add("db2");
		defaultSrvs.add("mysql");
		defaultSrvs.add("postgre");
		defaultSrvs.add("firebird");
		defaultSrvs.add("derby");
		defaultSrvs.add("derby-e");
		defaultSrvs.add("oracle");
		for (int i = 0; i < defaultSrvs.size(); i++) {
			drivers.insertItemAt(defaultSrvs.get(i), i);
		}
		return drivers;
	}

	public boolean isNewSetup(Preferences form_data, JTextField tf_server,
			JTextField tf_port, JTextField tf_db, JTextField tf_user,
			JPasswordField tf_passwd, JComboBox drivers) {
		boolean ret = true;

		if (form_data.isModified()) {
			if (form_data.getHost().equals(tf_server.getText())
					&& form_data.getPort().equals(tf_port.getText())
					&& form_data.getDatabase().toString().equals(tf_db.getText())
					&& form_data.getUser().equals(tf_user.getText())
					&& form_data.getPassword().equals(tf_passwd.getText())
					&& form_data.getDriver().equals(
							drivers.getSelectedItem().toString())) {
				ret = false;
			}
		}

		return ret;
	}

	public void hideSQLarea(JScrollPane scrollableSqlArea, JButton goSQL) {
		scrollableSqlArea.setVisible(false);
		goSQL.setVisible(false);
	}

	public void resetData(JComboBox schemas, JComboBox tables) {
		schemas.removeAllItems();
		tables.removeAllItems();
	}

	private RWhistory getRWhistory() {
		RWhistory rw = new RWhistory(SystemOper.singleton().getHomePath()
				+ Constants.HISTORY_FILE);
		return rw;
	}

	public void showSQLarea(JScrollPane scrollableSqlArea, JButton goSQL,
			final RSyntaxTextArea sqltext, JPanel panel) {
		scrollableSqlArea.setVisible(true);
		goSQL.setVisible(true);
		sqltext.setBounds(5, 57 + Constants.LOGTEXTH, 790, Constants.SQLTEXTH);
		sqltext.setLineWrap(true);
		sqltext.setWrapStyleWord(false);

		// set syntax highlight in the SQL editor
		sqltext.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SQL);

		sqltext
				.setToolTipText("Use the keyboard (arrow UP or arrow Down) to Move through history commands");
		sqltext.setText("Write here your query.");

		// send sql commands for analisy of SQLSyntax
		sqltext.getDocument().addDocumentListener(new SQLSyntax(sqltext));

		sqltext.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {
				// This event closes the open parenthesis
				String x = sqltext.getText();
				int pos = sqltext.getCaretPosition() - 1;
				if (ke.getKeyChar() == '(') {
					x += " )";
					sqltext.setText(x);
					sqltext.setCaretPosition(pos + 1);
				}
			}
		});

		sqltext.addKeyListener(new KeyAdapter() {
			private int count = getRWhistory().getTotalLines();

			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					count++;
					if (count > getRWhistory().getTotalLines()) {
						count = getRWhistory().getTotalLines();
						sqltext.setText("End of command's history!");
					} else {
						sqltext.setText(getRWhistory().readFile(count) + "\n");
					}

				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					count--;
					if (count < 0) {
						count = 0;
						sqltext.setText("End of command's history!");
					} else {
						sqltext.setText(getRWhistory().readFile(count) + "\n");
					}
				}

			}
		});

		scrollableSqlArea.setBounds(5, 57 + Constants.LOGTEXTH, 792,
				Constants.SQLTEXTH);
		goSQL.setText("!");
		goSQL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getRWhistory().writeFile(sqltext.getText());
			}
		});
		goSQL.setBounds(796, 57 + Constants.LOGTEXTH, 40,
				Constants.SQLTEXTH - 1);
		panel.add(scrollableSqlArea);
		panel.add(goSQL);
	}

	public void setGenericWindowSettings(JPanel panel, JComboBox drivers,
			JTextField tf_server, JTextField tf_port, final JTextField tf_db,
			JTextField tf_user, JPasswordField tf_passwd,
			Action actionValidadeAndConnect, JComboBox schemas,
			JComboBox tables, JButton validateAndConnectButton,
			JButton connectBtnWeb, Action actionGetTables, JButton data_button,
			JButton structure_button, JButton SQL_button, JTextArea logtext,
			JScrollPane scrollableTextArea, JFrame mainDesktopFrame,
			int os_specH, JButton table_button) {
		mainDesktopFrame.setVisible(true);
		panel.add(drivers);
		JLabel l_server = new JLabel("Srv: ");
		JLabel l_type = new JLabel("Type: ");

		l_type.setBounds(5, 2, 60, Constants.FRAME_DEFAULT_BUTTON_HEIGHT_SIZE);
		panel.add(l_type);

		l_server.setBounds(110, 2, 100,
				Constants.FRAME_DEFAULT_BUTTON_HEIGHT_SIZE);
		tf_server.setBounds(135, 2, 80,
				Constants.FRAME_DEFAULT_BUTTON_HEIGHT_SIZE);
		tf_server.setToolTipText("localhost");
		panel.add(l_server);
		panel.add(tf_server);

		JLabel l_port = new JLabel("Port: ");

		l_port.setBounds(220, 2, 100,
				Constants.FRAME_DEFAULT_BUTTON_HEIGHT_SIZE);
		tf_port.setBounds(251, 2, 45,
				Constants.FRAME_DEFAULT_BUTTON_HEIGHT_SIZE);
		tf_port.setToolTipText("Insert Port");
		panel.add(l_port);
		panel.add(tf_port);

		JLabel l_db = new JLabel("db: ");

		l_db.setBounds(300, 2, 100, Constants.FRAME_DEFAULT_BUTTON_HEIGHT_SIZE);
		tf_db.setBounds(325, 2, 50, Constants.FRAME_DEFAULT_BUTTON_HEIGHT_SIZE);
		tf_db.setToolTipText("Add DataBase");
		panel.add(l_db);
		panel.add(tf_db);

		JButton btnOpenDb = new JButton(actionGetTables);
		btnOpenDb.setText(">");
		btnOpenDb.setBounds(375, 2, 50,
				Constants.FRAME_DEFAULT_BUTTON_HEIGHT_SIZE);
		btnOpenDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int res = fc.showOpenDialog(null);
				if (res == JFileChooser.APPROVE_OPTION) {
					File arq = fc.getSelectedFile();
					tf_db.setText(arq.getAbsolutePath());
				}
			}
		});
		panel.add(btnOpenDb);

		JLabel l_user = new JLabel("User: ");

		l_user.setBounds(430, 2, 100,
				Constants.FRAME_DEFAULT_BUTTON_HEIGHT_SIZE);
		tf_user.setBounds(470, 2, 100,
				Constants.FRAME_DEFAULT_BUTTON_HEIGHT_SIZE);
		tf_user.setToolTipText("Login");
		panel.add(l_user);
		panel.add(tf_user);

		JLabel l_passwd = new JLabel("Passwd: ");

		tf_passwd.setEchoChar('#');
		l_passwd.setBounds(575, 2, 100,
				Constants.FRAME_DEFAULT_BUTTON_HEIGHT_SIZE);
		tf_passwd.setBounds(630, 2, 100,
				Constants.FRAME_DEFAULT_BUTTON_HEIGHT_SIZE);

		tf_passwd.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),
				actionValidadeAndConnect);

		panel.add(l_passwd);
		panel.add(tf_passwd);

		JLabel l_schemas = new JLabel("Schemas: ");
		l_schemas.setBounds(5, 32, 100,
				Constants.FRAME_DEFAULT_BUTTON_HEIGHT_SIZE);
		panel.add(l_schemas);

		schemas.setBounds(65, 32, 140,
				Constants.FRAME_DEFAULT_BUTTON_HEIGHT_SIZE);
		panel.add(schemas);
		schemas.setEnabled(false);

		JLabel l_tables = new JLabel("Tables: ");
		l_tables.setBounds(264, 32, 100,
				Constants.FRAME_DEFAULT_BUTTON_HEIGHT_SIZE);
		panel.add(l_tables);

		tables.setBounds(310, 32, 188,
				Constants.FRAME_DEFAULT_BUTTON_HEIGHT_SIZE);
		panel.add(tables);
		tables.setEnabled(false);

		validateAndConnectButton.setText("D");
		validateAndConnectButton.setToolTipText("Desktop Use");
		validateAndConnectButton.setBounds(735, 2, 50,
				Constants.FRAME_DEFAULT_BUTTON_HEIGHT_SIZE);
		panel.add(validateAndConnectButton);

		connectBtnWeb.setText("W");
		connectBtnWeb.setToolTipText("Web Use");
		connectBtnWeb.setBounds(790, 2, 50,
				Constants.FRAME_DEFAULT_BUTTON_HEIGHT_SIZE);
		panel.add(connectBtnWeb);

		table_button.setAction(actionGetTables);
		table_button.setText(">");
		table_button.setBounds(210, 32, 50,
				Constants.FRAME_DEFAULT_BUTTON_HEIGHT_SIZE);
		panel.add(table_button);

		JButton viewLog = new JButton();
		viewLog.setText("Log");
		viewLog.setBounds(503, 32, 58,
				Constants.FRAME_DEFAULT_BUTTON_HEIGHT_SIZE);
		ActionListener actionShowLog = getActionShowLog();
		viewLog.addActionListener(actionShowLog);
		panel.add(viewLog);

		data_button.setText("Data");
		data_button.setBounds(564, 32, 63,
				Constants.FRAME_DEFAULT_BUTTON_HEIGHT_SIZE);
		panel.add(data_button);
		data_button.setEnabled(false);

		structure_button.setText("Structure");
		structure_button.setBounds(631, 32, 91,
				Constants.FRAME_DEFAULT_BUTTON_HEIGHT_SIZE);
		panel.add(structure_button);
		structure_button.setEnabled(false);

		SQL_button.setText("SQL");
		SQL_button.setBounds(725, 32, 112,
				Constants.FRAME_DEFAULT_BUTTON_HEIGHT_SIZE);
		panel.add(SQL_button);
		SQL_button.setEnabled(false);

		logtext.setBounds(5, 57, 800, Constants.LOGTEXTH);
		logtext.setBackground(Color.BLACK);
		logtext.setForeground(Color.CYAN);
		logtext.setLineWrap(true);
		logtext.setWrapStyleWord(false);

		scrollableTextArea.setBounds(5, 57, 833, Constants.LOGTEXTH);

		panel.add(scrollableTextArea);

		mainDesktopFrame.setIconImage(getDefaultIcon());

		mainDesktopFrame.setTitle(Constants.APPNAME);
		mainDesktopFrame.getContentPane().add(panel);
		mainDesktopFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainDesktopFrame.setResizable(false);
		// Set initial size

		mainDesktopFrame.setSize(Constants.FRAME_DEFAULT_H_SIZE,
				Constants.FRAME_DEFAULT_MIN_HEIGHT_SIZE + os_specH);

	}

	public Image getDefaultIcon() {
		String iconPath = Constants.APPLOGO;
		if (!SystemOper.singleton().isWindows())
			iconPath = "/usr/lib/dbjmin/" + iconPath;
		return Toolkit.getDefaultToolkit().getImage(iconPath);
	}

	private AbstractAction getActionShowLog() {

		return new SwingLogAction();
	}

	public void callSql(JTextArea sqltext, ArrayList data, JTable maintable,
			JScrollPane scrollableTable, JTextArea logtext, JPanel panel) {

		Logger log = new Logger(Constants.LOGNAME);
		StringBuffer sb = new StringBuffer();
		String element = "";
		sb.append(logtext.getText() + "\n");
		sb.append(sqltext.getText() + "\n");
		ArrayList columns_data = new ArrayList();
		ArrayList columns_name = (ArrayList) data.get(0);
		ArrayList set = (ArrayList) data.get(1);
		ArrayList errors = (ArrayList) data.get(2);

		String[] cols = (String[]) Array.newInstance(String.class, columns_name
				.size());
		String[] cols_data = (String[]) Array.newInstance(String.class,
				columns_name.size());

		for (int j = 0; j < columns_name.size(); j++) {
			// log.AddLogLine("columns_name.get(j): "+columns_name.get(j).toString());
			try {
				cols[j] = columns_name.get(j).toString();
			} catch (NullPointerException e) {
				log.AddLogLine("columns_name.get(j): "
						+ columns_name.get(j).toString());
			}
		}

		maintable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {}, cols));
		javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel) maintable
				.getModel();
		maintable.doLayout();
		maintable.setAutoCreateColumnsFromModel(true);
		maintable.setBounds(5, 57 + Constants.LOGTEXTH + Constants.SQLTEXTH,
				Constants.TABLE_RESULT_H_SIZE,
				480 - (Constants.LOGTEXTH + Constants.SQLTEXTH));
		scrollableTable.setBounds(5, 57 + Constants.LOGTEXTH
				+ Constants.SQLTEXTH, Constants.TABLE_RESULT_H_SIZE,
				480 - (Constants.LOGTEXTH + Constants.SQLTEXTH));
		maintable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for (int i = 0; i < set.size(); i++) {

			columns_data.clear();
			columns_data = (ArrayList) set.get(i);

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

		if (errors.size() > 0) {

			sb.append("ERROR\n");
			String node = null;
			Iterator iter = errors.iterator();
			while (iter.hasNext()) {
				node = iter.next().toString();
				sb.append(node + "\n");
			}

			logtext.setForeground(Color.RED);
			logtext.setText(sb.toString());
			scrollableTable.setVisible(false);
		} else {
			scrollableTable.setVisible(true);
			panel.add(scrollableTable);

			logtext.setForeground(Color.GREEN);
			logtext.setText(sb.toString());
		}
	}

	public javax.swing.table.DefaultTableModel setUITable(String[] cols,
			JTable maintable, JScrollPane scrollableTable) {
		maintable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {}, cols));
		javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel) maintable
				.getModel();
		maintable.doLayout();
		maintable.setAutoCreateColumnsFromModel(true);
		maintable.setBounds(5, 57 + Constants.LOGTEXTH + Constants.SQLTEXTH,
				Constants.TABLE_RESULT_H_SIZE,
				480 - (Constants.LOGTEXTH + Constants.SQLTEXTH));
		scrollableTable.setBounds(5, 57 + Constants.LOGTEXTH
				+ Constants.SQLTEXTH, Constants.TABLE_RESULT_H_SIZE,
				480 - (Constants.LOGTEXTH + Constants.SQLTEXTH));
		maintable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		return dtm;
	}

	public void refreshTable(JTable maintable, JScrollPane scrollableTable,
			JPanel panel) {
		maintable.setVisible(true);
		scrollableTable.setVisible(true);
		panel.remove(scrollableTable);
		panel.add(scrollableTable);

	}

	public boolean isValidFormData(Preferences form_data, JComboBox drivers,
			JTextArea logtext) {
		boolean ret = false;
		List result_data = null;
		InputDataValidation idv = new InputDataValidation();

		if (drivers.getSelectedItem().toString().equals("derby-e")) {
			result_data = idv.formValidationDerby("pref_form", form_data);
		} else {
			result_data = idv.formValidationGen("pref_form", form_data);
		}

		StringBuffer sb = new StringBuffer();
		if (result_data.get(0).equals(false)) {
			sb.append("ERROR\n");
			for (int sc = 1; sc < result_data.size(); sc++) {
				sb.append(result_data.get(sc).toString() + "\n");
			}

			logtext.setForeground(Color.RED);
			logtext.setText(sb.toString());
		} else {
			ret = true;
		}
		return ret;
	}

	public boolean isValidFormDataForPreServer(Preferences form_data) {
		boolean ret = false;
		ArrayList<String> result_data = null;
		InputDataValidation idv = new InputDataValidation();
		result_data = idv.testTextFields(form_data);
		StringBuffer sb = new StringBuffer();
		if (result_data.size() > 0) {
			sb.append("ERROR\n");
			for (int sc = 0; sc < result_data.size(); sc++) {
				sb.append(result_data.get(sc).toString() + "\n");
			}
			JOptionPane.showMessageDialog(null, sb.toString());

		} else {
			ret = true;
		}
		return ret;
	}

	public void setSchemasDropDown(Preferences form_data, JComboBox schemas) {

		DBconnector dbc = new DBconnector(form_data);

		int counter = 0;
		Iterator<String> it = dbc.getSchemas().getSchemas().iterator();
		while (it.hasNext()) {
			schemas.insertItemAt(it.next(), counter);
			counter++;
		}

	}
}
