package org.jdamico.dbjmin.test;

import junit.framework.TestCase;
import db2jmin.pojo.util.Constants;
import db2jmin.pojo.util.SystemOper;

public class SystemOperTest extends TestCase {
	public void testGetPreServersPath() {

		String mockPath = "/home/"
				+ SystemOper.singleton().execExtCommand("whoami") + "/.dbjmin/"
				+ Constants.PRESERVER_FILE;

		String path = null;
		path = SystemOper.singleton().getHomePath();

		assertEquals(mockPath, path);

	}

	public void testStartBrowser() {
		SystemOper.singleton().startBrowser(Constants.UNIXES_DEFAULT_BROWSERS,
				"http://www.gnu.org");
	}
}
