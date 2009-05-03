package org.jdamico.dbjmin.web.action;

import java.util.ArrayList;

import org.jdamico.dbjmin.crypto.DesEncrypter;

import db2jmin.pojo.data.DBconnector;

public class WebSampleDataAction implements JettyActions {
	
	private ArrayList<String> form_data = null; 

	public WebSampleDataAction(ArrayList<String> form_data) {
		this.form_data = form_data;
	}

	public boolean exec() {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList getResult() {
		DesEncrypter encDec = new DesEncrypter();
		DBconnector dbc = new DBconnector(encDec.transformFormData());
		return dbc.getTablesData(form_data.get(0), form_data.get(1));
	}

}
