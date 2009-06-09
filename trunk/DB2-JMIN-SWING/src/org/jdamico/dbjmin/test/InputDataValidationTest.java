package org.jdamico.dbjmin.test;

import db2jmin.pojo.util.InputDataValidation;
import junit.framework.TestCase;

public class InputDataValidationTest extends TestCase {
	
	public void parseDbUrltest(){
		String url = null;
		InputDataValidation idv = new InputDataValidation();
		String[] input = idv.parseDbUrl(url);
	}

}
