package org.jdamico.dbjmin.web.action;

import java.util.ArrayList;
import java.util.List;

import org.jdamico.dbjmin.crypto.DesEncrypter;

import db2jmin.pojo.data.DBconnector;
import db2jmin.pojo.data.Preferences;

public class WebStructureAction implements JettyActions {

	private Preferences formData = null;

	public WebStructureAction(Preferences form_data2) {
		this.formData = form_data2;
	}

	public boolean exec() {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	public ArrayList getResult() {
		DesEncrypter encDec = new DesEncrypter();
		DBconnector dbc = new DBconnector(encDec.transformFormData());
		return (ArrayList) dbc.getTablesDescription(formData.getHost(),
				formData.getPort());
	}

}
