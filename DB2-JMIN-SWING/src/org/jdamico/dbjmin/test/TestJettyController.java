package org.jdamico.dbjmin.test;

import org.jdamico.dbjmin.web.JettyController;

import junit.framework.TestCase;

public class TestJettyController extends TestCase {

	public void testStartJetty() {
		boolean isStarted = false;
		JettyController jettyControl;
		try {
			jettyControl = new JettyController(8000);
			jettyControl.init();
			isStarted = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertTrue(isStarted);
	}

}
