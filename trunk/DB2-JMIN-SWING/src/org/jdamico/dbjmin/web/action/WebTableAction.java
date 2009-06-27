package org.jdamico.dbjmin.web.action;

import java.util.ArrayList;

import org.jdamico.dbjmin.crypto.DesEncrypter;

import db2jmin.pojo.data.DBconnector;
import db2jmin.pojo.data.Preferences;

public class WebTableAction implements JettyActions {

	private Preferences form_data = null; 
	
	public WebTableAction(Preferences form_data2) {
		this.form_data = form_data2;
	}

	public boolean exec() {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	public ArrayList getResult() {
		DesEncrypter encDec = new DesEncrypter();
		DBconnector dbc = new DBconnector(encDec.transformFormData());
		return (ArrayList) dbc.getTables(form_data.getRemoteDB());
	}

}
