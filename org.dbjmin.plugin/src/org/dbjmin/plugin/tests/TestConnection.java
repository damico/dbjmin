package org.dbjmin.plugin.tests;

import java.sql.SQLException;
import java.util.ArrayList;

import org.dbjmin.plugin.data.DBConnect;

/**
 * Tests connection with DataBase thru of Console
 * @author Mario Cezar Ponciano - a.k.a: Razec (mrazec@gmail.com)
 *
 */

public class TestConnection {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args){
		// TODO Auto-generated method stub
	testDerby();
	}
	
	public static void testDerby(){
		
		final ArrayList<String> list = new ArrayList<String>();
		list.add("localhost");
		list.add("1527");
		list.add("myDB");
		list.add("app");
		list.add("app");
		DBConnect dbc = new DBConnect();
		dbc.createConnectionDerby(list);
		dbc.getSchemas();
		/*dbc.getTables("APP");*/
		//dbc.getSQL("Select * from RESTAURANTS");
		/*dbc.getTablesDescription("APP", "RESTAURANTS");*/
		
	
	}
	
	public static void testFirebird(){
		final ArrayList<String> list = new ArrayList<String>();
		list.add("localhost");
		list.add("3050");
		list.add("/home/workspace/SISTEMA.FDB");
		list.add("sysdba");
		list.add("masterkey");
		DBConnect dbc = new DBConnect();
		dbc.createConnectionFirebird(list);

	}

}
