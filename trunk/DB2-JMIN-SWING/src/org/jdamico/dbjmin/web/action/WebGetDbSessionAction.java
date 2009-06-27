package org.jdamico.dbjmin.web.action;

import java.util.ArrayList;

import org.jdamico.dbjmin.crypto.DesEncrypter;

import db2jmin.pojo.data.Preferences;

public class WebGetDbSessionAction implements JettyActions {

	public WebGetDbSessionAction(Preferences form_data) {
		// TODO Auto-generated constructor stub
	}

	public boolean exec() {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	public ArrayList getResult() {
		return (ArrayList) new DesEncrypter().transformFormData().toList();
	}

}
