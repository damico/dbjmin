/* ******************************************************************* 	*/
/* DB2-JMIN / MULTI-JMIN (MULTI DB CLIENT)								*/
/* Copyright (C) 2007  Jose' Ricardo de Oliveira Damico					*/
/* damico@dcon.com.br													*/
/* 																		*/
/* This program is free software; you can redistribute it and/or		*/
/* modify it under the terms of the GNU General Public License			*/
/* as published by the Free Software Foundation; either version 2		*/
/* of the License, or (at your option) any later version.				*/
/* 																		*/
/* This program is distributed in the hope that it will be useful,		*/
/* but WITHOUT ANY WARRANTY; without even the implied warranty of		*/
/* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the		*/
/* GNU General Public License for more details.							*/
/* 																		*/
/* You should have received a copy of the GNU General Public License	*/
/* along with this program; if not, write to the Free Software			*/
/* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 			*/
/* MA  02110-1301, USA.													*/
/* ******************************************************************** */

package db2jmin.pojo.util;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.UIManager;

/**
 * This class is used to discover which 
 * is the OS of client (only works to Linux | Windows)
 * Also this class allow to set specific LookAndFeel settings,
 * related to a specific OS
 * @author Jose Damico (damico@dcon.com.br)
 * */

public class SystemOper {
	
	private static final SystemOper getInstance = new SystemOper();
	
	public static SystemOper singleton(){
		return getInstance;
	}

	private Logger log = new Logger(Constants.LOGNAME);

	public boolean isWindows() {
		boolean ret = true;
		if (System.getProperty("os.name").equalsIgnoreCase("Linux"))
			ret = false;
		return ret;
	}

	public void setWinUIManager() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			log.AddLogLine("EXCEPTION: " + e);
		}
	}

	public String execExtCommand(String command){
		StringBuffer sb = new StringBuffer();
		try {
	        Process child = Runtime.getRuntime().exec(command);
	        InputStream in = child.getInputStream();
	        int c;
	        while ((c = in.read()) != -1) {
	            sb.append((char)c);
	        }
	        in.close();
	    } catch (IOException e) {
	    	log.AddLogLine("IOException: " + e);
	    }
	    String ret = sb.toString();
	    ret = ret.replaceAll("\n", "");
	    return ret;
	}
}
