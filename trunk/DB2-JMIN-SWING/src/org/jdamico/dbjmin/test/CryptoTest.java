package org.jdamico.dbjmin.test;

import java.util.List;

import junit.framework.TestCase;

import org.jdamico.dbjmin.crypto.DesEncrypter;

import db2jmin.pojo.data.Preferences;
import db2jmin.pojo.util.Constants;

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
		Preferences form_data = encDec.transformFormData();
		List<String> list = form_data.toList();
	    for(int i = 0; i < list.size(); i++){
	    	System.out.println(list.get(i));
	    }
	}
}
