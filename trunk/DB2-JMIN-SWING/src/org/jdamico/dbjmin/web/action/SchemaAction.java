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
		boolean ret =  false;
		String fs = SystemOper.singleton().getTempPath();
		fs = fs + Constants.TEMP_ALIVE_CREDENTIAL;
		boolean isPropWritten = ManageProperties.getInstance().write(fs, form_data);
		if(isPropWritten){
			ret = SystemOper.singleton().startBrowser(Constants.UNIXES_DEFAULT_BROWSERS, "http://127.0.0.1:8888/schemas");
		}
		return ret;
	}

	public ArrayList getResult() {
		return null;
	}
	
	

}
