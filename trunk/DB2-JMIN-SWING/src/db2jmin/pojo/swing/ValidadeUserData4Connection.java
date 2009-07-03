package db2jmin.pojo.swing;

import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import db2jmin.pojo.data.Preferences;

public class ValidadeUserData4Connection {

	private Preferences formData;
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

		this.formData = form_data2;
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

		formData.setHost(tf_server.getText());
		formData.setPort(tf_port.getText());
		formData.setDatabase(tf_db.getText());
		formData.setUser(tf_user.getText());
		formData.setPassword(tf_passwd.getText());
		formData.setDriver(drivers.getSelectedItem().toString());

		// formData.add(tf_server.getText());
		// formData.add(tf_port.getText());
		// formData.add(tf_db.getText());
		// formData.add(tf_user.getText());
		// formData.add(tf_passwd.getText());
		// formData.add(drivers.getSelectedItem().toString());
		ret = SwingUtils.singleton().isValidFormData(formData, drivers,
				logtext);

		return ret;
	}

}
