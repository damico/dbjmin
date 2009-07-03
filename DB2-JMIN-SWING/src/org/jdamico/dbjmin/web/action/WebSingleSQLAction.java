package org.jdamico.dbjmin.web.action;

import java.util.ArrayList;

import org.jdamico.dbjmin.crypto.DesEncrypter;

import db2jmin.pojo.data.DBconnector;
import db2jmin.pojo.data.Preferences;

public class WebSingleSQLAction implements JettyActions {

	private Preferences form_data = null;

	public WebSingleSQLAction(Preferences form_data2) {
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
		return dbc.getSQL(form_data.getHost());
	}

}