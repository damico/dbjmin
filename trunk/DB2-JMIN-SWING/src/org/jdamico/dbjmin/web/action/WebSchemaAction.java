package org.jdamico.dbjmin.web.action;

import java.util.ArrayList;

import org.jdamico.dbjmin.crypto.DesEncrypter;

import db2jmin.pojo.data.DBconnector;

public class WebSchemaAction implements JettyActions {
	
	public WebSchemaAction(ArrayList<String> form_data) {
		// TODO Auto-generated constructor stub
	}

	public boolean exec() {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	public ArrayList getResult() {
		DesEncrypter encDec = new DesEncrypter();
		DBconnector dbc = new DBconnector(encDec.transformFormData());
		return (ArrayList) dbc.getSchemas();
	}

}
