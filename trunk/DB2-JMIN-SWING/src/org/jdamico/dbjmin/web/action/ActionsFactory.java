package org.jdamico.dbjmin.web.action;

import java.util.ArrayList;

import db2jmin.pojo.util.Constants;

public class ActionsFactory {

	public static JettyActions callJetty(ArrayList<String> form_data, int action) {
		switch(action){
			case Constants.D_SERVER_ACTION:
				return new ServerAction(form_data);
			case Constants.D_SCHEMA_ACTION:
				return new SchemaAction(form_data);
			case Constants.W_SCHEMA_ACTION:
				return new WebSchemaAction(form_data);
			case Constants.W_TABLE_ACTION:
				return new WebTableAction(form_data);
			default:
				return null;
		}
	}

}
