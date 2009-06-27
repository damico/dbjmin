package org.jdamico.dbjmin.web.action;

import java.util.ArrayList;

import db2jmin.pojo.data.Preferences;
import db2jmin.pojo.util.ManageProperties;

public class WebCloseSessionAction implements JettyActions {

	public WebCloseSessionAction(Preferences form_data) {
		// TODO Auto-generated constructor stub
	}

	public boolean exec() {
		boolean ret = false;
		ret = ManageProperties.getInstance().remove();
		System.exit(0);
		return ret;
	}

	public ArrayList getResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
