package org.jdamico.dbjmin.web.action;

import db2jmin.pojo.data.Preferences;
import db2jmin.pojo.util.Constants;

public class ActionsFactory {

	public static JettyActions callJetty(Preferences form_data, int action) {
		switch (action) {
		case Constants.D_SERVER_ACTION:
			return new ServerAction(form_data);
		case Constants.D_SCHEMA_ACTION:
			return new SchemaAction(form_data);
		case Constants.W_SCHEMA_ACTION:
			return new WebSchemaAction(form_data);
		case Constants.W_CLI_ACTION:
			return new WebCliAction(form_data);
		case Constants.W_TABLE_ACTION:
			return new WebTableAction(form_data);
		case Constants.W_SAMPLEDATA_ACTION:
			return new WebSampleDataAction(form_data);
		case Constants.W_STRUCTURE_ACTION:
			return new WebStructureAction(form_data);
		case Constants.W_BATCH_SQL_ACTION:
			return new WebBatchSQLAction(form_data);
		case Constants.W_SINGLE_SQL_ACTION:
			return new WebSingleSQLAction(form_data);
		case Constants.W_GET_DB_SESSION:
			return new WebGetDbSessionAction(form_data);
		case Constants.W_CLOSE_SESSION:
			return new WebCloseSessionAction(form_data);
		default:
			return null;
		}
	}

}
