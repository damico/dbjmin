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

package db2jmin.pojo.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import db2jmin.pojo.util.Constants;
import db2jmin.pojo.util.ExecuteUpdateObject;
import db2jmin.pojo.util.InputDataValidation;
import db2jmin.pojo.util.Logger;

/**
 * This class takes care of all DB connections 
 * @author Jose Damico (damico@dcon.com.br)
 * */

public class DBconnector {
	public DBconnector(ArrayList prefs) {

		remotedb = prefs.get(0).toString();
		portdb = prefs.get(1).toString();
		namedb = prefs.get(2).toString();
		userdb = prefs.get(3).toString();
		log.AddLogLine("User: " + userdb);
		pwddb = prefs.get(4).toString();
		driver = prefs.get(5).toString();
		log.AddLogLine("driver: " + driver);

		if (driver.equals("mysql")) {
			classfn = "com.mysql.jdbc.Driver";
			dburl = "jdbc:mysql://" + remotedb + ":" + portdb + "/" + namedb
					+ "";
			scolumn = "SCHEMA_NAME";
			tcolumn = "TABLE_NAME";
			SCOLNAME = "COLUMN_NAME";
			STYPE = "DATA_TYPE";
			SLENGTH = "CHARACTER_MAXIMUM_LENGTH";
			slimit = " LIMIT 100 ";

			SQL_schemas = "select " + scolumn
					+ " from information_schema.SCHEMATA";
			SQL_tables = "Select "
					+ tcolumn
					+ ", table_schema from information_schema.TABLES where table_schema =?";
			SQL_structure = "SELECT  "
					+ SCOLNAME
					+ ","
					+ STYPE
					+ ","
					+ SLENGTH
					+ " FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=? AND TABLE_NAME=? ";

		} else if (driver.equals("db2")) {
			classfn = "com.ibm.db2.jcc.DB2Driver";
			dburl = "jdbc:db2://" + remotedb + ":" + portdb + "/" + namedb + "";
			scolumn = "NAME";
			tcolumn = "TABNAME";
			SCOLNAME = "COLNAME";
			STYPE = "TYPENAME";
			SLENGTH = "LENGTH";
			slimit = " FETCH FIRST 100 ROWS ONLY ";

			SQL_schemas = "select " + scolumn + " from sysibm.sysschemata";
			SQL_tables = "Select " + tcolumn
					+ ", TABSCHEMA from SYSCAT.TABLES where tabschema =?";
			SQL_structure = "SELECT  " + SCOLNAME + "," + STYPE + "," + SLENGTH
					+ " FROM SYSCAT.COLUMNS WHERE TABSCHEMA=? AND TABNAME=? ";

		} else if (driver.equals("postgre")) {
			classfn = "org.postgresql.Driver";
			dburl = "jdbc:postgresql://" + remotedb + ":" + portdb + "/"
					+ namedb + "";
			scolumn = "catalog_name";
			tcolumn = "TABLE_NAME";
			SCOLNAME = "COLUMN_NAME";
			STYPE = "DATA_TYPE";
			SLENGTH = "CHARACTER_MAXIMUM_LENGTH";
			slimit = " LIMIT 100 ";

			SQL_schemas = "select "
					+ scolumn
					+ " from information_schema.information_schema_catalog_name";
			SQL_tables = "Select "
					+ tcolumn
					+ ", table_catalog from information_schema.TABLES where table_catalog =?";
			SQL_structure = "SELECT  "
					+ SCOLNAME
					+ ","
					+ STYPE
					+ ","
					+ SLENGTH
					+ " FROM information_schema.COLUMNS WHERE table_catalog=? AND TABLE_NAME=? ";

		} else if (driver.equals("firebird")) {
			classfn = "org.firebirdsql.jdbc.FBDriver";
			dburl = "jdbc:firebirdsql:" + remotedb + "/" + portdb + ":"
					+ namedb + "";
			scolumn = "catalog_name";
			tcolumn = "TABLE_NAME";
			SCOLNAME = "COLUMN_NAME";
			STYPE = "DATA_TYPE";
			SLENGTH = "FIELD_LENGTH";
			slimit = " FIRST 100 ";

			SQL_schemas = "select "
					+ scolumn
					+ " from information_schema.information_schema_catalog_name";
			SQL_tables = "SELECT DISTINCT RDB$RELATION_FIELDS.RDB$RELATION_NAME AS "
					+ tcolumn
					+ " "
					+ "FROM RDB$RELATION_FIELDS, RDB$FIELDS "
					+ "WHERE ( RDB$RELATION_FIELDS.RDB$FIELD_SOURCE = RDB$FIELDS.RDB$FIELD_NAME ) "
					+ "AND ( RDB$FIELDS.RDB$SYSTEM_FLAG <> 1 ) "
					+ "AND RDB$RELATION_FIELDS.RDB$RELATION_NAME <> ?";

			SQL_structure = "SELECT b.RDB$FIELD_NAME AS COLUMN_NAME, d.RDB$TYPE_NAME AS DATA_TYPE, c.RDB$FIELD_LENGTH AS FIELD_LENGTH "
					+ "FROM   RDB$RELATIONS a "
					+ "INNER JOIN RDB$RELATION_FIELDS b ON     a.RDB$RELATION_NAME = b.RDB$RELATION_NAME "
					+ "INNER JOIN RDB$FIELDS c ON     b.RDB$FIELD_SOURCE = c.RDB$FIELD_NAME "
					+ "INNER JOIN RDB$TYPES d ON     c.RDB$FIELD_TYPE = d.RDB$TYPE "
					+ "WHERE  a.RDB$SYSTEM_FLAG = 0 "
					+ "AND  d.RDB$FIELD_NAME <> ? "
					+ "AND  d.RDB$FIELD_NAME = 'RDB$FIELD_TYPE' "
					+ "AND a.RDB$RELATION_NAME = ? "
					+ "ORDER BY a.RDB$RELATION_NAME, b.RDB$FIELD_ID";

		} else if (driver.equals("derby")) {
			classfn = "org.apache.derby.jdbc.ClientDriver";
			dburl = "jdbc:derby://" + remotedb + ":" + portdb + "/" + namedb
					+ ";create=true;";
			scolumn = "SCHEMANAME";
			tcolumn = "TABLENAME";
			SCOLNAME = "COLUMNNAME";
			STYPE = "COLUMNDATATYPE";
			SLENGTH = "COLUMNDATATYPE";
			slimit = " FIRST 100 ";

			SQL_schemas = "select " + scolumn + " from SYS.SYSSCHEMAS";

			SQL_tables = "select "
					+ tcolumn
					+ " from  sys.systables st "
					+ "left join sys.sysschemas ss on st.schemaid=ss.schemaid where schemaname=?";

			SQL_structure = "SELECT "
					+ SCOLNAME
					+ ","
					+ STYPE
					+ ","
					+ SLENGTH
					+ "  FROM SYS.SYSTABLES ST "
					+ "LEFT JOIN SYS.SYSSCHEMAS SS ON ST.SCHEMAID=SS.SCHEMAID "
					+ "LEFT JOIN SYS.SYSCOLUMNS SC ON ST.TABLEID=SC.REFERENCEID "
					+ "WHERE SCHEMANAME=? AND TABLENAME=?";

		} else if (driver.equals("derby-e")) {
			classfn = "org.apache.derby.jdbc.EmbeddedDriver";
			dburl = "jdbc:derby:" + namedb + ";create=true;";
			scolumn = "SCHEMANAME";
			tcolumn = "TABLENAME";
			SCOLNAME = "COLUMNNAME";
			STYPE = "COLUMNDATATYPE";
			SLENGTH = "COLUMNDATATYPE";
			slimit = " FIRST 100 ";

			SQL_schemas = "select " + scolumn + " from SYS.SYSSCHEMAS";

			SQL_tables = "select "
					+ tcolumn
					+ " from  sys.systables st "
					+ "left join sys.sysschemas ss on st.schemaid=ss.schemaid where schemaname=?";

			SQL_structure = "SELECT "
					+ SCOLNAME
					+ ","
					+ STYPE
					+ ","
					+ SLENGTH
					+ "  FROM SYS.SYSTABLES ST "
					+ "LEFT JOIN SYS.SYSSCHEMAS SS ON ST.SCHEMAID=SS.SCHEMAID "
					+ "LEFT JOIN SYS.SYSCOLUMNS SC ON ST.TABLEID=SC.REFERENCEID "
					+ "WHERE SCHEMANAME=? AND TABLENAME=?";

		} else if (driver.equals("oracle")) {
			classfn = "oracle.jdbc.OracleDriver";
			dburl = "jdbc:oracle:thin:@" + remotedb + ":" + portdb + ":"
					+ namedb + "";
			scolumn = "SCHEMANAME";
			tcolumn = "OBJECT_NAME";
			SCOLNAME = "COLUMN_NAME";
			STYPE = "DATA_TYPE";
			SLENGTH = "DATA_LENGTH";
			slimit = " WHERE ROWNUM<=100 ";

			SQL_schemas = "SELECT username as " + scolumn
					+ " FROM all_users order by username";

			SQL_tables = "SELECT "
					+ tcolumn
					+ " FROM SYS.All_Objects WHERE OBJECT_TYPE= 'TABLE' AND OWNER = ? "
					+ "ORDER BY " + tcolumn + " DESC";

			SQL_structure = "SELECT "
					+ SCOLNAME
					+ ","
					+ STYPE
					+ ","
					+ SLENGTH
					+ " FROM SYS.ALL_TAB_COLUMNS ST "
					+ "LEFT JOIN SYS.All_Objects SA ON ST.TABLE_NAME=SA.OBJECT_NAME "
					+ "WHERE SA.OWNER =? AND  ST.TABLE_NAME=? order by column_name";
		}

		//log.AddLogLine("dburl: " + dburl);
	}

	public boolean testConn() {
		boolean ret = false;
		//log.AddLogLine("Test DB (" + driver + "): started");
		Connection con = null;
		try {
			Class.forName(classfn);
			if (driver.contains("derby")) {
				con = DriverManager.getConnection(dburl);
			} else {
				con = DriverManager.getConnection(dburl, userdb, pwddb);
			}

			log.AddLogLine("Test DB (" + driver + "): connected");
			ret = true;
		} catch (ClassNotFoundException e) {
			log.AddLogLine("EXCEPTION: " + e);
			ret = false;
		} catch (SQLException e) {
			log.AddLogLine("EXCEPTION: " + e);
			ret = false;
		}

		return ret;
	}

	public ArrayList<String> getSchemas() {

		Statement stmt = null;
		ArrayList<String> schemas = new ArrayList<String>();
		Connection con = null;

		if (driver.equals("firebird")) {
			schemas.add(namedb);
		} else {

			try {
				Class.forName(classfn);

				if (driver.contains("derby")) {
					con = DriverManager.getConnection(dburl);
				} else {
					con = DriverManager.getConnection(dburl, userdb, pwddb);
				}
				log.AddLogLine("DB: connected");
				stmt = con.createStatement();
				String SQL = SQL_schemas;

				log.AddLogLine("SQL: " + SQL);
				ResultSet rs = stmt.executeQuery(SQL);
				while (rs.next()) {
					schemas.add(rs.getString("" + scolumn + ""));
				}
				rs.close();
			} catch (SQLException e) {
				log.AddLogLine("EXCEPTION: " + e);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					stmt.close();
					con.close();
				} catch (SQLException e) {
					log.AddLogLine("EXCEPTION: " + e);
				}
			}
		}
		return schemas;
	}

	public ArrayList getTables(String schema) {

		Statement stmt = null;
		ArrayList tables = new ArrayList();
		Connection con = null;
		try {
			Class.forName(classfn);
			log.AddLogLine("DB: connected");
			if (driver.equals("derby")) {
				con = DriverManager.getConnection(dburl);
			} else {
				con = DriverManager.getConnection(dburl, userdb, pwddb);
			}
			stmt = con.createStatement();
			String SQL = SQL_tables;
			log.AddLogLine("SQL: " + SQL);
			PreparedStatement stmtp = con.prepareStatement(SQL);
			stmtp.setString(1, schema);

			ResultSet rs = stmtp.executeQuery();
			while (rs.next()) {
				tables.add(rs.getString("" + tcolumn + ""));
			}
			rs.close();
		} catch (SQLException e) {
			log.AddLogLine("EXCEPTION: " + e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				con.close();
			} catch (SQLException e) {
				log.AddLogLine("EXCEPTION: " + e);
			}
		}
		return tables;
	}

	public ArrayList<ArrayList> getTablesDescription(String schema, String table) {

		Statement stmt = null;
		ArrayList<ArrayList> table_description = new ArrayList<ArrayList>();
		ArrayList<String> colname = new ArrayList<String>();
		ArrayList typename = new ArrayList();
		ArrayList length = new ArrayList();
		Connection con = null;
		try {
			Class.forName(classfn);
			log.AddLogLine("DB: connected");
			if (driver.equals("derby")) {
				con = DriverManager.getConnection(dburl);
			} else {
				con = DriverManager.getConnection(dburl, userdb, pwddb);
			}
			stmt = con.createStatement();
			String SQL = SQL_structure;
			PreparedStatement stmtp = con.prepareStatement(SQL);
			stmtp.setString(1, schema);
			stmtp.setString(2, table);
			log.AddLogLine("SQL: " + SQL);
			ResultSet rs = stmtp.executeQuery();
			while (rs.next()) {
				colname.add("" + rs.getString("" + SCOLNAME + ""));
				typename.add("" + rs.getString("" + STYPE + ""));
				length.add("" + rs.getString("" + SLENGTH + ""));

			}
			rs.close();
		} catch (SQLException e) {
			log.AddLogLine("EXCEPTION: " + e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				con.close();
			} catch (SQLException e) {
				log.AddLogLine("EXCEPTION: " + e);
			}
		}
		table_description.add(colname);
		table_description.add(typename);
		table_description.add(length);
		return table_description;
	}

	public ArrayList getTablesData(String schema, String table) {

		Statement stmt = null;
		ArrayList columns_name = new ArrayList();
		String error = "";
		
		ArrayList data = new ArrayList();
		ArrayList set = new ArrayList();

		Connection con = null;
		String SQL = null;
		int n_columns = 0;

		if (driver.equals("mysql")) {
			SQL = "SELECT * FROM " + table + " " + slimit;
		}
		if (driver.equals("firebird")) {
			SQL = "SELECT " + slimit + " * FROM " + table + " ";
		}
		if (driver.equals("db2")) {
			SQL = "SELECT * FROM " + schema + "." + table + " " + slimit;
		}
		if (driver.equals("postgre")) {
			SQL = "SELECT * FROM " + table + " " + slimit;
		}
		if (driver.equals("derby")) {
			SQL = "SELECT * FROM " + schema + "." + table + " ";
		}
		if (driver.equals("oracle")) {
			SQL = "SELECT * FROM " + schema + "." + table + " ";
		}
		try {
			Class.forName(classfn);
			log.AddLogLine("DB: connected");
			if (driver.equals("derby")) {
				con = DriverManager.getConnection(dburl);
			} else {
				con = DriverManager.getConnection(dburl, userdb, pwddb);
			}
			stmt = con.createStatement();

			log.AddLogLine("SQL: " + SQL);
			ResultSet rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				ArrayList columns_data = new ArrayList();
				n_columns = rs.getMetaData().getColumnCount();

				log.AddLogLine("n_columns: " + n_columns);

				columns_name.clear();
				String column = "";

				for (int i = 1; i <= n_columns; i++) {
					//log.AddLogLine("rs.getMetaData().getColumnName(i): "+ rs.getMetaData().getColumnName(i));
					columns_name.add(rs.getMetaData().getColumnName(i).toString());
					column = rs.getMetaData().getColumnName(i).toString();
					//log.AddLogLine("rs.getString(" + column + "): "+ rs.getString(column));
					if (rs.getString(column) != null) {
						columns_data.add(rs.getString(column));
					} else {
						columns_data.add("--");
					}

				}
				set.add(columns_data.clone());
				log.AddLogLine("===================================================");
				log.AddLogLine("nn_columns_data: " + columns_data.size()+" | "+"nn_set: " + set.size());
				columns_data.clear();
			}
			rs.close();

			log.AddLogLine("nn_columns: " + columns_name.size());

		} catch (SQLException e) {
			error = error + e.getMessage();
			log.AddLogLine("EXCEPTION: " + e);
		} catch (ClassNotFoundException e) {
			error = error + e.getMessage();
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				con.close();
			} catch (SQLException e) {
				error = error + e.getMessage();
				log.AddLogLine("EXCEPTION: " + e);
			}
		}
		data.add(columns_name);
		data.add(set);
		data.add(error);
		return data;
	}

	public ArrayList getSQL(String sql) {

		Statement stmt = null;
		ArrayList columns_name = new ArrayList();
		ArrayList errors = new ArrayList();
		ArrayList data = new ArrayList();
		ArrayList set = new ArrayList();
		ArrayList columns_data = new ArrayList();

		Connection con = null;

		int n_columns = 0;

		try {
			Class.forName(classfn);
			log.AddLogLine("DB: connected");
			if (driver.equals("derby")) {
				con = DriverManager.getConnection(dburl);
			} else {
				con = DriverManager.getConnection(dburl, userdb, pwddb);
			}
			stmt = con.createStatement();
			String SQL = sql;

			int affected_rows = 0;
			
			InputDataValidation idv = new InputDataValidation();
			
			if (idv.isExecuteUpdateValidString(sql)) {

				affected_rows = stmt.executeUpdate(sql);

				columns_name.add("Affected Rows");
				columns_name.add("Return");
				columns_data.add(affected_rows);
				columns_data.add("Success");
				set.add(columns_data);
				data.add(columns_name);
				data.add(set);
				data.add(errors);

			} else {

				log.AddLogLine("SQL: " + SQL);
				ResultSet rs = stmt.executeQuery(SQL);
				int rows = 0;
				while (rs.next()) {
					if(rows < Constants.SELECT_LIMIT){
						n_columns = rs.getMetaData().getColumnCount();

						log.AddLogLine("n_columns: " + n_columns);

						columns_name.clear();
						String column = "";

						for (int i = 1; i <= n_columns; i++) {
							log.AddLogLine("rs.getMetaData().getColumnName(i): "
									+ rs.getMetaData().getColumnName(i));
							columns_name.add(rs.getMetaData().getColumnName(i)
									.toString());
							column = rs.getMetaData().getColumnName(i).toString();
							if (rs.getString(column) != null) {
								columns_data.add(rs.getString(column));
							} else {
								columns_data.add("--");
							}
						}
						set.add(columns_data.clone());
						log
								.AddLogLine("===================================================");
						log.AddLogLine("nn_columns_data: " + columns_data.size());
						log.AddLogLine("nn_set: " + set.size());
						columns_data.clear();
						rows++;
					}else{
						String error = "Number of returned rows exceeded the limit ("+Constants.SELECT_LIMIT+")";
						errors.add(error);
						log.AddLogLine("EXCEPTION: " + error);
						throw new Exception(error);
					}
					
				}
				rs.close();

				log.AddLogLine("nn_columns: " + columns_name.size());

			}

		} catch (SQLException e) {

			errors.add(e.toString());

			log.AddLogLine("EXCEPTION: " + e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				con.close();
			} catch (SQLException e) {
				log.AddLogLine("EXCEPTION: " + e);
			}
		}
		data.add(columns_name);
		data.add(set);
		data.add(errors);

		return data;
	}

	public ExecuteUpdateObject getBatchSQL(String sql) {
		log.AddLogLine("invoking: getBatchSQL("+sql+")");
		ExecuteUpdateObject eUO = new ExecuteUpdateObject();
		eUO.setStatement(sql);
		Statement stmt = null;
		Connection con = null;

		try {
			Class.forName(classfn);
			//log.AddLogLine("DB: connected");
			if (driver.equals("derby")) {
				con = DriverManager.getConnection(dburl);
			} else {
				con = DriverManager.getConnection(dburl, userdb, pwddb);
			}
			stmt = con.createStatement();

			int affected_rows = 0;
			while (sql.startsWith(" ")) {
				sql = sql.substring(1);
			}

			InputDataValidation idv = new InputDataValidation();
			

			if (idv.isExecuteUpdateValidString(sql)) {
				
				log.AddLogLine("stmt.executeUpdate("+sql+")");
				affected_rows = stmt.executeUpdate(sql);
				eUO.setStatus(String.valueOf(affected_rows));
			

			} else {
				String error = "This is not an Execute/Update action";
				log.AddLogLine(error);
				eUO.setStatus(error);
			}

		} catch (SQLException e) {
			eUO.setStatement(sql);
			eUO.setStatus(String.valueOf(e.toString()));
			log.AddLogLine("SQLException: " + e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				con.close();
			} catch (SQLException e) {
				log.AddLogLine("Finally: SQLException: " + e);
			}
		}
		return eUO;
	}
	
	private Logger log = new Logger(Constants.LOGNAME);
	private String remotedb = null;
	private String portdb = null;
	private String namedb = null;
	private String userdb = null;
	private String pwddb = null;
	private String driver = null;
	private String classfn = null;
	private String dburl = null;
	private String SQL_schemas = null;
	private String scolumn = null;
	private String SQL_tables = null;
	private String tcolumn = null;
	private String SQL_structure = null;

	private String SCOLNAME = null;
	private String STYPE = null;
	private String SLENGTH = null;
	private String slimit = null;

}
