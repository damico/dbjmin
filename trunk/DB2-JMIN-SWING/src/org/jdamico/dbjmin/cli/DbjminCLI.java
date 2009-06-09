package org.jdamico.dbjmin.cli;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.jdamico.dbjmin.web.action.ActionsFactory;

import db2jmin.pojo.util.Constants;
import db2jmin.pojo.util.InputDataValidation;
import db2jmin.pojo.util.PasswordField;

public class DbjminCLI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String fArg = null;
		
		try{
			fArg = args[0];
		}catch(Exception e){
			
			
			exitWithMessage("No arguments!");
			
		}
		
		
		if (fArg != null && !fArg.trim().equals("")) {

			InputDataValidation idv = new InputDataValidation();
			String[] input = idv.parseDbUrl(args[0]);

			boolean badUrl = false;
			
			for (int i = 0; i < input.length; i++) {
				if(input[i]==null) badUrl = true; 
			}

			if(!badUrl){
				
				ArrayList<String> formData = new ArrayList<String>();

				formData.add(0, input[2]);
				formData.add(1, input[3]);
				formData.add(2, input[4]);
				formData.add(3, input[1]);

				char password[] = null;
				try {
					password = PasswordField.getPassword(System.in, "DB password: ");
				} catch (IOException ioe) {
					exitWithMessage("Invalid password char[]");
				}

				
				formData.add(4, String.valueOf(password));

				formData.add(5, input[0]);

				ArrayList val = idv.formValidationGen("pref_form", formData);
				
				if((Boolean)val.get(0)){
					ActionsFactory.callJetty(formData,	Constants.D_SERVER_ACTION).exec();
					boolean daemon = ActionsFactory.callJetty(formData,	Constants.W_CLI_ACTION).exec();
					
					if(daemon){
						InetAddress addr = null;
						try {
					        addr = InetAddress.getLocalHost();

					    } catch (UnknownHostException e) {
					    	exitWithMessage("Error trying to attach daemon to local ip address");
					    }

						
						System.out.print("\n\ndbjmind running... http://"+addr.getHostAddress()+":8888/schemas\n\n");
					}
					else exitWithMessage("Error trying to start daemon!");
					
				}else{
					exitWithMessage(val.get(1).toString());
				}
				
				
				
			}else{
				exitWithMessage("Invalid db url: "+fArg);
			}
			
			

		}else System.out.println("Invalid arguments!");

	}
	
	
	private static void exitWithMessage(String message){
		System.out.println(message);
		System.exit(1);
	}
	
}
