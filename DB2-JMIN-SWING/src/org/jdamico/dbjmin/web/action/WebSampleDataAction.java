package org.jdamico.dbjmin.web.action;

import java.util.ArrayList;

import org.jdamico.dbjmin.crypto.DesEncrypter;

import db2jmin.pojo.data.DBconnector;
import db2jmin.pojo.data.Preferences;

public class WebSampleDataAction implements JettyActions {

	private Preferences formData = null;

	public WebSampleDataAction(Preferences form_data2) {
		this.formData = form_data2;
	}

	public boolean exec() {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList getResult() {
		DesEncrypter encDec = new DesEncrypter();
		DBconnector dbc = new DBconnector(encDec.transformFormData());
		return dbc
				.getTablesData(formData.getHost(), formData.getPort());
	}

}
