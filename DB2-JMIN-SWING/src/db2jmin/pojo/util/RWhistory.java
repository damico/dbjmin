/*******************************************************************************
 * DB2-JMIN / MULTI-JMIN (MULTI DB CLIENT)	
 * Copyright (c) 2008, 2009 Mario C. Ponciano and others.
 * This program is free software; you can redistribute it and/or		
 * modify it under the terms of the GNU General Public License			
 * as published by the Free Software Foundation; either version 2		
 * of the License, or (at your option) any later version.
 *
 *Contributors:
 *Mario C. Ponciano a.k.a Razec (mrazec@gmail.com)
 *Jose' Ricardo de Oliveira Damico (jd.comment@gmail.com)
 *  	
*******************************************************************************/			
package db2jmin.pojo.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**	 	
 *This class creates a file called "dbash_history.txt"
 * Write/Read the commands SQL stored in a "dbash_history.txt"
 * 
 * */

public class RWhistory{


	private Logger log = new Logger(Constants.LOGNAME);
	private String fileHistory = null;
	
	public RWhistory(String fileHistory){
		this.fileHistory = fileHistory;
		
	}
	
	//This method writes in the bash_history.txt
	public void writeFile(String $text){
		String text = $text;
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileHistory, true));
			bw.write(text + System.getProperty("line.separator"));
			bw.flush();
			bw.close();
		} catch (IOException ioe) {
	    	log.AddLogLine("IOException: " + ioe);
		}
	} 

	//This method read line by line of dbash_history.txt
	public String readFile(int nline) {
		String row = null;
		int count = 0;
		try {
	        BufferedReader input = new BufferedReader(new FileReader(fileHistory));
	        
	        while ((row = input.readLine()) != null) {
	            if(count == nline) break;
	        	count ++;
	            
	        }
	        input.close();
	    } catch (IOException ioe) {
	    	log.AddLogLine("IOException: " + ioe.getMessage());	
	    }
		if(row == null) row = "";
		return row;
        
	}

	public int getTotalLines() {
		int ret = 0;
		try {
	        BufferedReader input = new BufferedReader(new FileReader(fileHistory));
	        while ((input.readLine()) != null) {
	            ret ++;
	        }
	        input.close();
	    } catch (IOException ioe) {
	    	log.AddLogLine("IOException: " + ioe.getMessage());	
	    }
		return ret;
	}


	
}
