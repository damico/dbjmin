package org.jdamico.dbjmin.test;

import java.io.IOException;
import java.util.ArrayList;

import junit.framework.TestCase;
import db2jmin.pojo.data.Preferences;
import db2jmin.pojo.util.InputDataValidation;
import db2jmin.pojo.util.PasswordField;

public class InputDataValidationTest extends TestCase {

	public void testParseDbUrl() {
		String url = "db2://jdamico@127.0.0.1:50000/ccmdb";
		InputDataValidation idv = new InputDataValidation();
		String[] input = idv.parseDbUrl(url);

		for (int i = 0; i < input.length; i++) {
			System.out.println(input[i]);
		}

		Preferences formData = new Preferences();

		formData.setHost(input[2]);
		formData.setPort(input[3]);
		formData.setDatabase(input[4]);
		formData.setUser(input[1]);

		char password[] = null;
		try {
			password = PasswordField.getPassword(System.in,
					"Enter your password: ");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		formData.setPassword(password.toString());
		formData.setDriver(input[0]);

		ArrayList val = idv.formValidationGen("pref_form", formData);

		for (int i = 0; i < val.size(); i++) {
			System.out.println(val.get(i));
		}
	}

}
