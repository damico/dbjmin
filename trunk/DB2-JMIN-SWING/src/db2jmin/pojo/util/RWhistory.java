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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import db2jmin.pojo.util.Constants;

/**	 	
 *This class creates a file called "dbash_history.txt"
 * Write/Read the commands SQL stored in a "dbash_history.txt"
 * 
 * */

public class RWhistory{


	private Logger log = new Logger(Constants.LOGNAME);
	
	
	//This method writes in the bash_history.txt
	public void writeFile(String $text){
		String text = $text;
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(Constants.HISTORY_FILE, true));
			bw.write(text + System.getProperty("line.separator"));
			bw.flush();
			bw.close();
		} catch (IOException ioe) {
	    	log.AddLogLine("IOException: " + ioe);
		}
	} 

	//This method read line by line of dbash_history.txt
	public String readFile(int nline) {
		String ret = null;
		BufferedReader in = null; 
	    try {
	    	int count = nline;
	        in = new BufferedReader(new FileReader(Constants.HISTORY_FILE));
	        for (int i = 0; i <count+1; i++) {
                       in.readLine();
            } 
			ret = in.readLine();
       
	    } catch (FileNotFoundException fnf) {
        	
        	log.AddLogLine("FileNotFoundException: "+fnf);
        	
	    } catch (IOException ioe) {
	    	
          	log.AddLogLine("IOException: " + ioe);   
          	
        }finally{
             try {
                 	if(in != null){
				    in.close();
			    }
             }catch (IOException ioe) {            	 
		      	log.AddLogLine("IOException: " + ioe);		      	
			}
        } 
       	if(ret == null)
			ret = Constants.EMPTY;
		
		return ret;
        
	}


	
}
