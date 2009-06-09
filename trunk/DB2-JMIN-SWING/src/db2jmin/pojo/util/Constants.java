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

public interface Constants {

	public static final String APPNAME = "DBJMIN - 0.5 (2009-jun-24)";
	public static final int LOGTEXTH = 80;
	public static final int SQLTEXTH = 80;
	public static final String PRESERVER_FILE = "preServers.xml";
	public static final int DEFAULT_OS_SPEC_H = 2;
	
	public static final String APPLOGO = "dbjmin.png";
	public static int frameWidth = 850;
	public static int frameHeight = 50;
	public static String COMMON_KEY = "dbjmin"; //add to xml
	public static final String HISTORY_FILE = "dbash_history.txt"; //add to xml
	public static final String TEMP_ALIVE_CREDENTIAL = "dbjmin.properties"; //add to xml
	public static final int SELECT_LIMIT = 1500; //add to xml
	public static final String[] UNIXES_DEFAULT_BROWSERS = {"firefox", "iceweasel"};
	public static final String LOGNAME = "DBJMIN"; //add to xml
	public static final boolean VERBOSE_CONSOLE = false; //add to xml
	public static final int FIXED_LOGLIMIT = 5000000; //add to xml
	
	
	public static final int D_SERVER_ACTION = 0;
	public static final int D_SCHEMA_ACTION = 1;
	public static final int W_SCHEMA_ACTION = 2;
	public static final int W_TABLE_ACTION = 3;
	public static final int W_SAMPLEDATA_ACTION = 4;
	public static final int W_STRUCTURE_ACTION = 5;
	public static final int W_BATCH_SQL_ACTION = 6;
	public static final int W_SINGLE_SQL_ACTION = 7;
	public static final int W_GET_DB_SESSION = 8;
	public static final int W_CLOSE_SESSION = 9;
	
	public static final String HTML_TOP = 	"<html>\n" +
	"<head>\n" +
	"<title>"+ APPNAME +"</title></head>\n" +
	"<body>";

	public static final String HTML_BOTTON = "<hr>" +
	"" + APPNAME +
	"</body>\n" +
	"</html>";
	
	public static final String HTML_SQL_FORM = 	"@<br><form action = '/sqldata' method='post'>\n" +
												"<table width='400'><tr><td>" +
												"<textarea name='s' rows='15' cols='45'></textarea><br>\n" +
												"</td></tr>" +
												"<tr><td>" +
												"<input type='submit' value='Submit' />" +
												"</td></tr></table>" +
												"</form>";
	
	public static final String HTML_SCHEMA_HEADER = "<h2>Schemas</h2><hr>\n";
	public static final String HTML_TABLE_HEADER = "<h2>Tables</h2><hr>\n";
	public static final String HTML_SAMPLEDATA_HEADER = "<h2>Sample Data</h2><hr>\n";
	public static final String HTML_COMMON_HEADER = "<h2>#</h2><hr>\n";
	public static final String HTML_STRUCTURE_HEADER = "<h2>Table Structure</h2><hr>\n";
	public static final String HTML_SQL_HEADER = "<h2>SQL</h2><hr>\n";
	
	public static final String HTML_TOOLS = "<table width='795'>" +
											"<tr valign='top'>" +
											"<td width='10'>inserLogButton</td>" +
											"<td width='10'><form action = '/schemas' method='get'><input type='submit' value='Schemas' /></form></td>" +
											"<td width='10'><form action = '/sql' method='get'><input type='submit' value='SQL' /></form></td>" +
											"<td width='775'><center>@</center></td>" +
											"<td width='10'><form action = '/destroy' method='get'><input type='submit' value='Close Session' /></form></td>" +
											"</tr>" +
											"</table>";
	
	public static final String HTML_DESTROY_HEADER = "<h2>Close Session</h2><hr>\n";
	
	
	public static final int SCHEMA_STRUCTURE = 0;
	public static final int SCHEMA_DATA = 1;

	
	
	
	
	

}
