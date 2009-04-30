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

/**
 * This class intents to hold all application
 * Constants
 * @author Jose Damico (damico@dcon.com.br)
 * */

public class Constants {

	public static final String APPNAME = "DBJMIN - 0.3 (2009-feb-09)";
	public static final int LOGTEXTH = 80;
	public static final int SQLTEXTH = 80;
	public static final String PRESERVER_FILE = "preServers.xml";
	public static final int DEFAULT_OS_SPEC_H = 2;
	public static final String LOGNAME = "DBJMIN";
	public static final boolean VERBOSE_CONSOLE = false;
	public static final String APPLOGO = "dbjmin.png";
	public static int frameWidth = 798;
	public static int frameHeight = 50;
	public static String COMMON_KEY = "dbjmin";
	public static final String HISTORY_FILE = "dbash_history.txt";
	public static final String TEMP_ALIVE_CREDENTIAL = "dbjmin.properties";
	
	public static final int D_SERVER_ACTION = 0;
	public static final int D_SCHEMA_ACTION = 1;
	public static final int W_SCHEMA_ACTION = 2;
	public static final int W_TABLE_ACTION = 3;
	
	public static final String HTML_TOP = 	"<html>\n" +
	"<head>\n" +
	"<title>DBJMIN: Schemas</title>\n" +
	"</head>\n" +
	"<body>\n" +
	"<h2>DBJMIN: Schemas</h2><hr>";

	public static final String HTML_BOTTON = "<hr>" +
	"" + APPNAME +
	"</body>\n" +
	"</html>";
	

}
