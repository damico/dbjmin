package org.jdamico.dbjmin.test;

import db2jmin.pojo.util.SystemOper;
import junit.framework.TestCase;

public class TestSystemOper extends TestCase {
	public void testExecExtCommand(){
		System.out.println(SystemOper.singleton().execExtCommand("whoami"));
	}
}
