package org.jdamico.dbjmin.web.action;

import java.util.ArrayList;

import db2jmin.pojo.data.Preferences;
import db2jmin.pojo.util.Constants;
import db2jmin.pojo.util.ManageProperties;
import db2jmin.pojo.util.SystemOper;

public class SchemaAction implements JettyActions {

	private Preferences form_data = null;

	public SchemaAction(Preferences form_data2) {
		this.form_data = form_data2;
	}

	public boolean exec() {
		boolean ret = false;
		String fs = SystemOper.singleton().getTempPath();
		fs = fs + Constants.TEMP_ALIVE_CREDENTIAL;
		boolean isPropWritten = ManageProperties.getInstance().write(fs,
				form_data);
		if (isPropWritten) {
			ret = SystemOper.singleton().startBrowser(
					Constants.UNIXES_DEFAULT_BROWSERS,
					"http://127.0.0.1:8888/schemas");
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	public ArrayList getResult() {
		return null;
	}

}
