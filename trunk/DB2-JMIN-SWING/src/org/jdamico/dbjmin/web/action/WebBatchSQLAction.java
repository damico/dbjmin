package org.jdamico.dbjmin.web.action;

import java.util.ArrayList;

import org.jdamico.dbjmin.crypto.DesEncrypter;

import db2jmin.pojo.data.DBconnector;
import db2jmin.pojo.data.Preferences;
import db2jmin.pojo.util.Constants;
import db2jmin.pojo.util.ExecuteUpdateObject;
import db2jmin.pojo.util.InputDataValidation;
import db2jmin.pojo.util.Logger;
import db2jmin.pojo.util.SQLParser;

public class WebBatchSQLAction implements JettyActions {

	private Logger log = new Logger(Constants.LOGNAME);
	private Preferences form_data = null;

	public WebBatchSQLAction(Preferences form_data2) {
		this.form_data = form_data2;
	}

	public boolean exec() {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	public ArrayList getResult() {
		DesEncrypter encDec = new DesEncrypter();
		DBconnector dbc = new DBconnector(encDec.transformFormData());
		ArrayList<ExecuteUpdateObject> euoArray = new ArrayList<ExecuteUpdateObject>();
		String query = form_data.getRemoteDB();
		String preSql = query.replaceAll("\n", "");
		ArrayList<String> sqlStmts = SQLParser.singleton().breakSql(preSql);
		InputDataValidation idv = new InputDataValidation();
		if (idv.isExecuteUpdateValidArray(sqlStmts)) {
			for (int i = 0; i < sqlStmts.size(); i++) {
				log.AddLogLine("calling getBatchSQL: " + i);
				euoArray.add(dbc.getBatchSQL(sqlStmts.get(i)));
			}
		} else {
			ExecuteUpdateObject euo = new ExecuteUpdateObject();
			euo.setStatement(query);
			String error = "Invalid set of Statements.";
			euo.setStatus(error);
			euoArray.add(euo);
			log.AddLogLine(error);
		}

		return euoArray;
	}

}
