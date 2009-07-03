package org.jdamico.dbjmin.web.action;

import java.util.ArrayList;

import db2jmin.pojo.data.Preferences;
import db2jmin.pojo.util.Constants;
import db2jmin.pojo.util.ManageProperties;
import db2jmin.pojo.util.SystemOper;

public class WebCliAction implements JettyActions {

	private Preferences formData = null;

	public WebCliAction(Preferences form_data2) {
		this.formData = form_data2;
	}

	public boolean exec() {

		String fs = SystemOper.singleton().getTempPath();
		fs = fs + Constants.TEMP_ALIVE_CREDENTIAL;
		boolean isPropWritten = ManageProperties.getInstance().write(fs,
				formData);

		return isPropWritten;
	}

	public ArrayList getResult() {
		return null;
	}

}
