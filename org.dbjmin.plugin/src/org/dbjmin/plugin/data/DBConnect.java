package org.dbjmin.plugin.data;

/**
 * This class takes care of all DB connections 
 * @author Jose Damico (damico@dcon.com.br)
 * 
 * */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.dbjmin.plugin.util.Constants;
import org.dbjmin.plugin.util.Logger;
import org.eclipse.swt.widgets.Combo;


public class DBConnect {
		private static Connection conn = null; //Variavel de Conexão recebe o nulo
		//private static String dbURL = "jdbc:derby://localhost:1527/myDB;create=true;user=app;password=app";
		private Logger log = new Logger(Constants.LOGNAME);
		private Statement stmt;
		private String query;
		private String remotedb;
		private String portdb;
		private String namedb;
		private String userdb;
		private String pwddb;
		private String dbURL;  
		private Combo combo;
		private String scolumn;
		private String tcolumn;
		private String SQL_schemas = null;
		private String SQL_tables = null;
		private String SCOLNAME;
		private String STYPE;
		private String slimit;
		private String SLENGTH;
		private String SQL_structure = null;
		private int cols;
		private Combo driver;
		
		public DBConnect(){
		
		}
	
		/* 
		 * This simple method check all database and choice a database and connects
		 */
		public boolean isValidConnect(Combo driver, ArrayList<String> prefs){
			this.driver = driver;
			boolean ret = false;
			if(driver.getItem(driver.getSelectionIndex()).toString().equals("derby")){
				
				createConnectionDerby(prefs);
			
			}else if(driver.getItem(driver.getSelectionIndex()).toString().equals("firebird")){
				
				createConnectionFirebird(prefs);
			
			}else{
				JOptionPane.showMessageDialog(null,"Choose a database please");
			}
			
			return ret;
			
		}
		
	    /*
	     * This method connect with Derby Database
	     * */     
		public void createConnectionDerby(ArrayList<String> prefs)
		{
			
			remotedb = prefs.get(0).toString();
			portdb = prefs.get(1).toString();
			namedb = prefs.get(2).toString();
			userdb = prefs.get(3).toString();
			pwddb = prefs.get(4).toString();
		
			dbURL = "jdbc:derby://"+remotedb+":"+portdb+"/"+namedb+";create=true;user="+userdb+";password="+pwddb;
			System.out.println(dbURL);
			scolumn = "SCHEMANAME";
			tcolumn = "TABLENAME";
			SCOLNAME = "COLUMNNAME";
			STYPE = "COLUMNDATATYPE";
			SLENGTH = "COLUMNDATATYPE";
			slimit = " FIRST 100 ";
			
			SQL_schemas = "select " + scolumn + " from SYS.SYSSCHEMAS";
			
			SQL_tables = "select "	+ tcolumn + " from  sys.systables st "
				+ "left join sys.sysschemas ss on st.schemaid=ss.schemaid where schemaname=?";
			
			SQL_structure  = "SELECT "
				+ SCOLNAME
				+ ","
				+ STYPE
				+ ","
				+ SLENGTH
				+ "  FROM SYS.SYSTABLES ST "
				+ "LEFT JOIN SYS.SYSSCHEMAS SS ON ST.SCHEMAID=SS.SCHEMAID "
				+ "LEFT JOIN SYS.SYSCOLUMNS SC ON ST.TABLEID=SC.REFERENCEID "
				+ "WHERE SCHEMANAME=? AND TABLENAME=?";
	      try
	      {
	          Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
	          conn = DriverManager.getConnection(dbURL); 
	        
	      }
	      catch (Exception except)
	      {
	        
	    	  JOptionPane.showMessageDialog(null,"Connection not found");
	          except.printStackTrace();
	   
	      }
	  }
		
	    /*
	     * This method connect with Firebird Database
	     * */    
		public void createConnectionFirebird(ArrayList<String> prefs)
		{
			
			remotedb = prefs.get(0).toString();
			portdb = prefs.get(1).toString();
			namedb = prefs.get(2).toString();
			userdb = prefs.get(3).toString();
			pwddb = prefs.get(4).toString();
		
			dbURL = "jdbc:firebirdsql:" + remotedb + "/" + portdb + ":"+ namedb + "";
			System.out.println(dbURL+ userdb+ pwddb);
	      try
	      {
	          Class.forName("org.firebirdsql.jdbc.FBDriver").newInstance();
	          conn = DriverManager.getConnection(dbURL, userdb, pwddb); 
	          System.out.println("Connected");
	      }
	      catch (Exception except)
	      {
	     
	    	  JOptionPane.showMessageDialog(null,"Connection not found");
	          except.printStackTrace();
	      }
	  }
		
		
		 public ArrayList getSchemas() {
				ArrayList schemas = new ArrayList();
						
				if(driver.getItem(driver.getSelectionIndex()).toString().equals("firebird")) {
					
					schemas.add(namedb);
					
				} else {
				 try
			     {
						if (driver.getItem(driver.getSelectionIndex()).toString().equals("derby")) {
							conn = DriverManager.getConnection(dbURL);
						} else {
							conn = DriverManager.getConnection(dbURL, userdb, pwddb);
						}
						log.AddLogLine("DB: connected");
						
			         stmt = conn.createStatement();
			         String SQL = SQL_schemas;
			         
			         ResultSet rs = stmt.executeQuery(SQL);
			     	while (rs.next()) {
						schemas.add(rs.getString("" + scolumn + ""));
						System.out.println(rs.getString("" + scolumn + ""));
					}
					rs.close();
			     }
			     catch (SQLException sqlExcept)
			     {
			         sqlExcept.printStackTrace();
			     }
					}
				return schemas;
		}

	 
	public ArrayList getTables(String schema)
	{
		System.out.println(query);
		ArrayList<String> tables = new ArrayList<String>();
	     try
	     {
	         stmt = conn.createStatement();
	         String SQL = SQL_tables;
	         PreparedStatement stmtp = conn.prepareStatement(SQL);
	         stmtp.setString(1, schema);
	         ResultSet rs = stmtp.executeQuery();
	         while (rs.next()) {
					tables.add(rs.getString("" + tcolumn + ""));
					System.out.println(rs.getString("" + tcolumn + ""));
			}
			rs.close();
		     }
		     catch (SQLException sqlExcept)
		     {
		         sqlExcept.printStackTrace();
		     }
			return tables;
	}
	
	public ArrayList getSQL(String sql)
	{
		ArrayList<String> querySQL = new ArrayList<String>();
	    PreparedStatement stmtp;
		try {
		    stmt = conn.createStatement();
			stmtp = conn.prepareStatement(sql);
			ResultSet rs = stmtp.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			cols = meta.getColumnCount();

			/*while(rs.next()){
					System.out.println(rs.getMetaData());
			}*/
			for (int i = 1; i <= cols; ++i) {
				querySQL.add(meta.getColumnLabel(i));
				System.out.println(meta.getColumnLabel(i));
	
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
		return querySQL;

    }
	
	public ArrayList getTablesDescription(String schema, String table) {

		Statement stmt = null;
		ArrayList<ArrayList> table_description = new ArrayList<ArrayList>();
		ArrayList<String> colname = new ArrayList<String>();
		ArrayList typename = new ArrayList();
		ArrayList length = new ArrayList();
		Connection con = null;
		try {
	
			stmt = conn.createStatement();
			String SQL = SQL_structure;
			PreparedStatement stmtp = conn.prepareStatement(SQL);
			stmtp.setString(1, schema);
			stmtp.setString(2, table);
			log.AddLogLine("SQL: " + SQL);
			ResultSet rs = stmtp.executeQuery();
			while (rs.next()) {
				colname.add("" + rs.getString("" + SCOLNAME + ""));
				typename.add("" + rs.getString("" + STYPE + ""));
				length.add("" + rs.getString("" + SLENGTH + ""));
				//System.out.println("" + rs.getString("" + SCOLNAME + ""));

			}
			rs.close();
		} catch (SQLException e) {
			log.AddLogLine("EXCEPTION: " + e);
		}
		table_description.add(colname);
		table_description.add(typename);
		table_description.add(length);
		return table_description;
	}
	
	 
}
