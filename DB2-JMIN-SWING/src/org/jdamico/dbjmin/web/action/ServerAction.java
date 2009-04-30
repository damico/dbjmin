package org.jdamico.dbjmin.web.action;

import java.util.ArrayList;

import org.jdamico.dbjmin.web.JettyController;

public class ServerAction implements JettyActions {
	
	private ArrayList<String> form_data = null;

	public ServerAction(ArrayList<String> form_data) {
		this.form_data = form_data;
	}

	public boolean exec() {
		boolean ret = false;
		try {
			JettyController jController = new JettyController(8888);
			jController.init();
			ret = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public ArrayList getResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
