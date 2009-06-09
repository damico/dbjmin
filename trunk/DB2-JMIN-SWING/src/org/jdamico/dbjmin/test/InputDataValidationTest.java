package org.jdamico.dbjmin.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import junit.framework.TestCase;
import db2jmin.pojo.util.InputDataValidation;
import db2jmin.pojo.util.PasswordField;

public class InputDataValidationTest extends TestCase {
	
	public void testParseDbUrl(){
		String url = "db2://jdamico@127.0.0.1:50000/ccmdb";
		InputDataValidation idv = new InputDataValidation();
		String[] input = idv.parseDbUrl(url);
		
		for(int i=0; i <input.length; i++){
			System.out.println(input[i]);
		}
		
		ArrayList<String> formData = new ArrayList<String>();
		
		formData.add(0, input[2]);
		formData.add(1, input[3]);
		formData.add(2, input[4]);
		formData.add(3, input[1]);
		
		
		
		  char password[] = null;
	      try {
	         password = PasswordField.getPassword(System.in, "Enter your password: ");
	      } catch(IOException ioe) {
	         ioe.printStackTrace();
	      }

		
		
		
	      formData.add(4, password.toString());
	       
		
		
		
		
		formData.add(5, input[0]);
		
		ArrayList val = idv.formValidationGen("pref_form", formData);
		
		for(int i = 0; i < val.size(); i++){
			System.out.println(val.get(i));
		}
	}

}
