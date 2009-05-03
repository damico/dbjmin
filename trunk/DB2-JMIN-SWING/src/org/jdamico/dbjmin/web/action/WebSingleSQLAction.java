package org.jdamico.dbjmin.web.action;

import java.util.ArrayList;

import org.jdamico.dbjmin.crypto.DesEncrypter;

import db2jmin.pojo.data.DBconnector;
import db2jmin.pojo.util.Constants;
import db2jmin.pojo.util.Logger;

public class WebSingleSQLAction implements JettyActions {
	
	private Logger log = new Logger(Constants.LOGNAME);
	private ArrayList<String> form_data = null; 

	public WebSingleSQLAction(ArrayList<String> form_data) {
		this.form_data = form_data;
	}

	public boolean exec() {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList getResult() {
		DesEncrypter encDec = new DesEncrypter();
		DBconnector dbc = new DBconnector(encDec.transformFormData());
		return dbc.getSQL(form_data.get(0));
	}

}
