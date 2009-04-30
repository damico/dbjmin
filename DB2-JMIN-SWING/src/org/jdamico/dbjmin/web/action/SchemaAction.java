package org.jdamico.dbjmin.web.action;

import java.util.ArrayList;

import db2jmin.pojo.util.Constants;
import db2jmin.pojo.util.ManageProperties;
import db2jmin.pojo.util.SystemOper;

public class SchemaAction implements JettyActions {

	private ArrayList<String> form_data = null;
	
	public SchemaAction(ArrayList<String> form_data){
		this.form_data = form_data;
	}
	
	public boolean exec() {
		String fs = SystemOper.singleton().getTempPath();
		fs = fs + Constants.TEMP_ALIVE_CREDENTIAL;
		boolean isPropWritten = ManageProperties.getInstance().write(fs, form_data);
		if(isPropWritten){
			SystemOper.singleton().startBrowser("iceweasel http://127.0.0.1:8888/schemas");
		}
		return isPropWritten;
	}

	public ArrayList getResult() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
