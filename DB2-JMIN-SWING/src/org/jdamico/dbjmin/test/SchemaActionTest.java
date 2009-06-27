package org.jdamico.dbjmin.test;

import junit.framework.TestCase;

import org.jdamico.dbjmin.web.action.ActionsFactory;
import org.jdamico.dbjmin.web.action.JettyActions;

import db2jmin.pojo.data.Preferences;
import db2jmin.pojo.util.Constants;

public class SchemaActionTest extends TestCase {

	public void testSchemaAction(){
		Preferences form_data = new Preferences();
		form_data.setRemoteDB("127.0.0.1");
		form_data.setPortdb("50000");
		form_data.setNamedb("nomebco");
		form_data.setUserdb("jose");
		form_data.setPwddb("1234");
		form_data.setDriver("db2");
		ActionsFactory.callJetty(form_data, Constants.D_SERVER_ACTION).exec();
		JettyActions sAction = ActionsFactory.callJetty(form_data, Constants.D_SCHEMA_ACTION);
		assertTrue(sAction.exec());
	}
	
}
