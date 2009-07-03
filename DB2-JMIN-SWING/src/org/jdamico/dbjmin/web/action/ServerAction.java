package org.jdamico.dbjmin.web.action;

import java.util.ArrayList;

import org.jdamico.dbjmin.web.JettyController;

import db2jmin.pojo.data.Preferences;

public class ServerAction implements JettyActions {

	private Preferences formData = null;

	public ServerAction(Preferences form_data2) {
		this.formData = form_data2;
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
