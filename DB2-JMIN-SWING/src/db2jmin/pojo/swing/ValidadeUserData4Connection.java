package db2jmin.pojo.swing;

import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import db2jmin.pojo.data.Preferences;

public class ValidadeUserData4Connection {

	private Preferences form_data;
	private JTextField tf_server;
	private JTextField tf_port;
	private JTextField tf_db;
	private JTextField tf_user;
	private JPasswordField tf_passwd;
	private JComboBox drivers;
	private JTextArea logtext;

	public ValidadeUserData4Connection(Preferences form_data2,
			JTextField tf_server, JTextField tf_port, JTextField tf_db,
			JTextField tf_user, JPasswordField tf_passwd, JComboBox drivers,
			JTextArea logtext) {

		this.form_data = form_data2;
		this.tf_server = tf_server;
		this.tf_port = tf_port;
		this.tf_db = tf_db;
		this.tf_user = tf_user;
		this.tf_passwd = tf_passwd;
		this.drivers = drivers;
		this.logtext = logtext;

	}

	public boolean isValidated() {

		boolean ret = false;

		if (drivers.getSelectedItem().toString().contains("derby")
				&& tf_user.getText().equals("") && tf_passwd.equals("")) {
			tf_user.setText("null");
			tf_passwd.setText("null");
		}

		form_data.setRemoteDB(tf_server.getText());
		form_data.setPortdb(tf_port.getText());
		form_data.setNamedb(tf_db.getText());
		form_data.setUserdb(tf_user.getText());
		form_data.setPwddb(tf_passwd.getText());
		form_data.setDriver(drivers.getSelectedItem().toString());

		// form_data.add(tf_server.getText());
		// form_data.add(tf_port.getText());
		// form_data.add(tf_db.getText());
		// form_data.add(tf_user.getText());
		// form_data.add(tf_passwd.getText());
		// form_data.add(drivers.getSelectedItem().toString());
		ret = SwingUtils.singleton().isValidFormData(form_data, drivers,
				logtext);

		return ret;
	}

}
