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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import db2jmin.pojo.data.DBconnector;

/**
 * This class loads is used to Validade input data from user, as db, host, port,
 * dbname etc
 * 
 * @author Jose Damico (damico@dcon.com.br)
 */

public class InputDataValidation {
	public InputDataValidation() {
		log.AddLogLine("Invoking  InputDataValidation()");
	}

	public ArrayList formValidationDerbye(String form_name, ArrayList<String> form_data) {

		ArrayList validationResult = new ArrayList();
		boolean ret = true;
		validationResult.add(0, ret);

		if (form_data.get(2).toString().equals("")) {
			ret = false;
			validationResult.add("Wrong database name");
			log.AddLogLine("EXCEPTION: " + form_data.get(2).toString());
		}

		if (ret) {

			DBconnector dbc = new DBconnector(form_data);
			ret = dbc.testConn();
		}

		return validationResult;
	}

	public ArrayList<String> testTextFields(ArrayList<String> form_data){
		ArrayList<String> validationResult = new ArrayList<String>();
		if (form_data.get(0).toString().equals("")) {
			validationResult.add("Wrong server address");
			log.AddLogLine("EXCEPTION: " + form_data.get(0).toString());
		}
		if (form_data.get(1).toString().equals("")) {
			validationResult.add("Wrong server port");
			log.AddLogLine("EXCEPTION: " + form_data.get(1).toString());
		}

		if (form_data.get(2).toString().equals("")) {
			validationResult.add("Wrong database name");
			log.AddLogLine("EXCEPTION: " + form_data.get(2).toString());
		}
		
		if (form_data.get(3).toString().equals("")) {
			validationResult.add("Wrong user name");
			log.AddLogLine("EXCEPTION: " + form_data.get(3).toString());
		}
		return validationResult;
	}
	
	public ArrayList formValidationGen(String form_name, ArrayList form_data) {

		ArrayList validationResult = new ArrayList();
		boolean ret = true;
		
		
		
		if (form_name.equals("pref_form")) {
			
			validationResult.addAll(testTextFields(form_data));
			
			if(validationResult.size() > 0){
				ret = false;
				validationResult.add(0, ret);
			}
			
			/*
			 * Commented because derby
			 * 
			 * if(form_data.get(3).toString().equals("")){ ret = false;
			 * validationResult.add("Wrong username/password");
			 * log.AddLogLine("EXCEPTION: "+form_data.get(3).toString()); }
			 * if(form_data.get(4).toString().equals("")){ ret = false;
			 * validationResult.add("Wrong username/password");
			 * log.AddLogLine("EXCEPTION: "+form_data.get(4).toString()); }
			 */
			ReachServer reachsrv = new ReachServer(form_data.get(0).toString());

			if (ret) {
				if (reachsrv.isAlive() == false) {
					ret = false;
					validationResult.add("Hostname/IP address unreachable");
					log.AddLogLine("Hostname/IP address unreachable");
				} else {
					DBconnector dbc = new DBconnector(form_data);
					if (!dbc.testConn()) {
						ret = false;
						validationResult.add("Database connection failed");
					} else {
						ret = true;
					}
				}
			}

			if (ret && reachsrv.isAlive()) {

				DBconnector dbc = new DBconnector(form_data);
				ret = dbc.testConn();
			}
		}

		validationResult.add(0, ret);

		return validationResult;
	}

	private Logger log = new Logger(Constants.LOGNAME);

	public boolean isExecuteUpdateValidArray(ArrayList<String> sqlStmts) {

		boolean ret = false;
		for (int i = 0; i < sqlStmts.size(); i++) {
			if (!isExecuteUpdateValidString(sqlStmts.get(i))) {
				ret = false;
				break;
			} else
				ret = true;
		}
		return ret;
	}

	public boolean isExecuteUpdateValidString(String sql) {

		boolean ret = false;
		
		sql = cleanSql(sql);
		sql = getFirstCmd(sql);
		
		HashMap<Integer, String> hm = getValidExecuteUpdateStatements();
		if (!hm.containsValue(sql)) ret = false; else ret = true;
		return ret;
	}

	private HashMap<Integer, String> getValidExecuteUpdateStatements() {
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		hm.put(0, "update");
		hm.put(1, "delete");
		hm.put(2, "insert");
		hm.put(3, "create");
		hm.put(4, "call");
		hm.put(5, "alter");
		hm.put(5, "drop");
		hm.put(6, "truncate");
		return hm;
	}
	
	private String cleanSql(String sql){
		sql = sql.replaceAll("\n", " ");
		while (sql.startsWith(" ")) {
			sql = sql.substring(1);
		}
		log.AddLogLine("cleanSql(String sql): "+sql);
		return sql;
	}
	
	private String getFirstCmd(String sql){
		StringTokenizer st = new StringTokenizer(sql, " ");
		String maincmd = st.nextToken();
		maincmd =  maincmd.toLowerCase().trim();
		log.AddLogLine("getFirstCmd(String sql): "+maincmd);
		return maincmd;
	}

	public String[] parseDbUrl(String url) {
		/*
		 * example of url: db2://username@localhost:50000/mydb
		 * */
		
		String type = null;
		String userServer = null;
		String portDb = null;
		int firstBreak =0, secondBreak = 0;
		
		for(int i = 0; i < url.length(); i++){
			if(url.charAt(i) == ':' && firstBreak == 0){
				firstBreak = i;
				type = url.substring(0,firstBreak);
			}else if(url.charAt(i) == ':' && firstBreak > 0){
				secondBreak = i;
				userServer = url.substring(firstBreak+2, secondBreak);
				portDb = url.substring(secondBreak, url.length());
			}
		}
		
		
		return null;
	}
}
