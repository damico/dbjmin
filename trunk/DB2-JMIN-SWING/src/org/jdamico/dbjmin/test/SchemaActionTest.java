package org.jdamico.dbjmin.test;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.jdamico.dbjmin.web.action.ActionsFactory;
import org.jdamico.dbjmin.web.action.JettyActions;

import db2jmin.pojo.util.Constants;

public class SchemaActionTest extends TestCase {

	public void testSchemaAction(){
		ArrayList<String> form_data = new ArrayList<String>();
		form_data.add("127.0.0.1");
		form_data.add("50000");
		form_data.add("nomebco");
		form_data.add("jose");
		form_data.add("1234");
		form_data.add("db2");
		ActionsFactory.callJetty(form_data, Constants.D_SERVER_ACTION).exec();
		JettyActions sAction = ActionsFactory.callJetty(form_data, Constants.D_SCHEMA_ACTION);
		assertTrue(sAction.exec());
	}
	
}
