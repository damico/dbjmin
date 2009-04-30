package org.jdamico.dbjmin.test;

import java.util.ArrayList;
import java.util.Properties;

import junit.framework.TestCase;

import org.jdamico.dbjmin.crypto.DesEncrypter;

import db2jmin.pojo.util.Constants;
import db2jmin.pojo.util.ManageProperties;
import db2jmin.pojo.util.SystemOper;

public class CryptoTest extends TestCase {
	public void testEncryptDecrypt(){
		String ret = null, ret2 = null;
		try {
	        DesEncrypter encrypter = new DesEncrypter(Constants.COMMON_KEY);
	        ret = encrypter.encrypt("test");
	        ret2 = encrypter.decrypt(ret);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    System.out.println(ret);
	    System.out.println(ret2);
	}
	
	public void testTransformForm_Data(){
		DesEncrypter encDec = new DesEncrypter();
		ArrayList<String > form_data = encDec.transformFormData();
	    for(int i = 0; i < form_data.size(); i++){
	    	System.out.println(form_data.get(i));
	    }
	}
}
