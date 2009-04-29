package org.jdamico.dbjmin.web.action;

import java.util.ArrayList;

import db2jmin.pojo.util.Constants;

public class ActionsFactory {

	public static JettyActions callJetty(ArrayList<String> form_data, int action) {
		switch(action){
			case Constants.SCHEMA_ACTION:
				return new SchemaAction(form_data);
			default:
				return null;
		}
	}

}
