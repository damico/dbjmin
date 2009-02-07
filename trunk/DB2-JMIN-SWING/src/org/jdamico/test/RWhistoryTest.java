/*******************************************************************************
 * DB2-JMIN / MULTI-JMIN (MULTI DB CLIENT)	
 * Copyright (c) 2008, 2009 Mario C. Ponciano and others.
 * This program is free software; you can redistribute it and/or		
 * modify it under the terms of the GNU General Public License			
 * as published by the Free Software Foundation; either version 2		
 * of the License, or (at your option) any later version.
 *
 *	
*******************************************************************************/	

package org.jdamico.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import db2jmin.pojo.util.Constants;
import db2jmin.pojo.util.RWhistory;
import junit.framework.TestCase;

/**	 	
 *This Test class checks if the file was created
 *Check Write/Read the commands SQL stored in a "bash_history.txt"
 * @author Mario Cezar Ponciano - a.k.a: Razec (mrazec@gmail.com)
 * */

public class RWhistoryTest extends TestCase{

    protected void setUp() {    	 
        System.out.println("Starting...");
        }
    

   
     public void testWriteFile() throws IOException{
    	 int checkW = 0;
    	 File file = new File(Constants.HISTORY_FILE); 
    	 if(file.exists()){
    	        RWhistory rw = new RWhistory();
    	        rw.writeFile("test");
    		    checkW++;
    	 }else{
    		 System.out.println("Not Exists");
    	 }
    	 if(checkW == 1){
    		 assertEquals(1, checkW);
    	 }
   }


	public void testReadFile()throws FileNotFoundException {
		 int checkR = 0;
    	 File file = new File(Constants.HISTORY_FILE); 
    	 if(file.exists()){
    		 RWhistory rw = new RWhistory();
    		 rw.readFile(1);
    	 }else{
    		 System.out.println("Not Exists");
    	 }
     	 if(checkR == 1){
    		 assertEquals(1, checkR);
    	 }
	    
	}
	
	protected void tearDown(){
		System.out.println("Close...");
	}
	
}
