package org.jdamico.dbjmin.web.action;

import java.util.ArrayList;

import org.jdamico.dbjmin.crypto.DesEncrypter;

public class WebGetDbSessionAction implements JettyActions {

	public WebGetDbSessionAction(ArrayList<String> form_data) {
		// TODO Auto-generated constructor stub
	}

	public boolean exec() {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList getResult() {
		return new DesEncrypter().transformFormData();
	}

}
