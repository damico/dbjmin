package org.jdamico.dbjmin.web.action;

import java.util.ArrayList;

import db2jmin.pojo.util.Constants;
import db2jmin.pojo.util.ManageProperties;
import db2jmin.pojo.util.SystemOper;

public class WebCliAction implements JettyActions {

	private ArrayList<String> form_data = null;
	
	public WebCliAction(ArrayList<String> form_data) {
		this.form_data = form_data;
	}
	
	public boolean exec() {
		
		String fs = SystemOper.singleton().getTempPath();
		fs = fs + Constants.TEMP_ALIVE_CREDENTIAL;
		boolean isPropWritten = ManageProperties.getInstance().write(fs, form_data);
		
		return isPropWritten;
	}

	public ArrayList getResult() {
		return null;
	}

}
